
class BTreeNode:
  def __init__(self, value, left=None, right=None):
    self.value = value
    self.left = left
    self.right = right


def insert(root, value):
  if root is None:
    root = BTreeNode(value)
  else:
    if value < root.value:
      if root.left is None:
        root.left = BTreeNode(value)
      else:
        insert(root.left, value)
    elif value > root.value:
      if root.right is None:
        root.right = BTreeNode(value)
      else:
        insert(root.right, value)
    else:
      print "value already exists in BST"


def test_if_bst(node):
  if node:
    if node.left:
      if node.left.value < node.value:
        test_if_bst(node.left)
      else:
        print "not a BST as node with value %s is left child of node with value %s" % (node.left.value, node.value)
    if node.right:
      if node.right.value > node.value:
        test_if_bst(node.right)
      else:
        print "not a BST as node with value %s is right child of node with value %s" % (node.right.value, node.value)
  

def traverse_inorder(node):
  if node is not None:
    traverse_inorder(node.left)
    print node.value
    traverse_inorder(node.right)

def search(node, value):
  if node is not None:
    if value < node.value:
      search(node.left, value)
    elif value > node.value:
      search(node.right, value)
    else:
      print "value %s exists in BST" % value
  else:
    print "value %s doesn't exist in BST" % value

if __name__ == "__main__":
  # BST
  #      5
  #    /   \
  #   3     7
  #  / \   / \
  # 2   4 6   8
  root = BTreeNode(5)
  insert(root, 3)
  insert(root, 2)
  insert(root, 4)
  insert(root, 7)
  insert(root, 6)
  insert(root, 8)
  # traverse BST inorder
  traverse_inorder(root)
  # search an element
  search(root, 4)
  search(root, 1)
  # verify that its a BST
  test_if_bst(root)
  # verify that its not a BST
  root.value = 1
  test_if_bst(root)
