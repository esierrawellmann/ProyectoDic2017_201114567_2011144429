import os
from flask import Flask,request,redirect,session,g,render_template,flash,url_for,send_from_directory,json,jsonify,make_response
from werkzeug.utils import secure_filename
from Matriz import Matriz
from Lista import Lista
from BTree import BTree
from ABB import ABB
import xml.etree.ElementTree
app = Flask(__name__)

UPLOAD_FOLDER = app.root_path+'/uploads'
ALLOWED_EXTENSIONS = set(['xml'])


app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER

variable = "texto"
users = Lista()
artists = Lista()
artist_albums = Lista()
songs_directory = Matriz()



@app.before_request
def before_request():
    g.users = users
    g.songs_directory = songs_directory

@app.route('/')
@app.route("/index")
def index():

    return render_template('login.html')

@app.route("/print_users")
def print_users():
    return users.structure_string()

@app.route("/dequeue_user/<u_user>")
def dequeue_user(u_user):
    usr = users.search_user(u_user.replace("+"," "))
    if usr is not None:
        usr.data.cola.dequeue()
    return ""

@app.route("/show_user_queue/<u_user>")
def show_user_queue(u_user):
    usr = users.search_user(u_user.replace("+"," "))
    if usr is not None:
        return usr.data.cola.structure_queue_string()
    return ""

@app.route("/add_song_to_queue/<y_year>/<r_genere>/<r_artist>/<r_album>/<r_song>/<r_user>")
def add_song_to_queue(y_year,r_genere,r_artist,r_album,r_song,r_user):
    _year = g.songs_directory.years.search_year(y_year.replace("+"," "))
    if _year is not None:
        genere = _year.data.generes.search_genere(r_genere.replace("+"," "))
        if genere is not None:
            node_artist = Artista()
            node_artist.nombre = r_artist.replace("+"," ")
            node_artist.b_year = _year.data.year
            node_artist.b_genere = genere.data.genere
            abb = genere.data.arbol.search(node_artist)
            if abb is not None:
                dato_abb = abb.albums.buscar(abb.albums.raiz,r_album.replace("+"," "))
                if dato_abb is not None:
                    cancion = dato_abb.canciones.search_song(r_song.replace("+"," "))
                    usr = users.search_user(r_user.replace("+"," "))
                    if usr is not None:
                        usr.data.cola.add(cancion.data)
                        return ""
    return ""


@app.route("/delete_song/<y_year>/<r_genere>/<r_artist>/<r_album>/<r_song>")
def delete_song(y_year,r_genere,r_artist,r_album,r_song):
    _year = g.songs_directory.years.search_year(y_year.replace("+"," "))
    if _year is not None:
        genere = _year.data.generes.search_genere(r_genere.replace("+"," "))
        if genere is not None:
            node_artist = Artista()
            node_artist.nombre = r_artist.replace("+"," ")
            node_artist.b_year = _year.data.year
            node_artist.b_genere = genere.data.genere
            abb = genere.data.arbol.search(node_artist)
            if abb is not None:
                dato_abb = abb.albums.buscar(abb.albums.raiz,r_album.replace("+"," "))
                if dato_abb is not None:
                    cancion = dato_abb.canciones.search_song(r_song.replace("+"," "))
                    dato_abb.canciones.delete(cancion)
    return ""

@app.route("/print_matrix", methods=['GET'])
def print_matrix():
    songs_directory = g.songs_directory
    return songs_directory.show_matrix()


