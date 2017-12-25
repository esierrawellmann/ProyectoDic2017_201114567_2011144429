from Lista import Lista

class Year(object):
    year = None
    generes = Lista()

class Genere(object):
    genere = None

class Matriz(object):

    years = Lista()

    single_generes = Lista();

    def add_year(self, data):
        year_to_insert = Year()
        year_to_insert.year = data
        row_year = self.years.search_year(year_to_insert)
        if  row_year is None:
            return self.years.add(year_to_insert)
        else:
            return row_year

    def add_single_generes(self,data):
        gen = Genere()
        gen.genere = data
        gen_src = self.single_generes.search_genere(data)
        if gen_src is None:
            self.single_generes.add(gen)


    def add_genere(self,year, data):
        genere_to_add = Genere()
        genere_to_add.genere = data
        col_genere = year.generes.search_genere(data)
        if col_genere is None:
            year.generes.add(genere_to_add)
        else:
            return col_genere


    def show_matrix(self):
        nodes = ""

        temp = self.head
        if self.head is not None:
            nodes += "subgraph col" + str(temp.index) + "{ \n rank=UD; \n"
            while (True):
                nodes += "user{0}[label={1}] \n".format(temp.index, temp.data.username)
                nodes += "user{0}->user{1} \n".format(temp.index, temp.next.index)
                nodes += "user{1}->user{0} \n".format(temp.prev.index, temp.index)
                temp = temp.next
                if (temp == self.head):
                    break
            nodes += " } "

        output = "digraph dibujo{node[shape=box width=1];rank=LR; " + nodes + " }"
        print(output)
        return output




