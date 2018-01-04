from Node import Node
class ABB(object):
    raiz = None

    info = "";
    str_t="";
    str_json = "";
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
        if dato == None:
            return None
        else:
            if dato.dato.nombre == a:
                return dato.dato
            else:
                if a.lower().strip() < dato.dato.nombre.lower().strip():
                    return self.buscar(dato.left, a)
                else:
                    return self.buscar(dato.right, a)

    def show_abb_json(self,raiz):
        if raiz == None:
            return ""
        else:
            self.show_abb_json(raiz.left)
            self.show_abb_json(raiz.right)
            self.str_json+="{{\"nombre\":\"{0}\",{1} }},".format(raiz.dato.nombre,raiz.dato.canciones.structure_songs_json(False))

    def show_abb(self, raiz):
        if raiz == None:
            return ""
        else:
            dto = raiz.dato.nombre.lower().strip().replace(" ", "")
            self.show_abb(raiz.left)
            if (raiz.left is not None):
                dto_l = raiz.left.dato.nombre.lower().strip().replace(" ", "")
                self.str_t += "node{0} -> node{1};\n".format(dto, dto_l)
            else:
                self.str_t += "{1}[label=\"null\"]; node{0} -> {1};\n".format(dto, "nulli{0}".format(dto))
            self.str_t += "node{0}[label=\"{1}\"];\n".format(dto, raiz.dato.nombre.replace(" ", "\\n"))
            self.show_abb(raiz.right)
            self.str_t += "{1}[label=\"null\"]; node{0} -> {1};\n".format(dto, "nulld{0}".format(dto))

    def print_abb(self,raiz):
        self.str_t =""
        self.show_abb(raiz.raiz)
        return self.str_t

    def structure_disk_json(self,raiz,asObject = True):
        self.str_json ="{0} \"albums\":[".format("{" if asObject else "")
        self.show_abb_json(raiz)
        self.str_json = self.str_json[:-1]
        self.str_json += "]{0}".format("}" if asObject else "")
        return self.str_json

class Disco(object):
    left = None
    right = None
    dato = None
    def __init__(self,dato):
        self.dato = dato
        izquierdo = None
        derecho = None

