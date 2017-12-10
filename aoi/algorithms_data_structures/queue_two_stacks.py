class Stack:

  def __init__(self):
    self.data_store = []

  def push(self, elem):
    self.data_store.append(elem)

  def pop(self):
    return self.data_store.pop()

  def size(self):
    return len(self.data_store)

  def print_stack(self):
    print self.data_store


class Queue:

  def __init__(self):
    self.size = 0
    self.push_stack = Stack()
    self.pop_stack = Stack()

  def switch_stacks(self, src, dest):
    print "moving elements from one stack to other"
    while src.size() != 0:
      dest.push(src.pop())

  def enqueue(self, elem):
    if self.size != 0 and self.push_stack.size() == 0:
      self.switch_stacks(self.pop_stack, self.push_stack)
    self.push_stack.push(elem)
    self.size += 1
    
  def dequeue(self):
    if self.size != 0 and self.pop_stack.size() == 0:
      self.switch_stacks(self.push_stack, self.pop_stack)
    elem = self.pop_stack.pop()
    self.size -= 1
    return elem

  def print_queue(self):
    print "push stack", self.push_stack.print_stack()
    print "pop stack", self.pop_stack.print_stack()

if __name__ == "__main__":
  q = Queue()
  q.enqueue(1)
  q.enqueue(2)
  q.print_queue()
  q.dequeue()
  q.enqueue(3)
  q.dequeue()
  q.dequeue()
  q.print_queue()