@app.route("/get_disk/<y_year>/<r_genere>/<r_artist>/<r_disk_name>")
def get_disk(y_year,r_genere,r_artist,r_disk_name):
    data = "{\"canciones\":[]}"
    _year = g.songs_directory.years.search_year(y_year.replace("+"," "))
    if _year is not None:
        genere = _year.data.generes.search_genere(r_genere.replace("+"," "))
        if genere is not None:
            node_artist = Artista()
            node_artist.nombre = r_artist.replace("+"," ")
            node_artist.b_year = _year.data.year
            node_artist.b_genere = genere.data.genere
            abb = genere.data.arbol.search(node_artist)
            nodo_disco = abb.albums.buscar(abb.albums.raiz, r_disk_name.replace("+"," "))
            if nodo_disco is not None:
                data = nodo_disco.canciones.structure_songs_json()
    r = make_response(data);
    r.mimetype = 'application/json';
    return r


@app.route("/delete_artist/<y_year>/<r_genere>/<r_artist>")
def delete_artist(y_year,r_genere,r_artist):
    _year = g.songs_directory.years.search_year(y_year.replace("+"," "))
    if _year is not None:
        genere = _year.data.generes.search_genere(r_genere.replace("+"," "))
        if genere is not None:
            node_artist = Artista()
            node_artist.nombre = r_artist.replace("+"," ")
            node_artist.b_year = _year.data.year
            node_artist.b_genere = genere.data.genere
            abb = genere.data.arbol.search(node_artist)
            if abb is not None:
                new = genere.data.arbol.copy(abb)
                genere.data.arbol = new
                return ""

@app.route("/get_disks_by_year_genere/<y_year>/<r_genere>/<r_artist>")
def get_disks_by_year_genere(y_year,r_genere,r_artist):
    data = "{\"albums\":[]}"
    _year = g.songs_directory.years.search_year(y_year.replace("+"," "))
    if _year is not None:
        genere = _year.data.generes.search_genere(r_genere.replace("+"," "))
        if genere is not None:
            node_artist = Artista()
            node_artist.nombre = r_artist.replace("+"," ")
            node_artist.b_year = _year.data.year
            node_artist.b_genere = genere.data.genere
            abb = genere.data.arbol.search(node_artist)
            if abb is not None:
                data = abb.albums.structure_disk_json(abb.albums.raiz)
    r = make_response(data);
    r.mimetype = 'application/json';
    return r


@app.route("/get_artists_by_year_and_genere/<y_year>/<r_genere>")
def get_artists_by_year_and_genere(y_year,r_genere):
    data = "{\"artists\":[]}"
    _year = g.songs_directory.years.search_year(y_year.replace("+"," "))
    if _year is not None:
        genere = _year.data.generes.search_genere(r_genere.replace("+"," "))
        if genere is not None:
            data = genere.data.arbol.print_arbol_json()
    r = make_response(data);
    r.mimetype = 'application/json';
    return r

@app.route("/library")
def libray():
    _year = g.songs_directory.years.structure_years_json()
    r = make_response(_year);
    r.mimetype = 'application/json';
    return r



@app.route("/get_generes_by_year/<y_year>")
def get_generes_by_year(y_year):
    data = "{\"generes\":[]}"
    _year = g.songs_directory.years.search_year(y_year.replace("+"," "))
    if _year is not None:
        data = _year.data.generes.structure_generes_json()
    r = make_response(data);
    r.mimetype = 'application/json';
    return r

@app.route("/show_artist_tree/<year>/<genere>")
def show_artist_tree(year,genere):
    _year = g.songs_directory.years.search_year(year.replace("+"," "))
    if _year is not None:
        genere = _year.data.generes.search_genere(genere.replace("+"," "))
        if genere is not None:
            return genere.data.arbol.show_arbol()
    return "digraph G {{ node[shape=record];\n  }}"

@app.route("/show_artist_bbtree/<y_year>/<r_genere>/<r_artist>")
def show_artist_bbtree(y_year,r_genere,r_artist):
    _year = g.songs_directory.years.search_year(y_year.replace("+"," "))
    if _year is not None:
        genere = _year.data.generes.search_genere(r_genere.replace("+"," "))
        if genere is not None:
            node_artist = Artista()
            node_artist.nombre = r_artist.replace("+"," ")
            node_artist.b_year = _year.data.year
            node_artist.b_genere = genere.data.genere
            abb = genere.data.arbol.search(node_artist)

            if abb is not None:
                return "digraph G {{ node[shape=circle];\n {0} }}".format(abb.albums.print_abb(abb.albums))
    return "digraph G {{ node[shape=record];\n  }}"

