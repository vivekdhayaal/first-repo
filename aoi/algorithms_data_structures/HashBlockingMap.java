import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.HashMap;

/**
 * A bounded HashMap
 * Each key is mapped to a queue containing multiple values
 * The queue is based on linked nodes
 * This queue orders elements FIFO (first-in-first-out)
 * The head of the queue is that element that has been on the queue the longest time
 * The tail of the queue is that element that has been on the queue the shortest time
 * New elements are inserted at the tail of the queue, and the queue retrieval
 * operations obtain elements at the head of the queue
 *
 * The respective capacity arguments serves as a way to prevent map and queue expansion
 * Linked nodes are dynamically created upon each insertion
 *
 * @param <K,E> the type of key,values held in this collection
 *
 */

class HashBlockingMap<K, E> {

    /*
     * A variant of the HashMap with a size bound.  The putLock gates
     * entry to "offer" and "popOrRemove", and has a associated conditions:
     * 1) for offers that wait for space to be available in map
     * 2) for offers with identical keys that wait for space in respective queues
     * An "offer" would put an entry into map if space available.
     * If "key" already exists, a queue is created for that key and the offered element enqueued
     * to maintain the order of elements from identical offers.
     * A "popOrRemove" pops out an element from the queue associated with a key
     * or removes the associated mapping, if the queue is empty.
     * When a "popOrRemove" notices that it has enabled at least one offer,
     * it signals. That offer in turn signals others if more
     * items have been popOrRemove'd since the signal. 
     *
     */

    /**
     * Linked queue class
     */
    static class LinkedQueue<E> {
        /**
         * Linked list node class
         */
        static class Node<E> {
            E item;
        
            /**
             * One of:
             * - the real successor Node
             * - null, meaning there is no successor (this is the last node)
             */
            Node<E> next;
        
            /**
             * Constructor
             * @param x the initial element in the queue
             */
            Node(E x) { 
                item = x;
                next = null;
            }
        }

        /** The capacity bound */
        private final int mQueueCapacity;
    
        /** Current number of elements */
        private volatile int mQueueCount = 0;

        /**
         * Head of linked list.
         * Invariant: head.item == null
         */
        private transient Node<E> head;
        
        /**
         * Tail of linked list.
         * Invariant: last.next == null
         */
        private transient Node<E> last;

        /** Wait queue for waiting offers */
        private final Condition mQueueNotFull;

        /**
         * Links node at end of queue.
         *
         * @param e the node to be enqueued
         * @param nanos the time duration to wait to add if the queue is full
         * @return true if enqueued successfully; false if waiting time elapsed
         * @throws InterruptedException
         */
        boolean enqueue(E e, long nanos) throws InterruptedException {
            while (mQueueCount == mQueueCapacity) {
                if (nanos <= 0) {
                    return false;
                }
                nanos = mQueueNotFull.awaitNanos(nanos);
            }
            Node<E> node = new Node<E>(e);
            last = last.next = node;
            mQueueCount++;
            if (mQueueCount < mQueueCapacity) {
                mQueueNotFull.signal();
            }
            return true;
        }
        
        /**
         * Removes a node from head of queue.
         *
         * @return x the dequeued node
         */
        E dequeue() {
            E x = null;
            if (mQueueCount > 0) {
                Node<E> h = head;
                Node<E> first = h.next;
                h.next = h; // help GC
                head = first;
                x = first.item;
                first.item = null;
                if (mQueueCount-- == mQueueCapacity) {
                    mQueueNotFull.signal();
                }
            }
            return x;
        }

        /**
         * Initialize the linked queue
         *
         * @param condition the condition on which items wait to enter this queue
         */
        LinkedQueue(Condition condition) {
            mQueueNotFull = condition;
            mQueueCapacity = 2;
            last = head = new Node<E>(null);
        }

    }


    /** The capacity bound */
    private final int mCapacity;

    /** Current number of elements */
    private volatile int mCount = 0;

    /**
     * Hash Map.
     */
    private transient HashMap<K, LinkedQueue<E>> mMap;

    /** Lock held by offer */
    private final ReentrantLock mPutLock = new ReentrantLock();

    /** Wait queue for waiting offers */
    private final Condition mNotFull = mPutLock.newCondition();

    /**
     * Constructs a new map with given size
     *
     * @param capacity the capacity of this map
     */
    HashBlockingMap(int capacity) {
        if (capacity <= 0) {
           throw new IllegalArgumentException();
        }
        mCapacity = capacity;
        // create a map with initial capacity 'mCapacity + 1' and load factor 1 to prevent resizing
        // threshold = (initial capacity * load factor)
        // we know it'll hold at max 'mCapacity' no. of elements. so threshold will never be reached
        // and thus resizing won't happen
        mMap = new HashMap<K, LinkedQueue<E>>(mCapacity + 1, 1);
    }

    /**
     * Returns the number of elements in this map.
     *
     * @return the number of elements in this map
     */
    int size() {
        return mCount;
    }

    /**
     * Associates the specified element with the specified key in this map, waiting if
     * necessary up to the specified wait time for space to become available
     * If the map previously contained a mapping for the key, the element will be
     * added to the queue associated with this key
     *
     * @param k key with which the specified element is to be associated
     * @param e the element to add to the Map
     * @param timeout the time duration to wait to add if the map is full
     * @param unit the time unit of timeout
     * @return 0 if mapping successful; 1 if key exists and element added to queue;
     *         -1 if the specified waiting time elapses before space is available.
     * @throws InterruptedException 
     * @throws NullPointerException 
     */
    int offer(K k, E e, long timeout, TimeUnit unit) throws InterruptedException {

        if (e == null) {
            throw new NullPointerException();
        }
        int status = -1;
        long nanos = unit.toNanos(timeout);
        final ReentrantLock putLock = mPutLock;
        putLock.lockInterruptibly();
        try {
            if (!mMap.containsKey(k)) {
                while (mCount == mCapacity) {
                    if (nanos <= 0) {
                        return -1;
                    }
                    nanos = mNotFull.awaitNanos(nanos);
                }
                mMap.put(k, null);
                mCount++;
                status = 0;
                if (mCount < mCapacity) {
                    mNotFull.signal();
                }
            } else {
                boolean enqueued = false;
                LinkedQueue<E> q = mMap.get(k);
                if (q == null) {
                    q = new LinkedQueue<E>(putLock.newCondition());
                    mMap.put(k, q);
                }
                enqueued = q.enqueue(e, nanos);
                if (enqueued) {
                    status = 1;
                }
            }
        } finally {
            putLock.unlock();
        }
        return status;
    }

    /**
     * Pops out an element from the queue associated with a key and
     * after all elements have been popped out, removes the mapping for the specified key from this map
     * if it is present. 
     *
     * @param o the key
     * @return returnValue the element popped out of queue or null if queue is empty and the key mapping was removed
     */
    E popOrRemove(Object o) {
        if (o == null) {
            return null;
        }
        E e = null;
        final ReentrantLock putLock = mPutLock;
        putLock.lock();
        try {
            LinkedQueue<E> q = mMap.get(o);
            if (q != null) {
                e = q.dequeue();
            }
            if (e == null) {
                // queue empty or non-existant; mapping can be removed
                mMap.remove(o);
                if (mCount-- == mCapacity) {
                    mNotFull.signal();
                }
            }
        } finally {
            putLock.unlock();
        }
        return e;
    }

}

