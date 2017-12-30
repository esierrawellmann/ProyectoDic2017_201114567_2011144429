import os
from flask import Flask,request,redirect,session,g,render_template,flash,url_for,send_from_directory,json
from werkzeug.utils import secure_filename
from Matriz import Matriz
from Lista import Lista
from BTree import BTree
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

@app.route("/btree")
def btree():
    arbol = BTree(3)

    arbol.insert(1)
    arbol.insert(2)
    arbol.insert(3)
    arbol.insert(4)
    arbol.insert(5)
    arbol.insert(6)
    arbol.insert(7)
    arbol.insert(8)
    arbol.insert(9)
    arbol.insert(10)
    arbol.insert(11)
    arbol.insert(12)

    arbol.insert(13)
    arbol.insert(14)
    arbol.insert(15)
    arbol.insert(16)
    arbol.insert(17)

    arbol.insert(18)

    arbol.insert(19)

    arbol.insert(20)
    arbol.insert(21)
    arbol.insert(22)

    arbol.insert(23)
    arbol.insert(24)
    arbol.insert(25)
    arbol.insert(26)
    arbol.insert(28)


    arbol.print_order()

    return "printed"

@app.route("/print_matrix")
def print_matrix():
    songs_directory = g.songs_directory
    return songs_directory.show_matrix()

@app.route("/show_artist_tree/<year>/<genere>")
def show_artist_tree(year,genere):
    _year = g.songs_directory.years.search_year(year).data
    if _year is not None:
        genere = _year.generes.search_genere(genere).data
        if genere is not None:
            genere.arbol.show_arbol()
    return year

@app.route("/login", methods=['POST'])
def login():
    json_response = request.get_json();
    attempt_user = users.search_user(json_response['user'])
    if attempt_user.data.password ==json_response['password']:
        return json.dumps({'success':True}), 200, {'ContentType':'application/json'}
    return json.dumps({'success':False}), 200, {'ContentType':'application/json'}


def allowed_file(filename):
    return '.' in filename and \
           filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS

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
                    usr.username = usuario.find("nombre").text
                    usr.password = usuario.find("pass").text
                    users.add(usr)

            for artistas in e.findall('artistas'):
                for artista in artistas:
                    node_artist = Artista()
                    node_artist.nombre = artista.find("nombre").text
                    artists.add(node_artist)
                    for albums in artista.findall('albumes'):
                        for album in albums:
                            node_album = Album()
                            node_album.año = album.find("año").text
                            node_album.genero = album.find("genero").text
                            node_album.nombre = album.find("nombre").text
                            year_data = songs_directory.add_year(node_album.año).data
                            genere = songs_directory.add_genere(year_data,node_album.genero).data
                            if genere.arbol.search(node_artist) is False:
                                genere.arbol.insert(node_artist)
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

@app.route('/eliminar')
def eliminar():
    userList = g.users
    if userList is not None:
        userList.delete(userList.search(8))
        userList.delete(userList.search(3))
        userList.show()
    return "eliminados"

def printStructure(stringStructure):
    pass

class Artista(object):
    nombre = None
    albums = None
class Usuario(object):
    username = None
    password = None

class Album(object):
    nombre = None
    genero = None
    año=None
app.secret_key = 'A0Zr98j/3yX R~XHH!jmN]LWX/,?RT'

if __name__ == '__main__':
    app.run(debug=True)