@app.route("/show_artist_disks/<y_year>/<r_genere>/<r_artist>/<r_disk_name>")
def show_artist_disks(y_year,r_genere,r_artist,r_disk_name):
    _year = g.songs_directory.years.search_year(y_year.replace("+"," "))
    if _year is not None:
        genere = _year.data.generes.search_genere(r_genere.replace("+"," "))
        if genere is not None:
            node_artist = Artista()
            node_artist.nombre = r_artist.replace("+"," ")
            node_artist.b_year = _year.data.year
            node_artist.b_genere = genere.data.genere
            abb = genere.data.arbol.search(node_artist)
            nodo_disco = abb.albums.buscar(abb.albums.raiz,r_disk_name.replace("+"," "))
            if nodo_disco is not None:
                return nodo_disco.canciones.structure_songs_string()
    return "digraph G {{ node[shape=record];\n  }}"

@app.route("/login", methods=['POST'])
def login():
    json_response = request.get_json();
    attempt_user = users.search_user(json_response['user'])
    _year = g.songs_directory.years.structure_years_json()

    if attempt_user.data.password ==json_response['password']:
        return "{{\"success\":true,\"data\":{0}}}".format(_year), 200, {'ContentType':'application/json'}
    return json.dumps({'success':False}), 200, {'ContentType':'application/json'}


def allowed_file(filename):
    return '.' in filename and \
           filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS

@app.route("/xml_client", methods=['POST'])
def xml_client():
    data = request.get_data()
    str = data.decode("utf-8")

    e = xml.etree.ElementTree.fromstring(str)
    for child in e.findall('usuarios'):
        for usuario in child:
            usr = Usuario()
            usr.cola = Lista()
            usr.username = usuario.find("nombre").text
            usr.password = usuario.find("pass").text
            users.add(usr)

    for artistas in e.findall('artistas'):
        for artista in artistas:

            for albums in artista.findall('albumes'):
                for album in albums:
                    node_album = Album()
                    node_album.año = album.find("annioo").text
                    node_album.genero = album.find("genero").text
                    node_album.nombre = album.find("nombre").text
                    node_album.canciones = Lista()
                    year_data = songs_directory.add_year(node_album.año).data
                    genere = songs_directory.add_genere(year_data, node_album.genero).data
                    node_artist = Artista()
                    node_artist.nombre = artista.find("nombre").text
                    node_artist.b_year = year_data.year
                    node_artist.b_genere = genere.genere
                    artist_b_genere = genere.arbol.search(node_artist)
                    if artist_b_genere is None:
                        node_artist.albums = None
                        artist_b_genere = genere.arbol.insert(node_artist)
                    if artist_b_genere.albums is None:
                        artist_b_genere.albums = ABB()

                    for canciones in album.findall('canciones'):
                        for cancion in canciones:
                            node_cancion = Cancion()
                            node_cancion.nombre = cancion.find("nombre").text
                            node_cancion.path = cancion.find("path").text
                            node_cancion.artist = node_artist.nombre
                            node_cancion.year = node_album.año
                            node_cancion.genere = node_album.genero
                            node_cancion.album = node_album.nombre
                            node_album.canciones.add(node_cancion)
                    artist_b_genere.albums.raiz = artist_b_genere.albums.insert(artist_b_genere.albums.raiz, node_album)

                    artist_albums.add(node_album)
            artists.add(artist_albums)
    g.songs_directory = songs_directory

    #e =
    return ""

