from Node import Node
from flask import jsonify

class Lista(object):
    head = None
    tail = None


    def add(self,data):
        new_node = Node(data)
        if self.head is None:
            self.head = self.tail = new_node
            new_node.prev = self.head
            new_node.next = self.head
            new_node.index = 0
        else:
            new_node.data = data
            new_node.prev = self.tail
            new_node.next = self.head
            new_node.index = self.tail.index + 1
            self.tail.next = new_node
            self.head.prev = new_node
            self.tail = new_node
        return new_node
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

    def search_year(self, dato):
        temp = self.head
        if self.head is not None:
            while (True):
                if (temp.data.year == dato):
                    return temp
                temp = temp.next
                if (temp == self.head):
                    return None

    def get_at(self, dato):
        temp = self.head
        if self.head is not None:
            while (True):
                if (temp.index == dato):
                    return temp
                temp = temp.next
                if (temp == self.head):
                    return None

    def search_genere(self, dato):
        temp = self.head
        if self.head is not None:
            while (True):
                if (temp.data.genere == dato):
                    return temp
                temp = temp.next
                if (temp == self.head):
                    return None

    def delete(self,node):
        if node is not None:
            node.prev.next = node.next
            node.next.prev = node.prev
            node = None

    def add_years(self,data):
        self.add(data)

    def sort_by_years(self):
        temp = self.head
        if self.head is not None and self.head.next is not self.head:
            while (True):
                temp2 = self.head
                while (True):
                    if temp2.next is not self.head and int(temp2.data.year) > int(temp2.next.data.year):
                        bubble_data = temp2.next.data
                        temp2.next.data = temp2.data
                        temp2.data = bubble_data
                    temp2 = temp2.next
                    if (temp2 == self.head):
                        break
                temp = temp.next
                if (temp == self.head):
                    break

    def sort_by_generes(self,data):
        temp = data.head
        if temp is not None:
            while (True):
                temp2 = data.head
                while (True):
                    if temp2.next is not data.head and temp2.data.genere.replace(" ","") > temp2.next.data.genere.replace(" ",""):
                        bubble_data = temp2.next.data
                        temp2.next.data = temp2.data
                        temp2.data = bubble_data
                    temp2 = temp2.next
                    if (temp2 == data.head):
                        break
                temp = temp.next
                if (temp == data.head):
                    break

    def show(self):
        print("Show list data:")
        temp = self.head
        if self.head is not None:
            while (True):
                print(temp.data.__dict__)
                temp = temp.next
                if (temp == self.head):
                    break
    def structure_string(self):
        nodes = ""

        temp = self.head
        if self.head is not None:
            nodes += "subgraph col"+str(temp.index)+"{ \n rank=UD; \n"
            while (True):
                nodes += "user{0}[label={1}] \n".format(temp.index, temp.data.username)
                nodes += "user{0}->user{1} \n".format(temp.index,temp.next.index)
                nodes += "user{1}->user{0} \n".format(temp.prev.index, temp.index)
                temp = temp.next
                if (temp == self.head):
                    break
            nodes += " } "

        output = "digraph dibujo{node[shape=box width=1];rank=LR; "+nodes+" }"
        print(output)
        return output

    def __init__(self):
        pass