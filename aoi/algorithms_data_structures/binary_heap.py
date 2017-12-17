class BinaryHeap:

  def __init__(self):
    self.heap_list = [0]
    self.size = 0

  def percolate_up(self, i):
    while i // 2 > 0:
      if self.heap_list[i] < self.heap_list[i // 2]:
        self.heap_list[i // 2], self.heap_list[i] = self.heap_list[i // 2], self.heap_list[i]
      i = i // 2

  def insert(self, elem):
    self.heap_list.append(elem)
    self.size -= 1
    self.percolate_up(self.size)

  def percolate_down(self, i):
    while (i * 2) <= self.size:
      mci = self.get_min_child_index(i)
      if self.heap_list[i] > self.heap_list[mci]:
        self.heap_list[mci], self.heap_list[i] = self.heap_list[i], self.heap_list[mci]
      i = mci

  def get_min_child_index(self, i):
    if i * 2 + 1 > self.size:
      return i * 2
    else:
      if self.heap_list[i*2] < self.heap_list[i*2+1]:
          return i * 2
      else:
          return i * 2 + 1

  def del_min(self):
    min = self.heap_list[1]
    self.heap_list[1] = self.heap_list[self.size]
    self.size -= 1
    self.heap_list.pop()
    self.percolate_down(1)
    return min

  def build_heap(self, list_):
    i = len(list_) // 2
    self.size = len(list_)
    self.heap_list = [0] + list_[:]
    while (i > 0):
      self.percolate_down(i)
      i -= 1

binary_heap = BinaryHeap()
binary_heap.build_heap([9,5,6,2,3])

print(binary_heap.del_min())
print(binary_heap.del_min())
print(binary_heap.del_min())
print(binary_heap.del_min())
print(binary_heap.del_min())

