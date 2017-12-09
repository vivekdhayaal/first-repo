class Node:
  def __init__(self, name, next):
    self.name = name
    self.next = next

def find_loop(head):
  node_set = {head.name}
  node = head
  while node.next != None:
    node = node.next
    if node.name in node_set:
      # loop detected
      print "loop detected at node with name: %s" % node.name
      return
    else:
      node_set.add(node.name)
  print "no loop detected in given linked list"

if __name__ == "__main__":
  # linked list with loop
  tail = Node(2, None)
  tail.next = tail
  head = Node(1, tail)
  find_loop(head)  
  # linked list without loop
  tail = Node(2, None)
  head = Node(1, tail)
  find_loop(head)  
