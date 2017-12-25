import os
from flask import Flask,request,redirect,session,g,render_template,flash,url_for,send_from_directory,json
from werkzeug.utils import secure_filename
from Matriz import Matriz
from Lista import Lista
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

@app.route('/')
@app.route("/index")
def index():
    return render_template('login.html')

@app.route("/print_users")
def print_users():
    return users.structure_string()

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
                            songs_directory.add_genere(year_data,node_album.genero)

                            artist_albums.add(node_album)
                    artists.add(artist_albums)

            songs_directory.show()


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