@app.route('/upload-file', methods=['POST'])
def upload_file():
    if request.method == 'POST':
        if 'file-input' not in request.files:
            flash("Not File Found")
            return redirect(request.url)
        file = request.files["file-input"]
        if file.filename =="":
            flash("Not File Found")
            return redirect(request.url)
        if file and allowed_file(file.filename):
            filename = secure_filename(file.filename)
            file.save(os.path.join(app.config['UPLOAD_FOLDER'], filename))
            e = xml.etree.ElementTree.parse(os.path.join(app.config['UPLOAD_FOLDER'], filename)).getroot()
            for child in e.findall('usuarios'):
                for usuario in child:
                    usr = Usuario()
                    usr.cola = Lista()
                    usr.username = usuario.find("nombre").text
                    usr.password = usuario.find("pass").text
                    users.add(usr)

            for artistas in e.findall('artistas'):
                for artista in artistas:

                    for albums in artista.findall('albumes'):
                        for album in albums:
                            node_album = Album()
                            node_album.año = album.find("annioo").text
                            node_album.genero = album.find("genero").text
                            node_album.nombre = album.find("nombre").text
                            node_album.canciones = Lista()
                            year_data = songs_directory.add_year(node_album.año).data
                            genere = songs_directory.add_genere(year_data,node_album.genero).data
                            node_artist = Artista()
                            node_artist.nombre = artista.find("nombre").text
                            node_artist.b_year = year_data.year
                            node_artist.b_genere = genere.genere
                            artist_b_genere = genere.arbol.search(node_artist)
                            if artist_b_genere is None:
                                node_artist.albums = None
                                artist_b_genere = genere.arbol.insert(node_artist)
                            if artist_b_genere.albums is None:
                                artist_b_genere.albums = ABB()

                            for canciones in album.findall('canciones'):
                                for cancion in canciones:
                                    node_cancion = Cancion()
                                    node_cancion.nombre = cancion.find("nombre").text
                                    node_cancion.path = cancion.find("path").text
                                    node_cancion.artist = node_artist.nombre
                                    node_cancion.year = node_album.año
                                    node_cancion.genere = node_album.genero
                                    node_cancion.album = node_album.nombre
                                    node_album.canciones.add(node_cancion)
                            artist_b_genere.albums.raiz = artist_b_genere.albums.insert(artist_b_genere.albums.raiz,node_album)



                            artist_albums.add(node_album)
                    artists.add(artist_albums)
            g.songs_directory = songs_directory


            flash("File Uploaded Successfully")
            return redirect(url_for('index'))


@app.route('/uploads/<filename>')
def uploaded_file(filename):
    return send_from_directory(app.config['UPLOAD_FOLDER'],
                               filename)

@app.route('/agregarALista')
def agregar_lista():
    userList = g.users
    if userList is not None:
        for x in range(0,10):
            userList.add(x)
            userList.show()
    return "agregado"

@app.route('/eliminar/<p_username>')
def eliminar(p_username):
    userList = g.users
    if userList is not None:
        usr = userList.search_user(p_username.replace("+"," "))
        if usr is not None:
            userList.delete(usr)
    return  userList.structure_string()

@app.route('/eliminar_genero/<r_genere>/<y_year>')
def eliminar_genero(r_genere,y_year):
    _year = g.songs_directory.years.search_year(y_year.replace("+"," "))
    if _year is not None:
        genere = _year.data.generes.search_genere(r_genere.replace("+"," "))
        if genere is not None:
            _year.data.generes.delete(genere)
    return  ""

class Artista(object):
    nombre = None
    albums = None
    b_year = None
    b_genere = None

class Usuario(object):
    username = None
    password = None
    cola = None

class Album(object):
    nombre = None
    genero = None
    año= None
    canciones = None
class Cancion(object):
    nombre = None
    path = None
    year =None
    genere = None
    artist = None
    album = None


app.secret_key = 'A0Zr98j/3yX R~XHH!jmN]LWX/,?RT'

if __name__ == '__main__':
    app.run(debug=True,port=8081)
