# Python Program to find path between 
# n1 and n2 using tree DFS

class Node:
    def __init__(self, data, children = []):
        self.data = data
        self.children = children
 
def pathToNode(node, path, data):
 
    # base case handling
    if node is None:
        return False
 
    # append the node data in path
    path.append(node.data)
  
    # See if the data is same as node's data
    if node.data == data:
        return True
  
    # Check if data is found in children
    for child in node.children:
        if pathToNode(child, path, data):
            return True
  
    # If not present in children under node, 
    # remove node data from path and return False 
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
        # common ancestor
        i=0
        while i<len(path1) and i<len(path2):
            # get out as soon as the path differs 
            # or any path's length get exhausted
            if path1[i] != path2[i]:
                break
            i = i+1
 
        # get the path by deducting the 
        # intersecting path (or till LCA)
        #print "source", data1
        parents = path1[i-1:]
        parents.reverse()
        #print "source's parents", parents
        #print "LCA", path2[i-1]
        children = path2[i:]
        #print "LCA's children", children
        #print "destination", data2
        return parents + children
    else:
        return 0

# Driver Code to test above functions
root = Node(1)
rootleft = Node(2)
rootright = Node(3)
root.children = [rootleft, rootright]
rootleftleft = Node(4)
rootrightright= Node(7)
rootrightleft = Node(6)
rootleftright = Node(5)
rootleft.children = [rootleftleft, rootleftright]
rootright.children = [rootrightleft, rootrightright]
rootrightleftright = Node(8)
rootrightleft.children = [rootrightleftright]
 
dist = distance(root, 4, 5)
print "Path between node {} & {}: {}".format(4, 5, dist) 
 
dist = distance(root, 4, 6)
print "Path between node {} & {}: {}".format(4, 6, dist) 
 
dist = distance(root, 3, 4)
print "Path between node {} & {}: {}".format(3, 4, dist) 
 
dist = distance(root, 2, 4)
print "Path between node {} & {}: {}".format(2, 4, dist) 
 
dist = distance(root, 8, 5)
print "Path between node {} & {}: {}".format(8, 5, dist) 
