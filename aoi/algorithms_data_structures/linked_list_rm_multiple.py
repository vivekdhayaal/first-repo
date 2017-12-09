class Node:
  def __init__(self, name, next):
    self.name = name
    self.next = next

def rm_multiple(head):
  node_list = [head]
  node = head.next
  while node != None:
    if node.name == node_list[-1].name:
      while node!= None and node.name == node_list[-1].name:
        node = node.next
      node_list.pop()
    else:
      node_list.append(node)
      node = node.next
  # now node_list contains the multiples removed
  # lets print it
  print "actual output sequence: ",
  for n in node_list:
    print n.name,
  print

if __name__ == "__main__":
  print "input sequence: 1234435667"
  print "expected ouput sequence: 1257"
  head = Node(1, Node(2, Node(3, Node(4, Node(4, Node(3, Node(5, Node(6, Node(6, Node(7, None))))))))))
  rm_multiple(head)
  print "input sequence: 1234435556778"
  print "expected ouput sequence: 1268"
  head = Node(1, Node(2, Node(3, Node(4, Node(4, Node(3, Node(5, Node(5, Node(5, Node(6, Node(7, Node(7, Node(8, None)))))))))))))
  rm_multiple(head)
  print "input sequence: 12344355526"
  print "expected ouput sequence: 16"
  head = Node(1, Node(2, Node(3, Node(4, Node(4, Node(3, Node(5, Node(5, Node(5, Node(2, Node(6, None)))))))))))
  rm_multiple(head)
