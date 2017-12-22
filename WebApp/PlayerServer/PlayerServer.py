from flask import Flask,Request,session,g
import Lista
app = Flask(__name__)

variable = "texto"
users = Lista.Lista()

@app.before_request
def before_request():
    g.users = users
@app.route('/')
def hello_world():
    if 'username' in session:
        return session["username"]
    return "not loged in"

@app.route('/cambiarTexto')
def cambiar_texto():
    session["username"] = "renatosierra"
    return session["username"]

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

app.secret_key = 'A0Zr98j/3yX R~XHH!jmN]LWX/,?RT'

if __name__ == '__main__':
    app.run(debug=True)

