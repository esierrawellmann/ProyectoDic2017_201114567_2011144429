from Lista import Lista
from BTree import BTree

class Year(object):
    year = None
    generes = None

class Genere(object):
    genere = None
    arbol = BTree(3)





class Matriz(object):

    years = Lista()

    single_generes = Lista();

    def add_year(self, data):
        year_to_insert = Year()
        year_to_insert.generes = Lista()
        year_to_insert.year = data
        row_year = self.years.search_year(year_to_insert.year)
        if  row_year is None:
            return self.years.add(year_to_insert)
        else:
            return row_year

    def add_single_generes(self,data):
        gen = Genere()
        gen.genere = data
        gen_src = self.single_generes.search_genere(gen.genere)
        if gen_src is None:
            self.single_generes.add(gen)

    def  look_for_node(self,genere_index,year_index):
        year = self.years.get_at(year_index)
        if year is not None:
            found = year.data.generes.get_at(genere_index)
            if found is not None:
                return found
        return None


    def add_genere(self,year, data):
        genere_to_add = Genere()
        genere_to_add.genere = data
        col_genere = year.generes.search_genere(genere_to_add.genere)
        if col_genere is None:
            return year.generes.add(genere_to_add)
        else:
            return col_genere


    def show_matrix(self):
        nodes = edges = generes_edges = ""
        self.years.sort_by_years()

        temp = self.years.head
        if temp is not None:
            while (True):

                self.years.sort_by_generes(temp.data.generes)
                generes_edges += "subgraph col" + str(temp.index) + "{ \n rank=same; \n"
                generes = temp.data.generes.head
                if generes is not None:
                    while (True):
                        nodes += " year{0}genere{2}[label=\"<left> | <data> {1} | <right>\"]; \n".format(temp.index, temp.data.year +" " + generes.data.genere ,generes.index)


                        if temp.next is not self.years.head and self.look_for_node(generes.index,temp.next.index) is not None:
                            edges += "year{0}genere{2} -> year{1}genere{3}; \n".format(temp.index, temp.next.index,generes.index,generes.index)
                        if temp.prev is not self.years.tail  and self.look_for_node(generes.index,temp.prev.index) is not None:
                            edges += "year{0}genere{2} -> year{1}genere{3}; \n".format(temp.index, temp.prev.index,generes.index,generes.index)



                        if generes.next is not temp.data.generes.head:
                            generes_edges += "year{0}genere{2} ->year{1}genere{3}; \n".format(temp.index, temp.index,generes.index, generes.next.index)
                        if generes.prev is not temp.data.generes.tail:
                            generes_edges += "year{0}genere{2} -> year{1}genere{3}; \n".format(temp.index, temp.index,generes.index, generes.prev.index)

                        generes = generes.next
                        if (generes == temp.data.generes.head):
                            break

                generes_edges += " } "
                temp = temp.next
                if (temp == self.years.head):
                    break

        output = "digraph dibujo{ node[shape=record];" + nodes +" "+ edges + generes_edges +" }"
        print(output)
        return output




