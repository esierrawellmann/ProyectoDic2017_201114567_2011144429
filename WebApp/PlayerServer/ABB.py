from Node import Node
class ABB(object):
    raiz = None
    info = "";
    str_t="";
    def insert(self, a, dato):
        if a == None:
            a = Disco(dato)
        else:
            d = a.dato.nombre
            if dato.nombre.lower().strip() < d.lower().strip():
                a.left = self.insert(a.left, dato)
            else:
                a.right = self.insert(a.right, dato)
        return a
    def buscar(self, dato, a):
        if a == None:
            return None
        else:
            if dato.nombre == a.dato.nombre:
                return a.dato
            else:
                if dato.nombre.lower().strip() < a.dato.nombre.lower().strip():
                    return self.buscar(dato, a.left)
                else:
                    return self.buscar(dato, a.right)

    def show_abb(self,raiz):
        if raiz == None:
            return ""
        else:
            dto = raiz.dato.nombre.lower().strip().replace(" ","")
            self.show_abb(raiz.left)
            if(raiz.left is not None):
                dto_l = raiz.left.dato.nombre.lower().strip().replace(" ", "")
                self.str_t += "node{0} -> node{1};\n".format(dto,dto_l)
            else:
                self.str_t += "{1}[label=\"null\"]; node{0} -> {1};\n".format(dto,"nulli{0}".format(dto))
            self.str_t += "node{0}[label=\"{1}\"];\n".format(dto,raiz.dato.nombre.replace(" ","\\n"))
            self.show_abb(raiz.right)
            if (raiz.right is not None):
                dto_r = raiz.right.dato.nombre.lower().strip().replace(" ", "")
                self.str_t += "node{0} -> node{1};\n".format(dto, dto_r)
            else:
                self.str_t += "{1}[label=\"null\"]; node{0} -> {1};\n".format(dto, "nulld{0}".format(dto))

    def print_abb(self,raiz):
        self.str_t =""
        self.show_abb(raiz.raiz)
        print("printing tree")
        print(self.str_t)
        return self.str_t

class Disco(object):
    left = None
    right = None
    dato = None
    def __init__(self,dato):
        self.dato = dato
        izquierdo = None
        derecho = None

