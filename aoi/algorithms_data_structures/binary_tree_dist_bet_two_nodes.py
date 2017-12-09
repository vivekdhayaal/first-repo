# Python Program to find distance between 
# n1 and n2 using one traversal

class Node:
    def __init__(self, data):
        self.data = data
        self.right = None
        self.left = None
 
def pathToNode(root, path, k):
 
    # base case handling
    if root is None:
        return False
 
     # append the node value in path
    path.append(root.data)
  
    # See if the k is same as root's data
    if root.data == k :
        return True
  
    # Check if k is found in left or right 
    # sub-tree
    if ((root.left != None and pathToNode(root.left, path, k)) or
            (root.right!= None and pathToNode(root.right, path, k))):
        return True
  
    # If not present in subtree rooted with root, 
    # remove root from path and return False 
    path.pop()
    return False
 
def distance(root, data1, data2):
    if root:
        # store path corresponding to node: data1
        path1 = []
        pathToNode(root, path1, data1)
 
        # store path corresponding to node: data2
        path2 = []
        pathToNode(root, path2, data2)
 
        # iterate through the paths to find the 
        # common path length
        i=0
        while i<len(path1) and i<len(path2):
            # get out as soon as the path differs 
            # or any path's length get exhausted
            if path1[i] != path2[i]:
                break
            i = i+1
 
        # get the path length by deducting the 
        # intersecting path length (or till LCA)
        return (len(path1)+len(path2)-2*i)
    else:
        return 0

# Driver Code to test above functions
root = Node(1)
root.left = Node(2)
root.right = Node(3)
root.left.left = Node(4)
root.right.right= Node(7)
root.right.left = Node(6)
root.left.right = Node(5)
root.right.left.right = Node(8)
 
dist = distance(root, 4, 5)
print "Distance between node {} & {}: {}".format(4, 5, dist) 
 
dist = distance(root, 4, 6)
print "Distance between node {} & {}: {}".format(4, 6, dist) 
 
dist = distance(root, 3, 4)
print "Distance between node {} & {}: {}".format(3, 4, dist) 
 
dist = distance(root, 2, 4)
print "Distance between node {} & {}: {}".format(2, 4, dist) 
 
dist = distance(root, 8, 5)
print "Distance between node {} & {}: {}".format(8, 5, dist) 
