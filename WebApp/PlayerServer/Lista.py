import Node
class Lista(object):
    head = None
    tail = None


    def add(self,data):
        new_node = Node.Node(data)
        if self.head is None:
            self.head = self.tail = new_node
            new_node.prev = self.head
            new_node.next = self.tail
        else:
            new_node.data = data
            new_node.prev = self.tail
            new_node.next = self.head

            self.tail.next = new_node
            self.head.prev = new_node

            self.tail = new_node
    def search(self,dato):
        temp = self.head
        if self.head is not None:
            while (True):
                if(temp.data == dato):
                    return temp
                temp = temp.next
                if (temp == self.head):
                    break

    def search_user(self,dato):
        temp = self.head
        if self.head is not None:
            while (True):
                if(temp.data.username == dato):
                    return temp
                temp = temp.next
                if (temp == self.head):
                    break
    def delete(self,node):
        if node is not None:
            node.prev.next = node.next
            node.next.prev = node.prev
            node = None

    def show(self):
        print
        "Show list data:"
        temp = self.head
        if self.head is not None:
            while (True):
                print(temp.data)
                temp = temp.next
                if (temp == self.head):
                    break

    def __init__(self):
        pass