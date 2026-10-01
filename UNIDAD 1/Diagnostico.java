
public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
    }



    class Nodo {
        String nombre;
        Nodo izquierdo;
        Nodo derecho;

        public Nodo(String nombre) {
            this.nombre = nombre;
            this.izquierdo = null;
            this.derecho = null;
        }
    }


    public class Arbol {
        Nodo raiz;

        public Arbol() {
            this.raiz = null;
        }

        public boolean vacio() {
            return this.raiz == null;
        }

        
        public Nodo buscarNodo(String nombre) {
            Nodo actual = this.raiz;

            while (actual != null) {
                int comparacion = nombre.compareToIgnoreCase(actual.nombre);

                if (comparacion == 0) {
                    return actual;
                } else if (comparacion < 0) {
                    actual = actual.izquierdo;
                } else {
                    actual = actual.derecho;
                }
            }

            return null;
        }
    }
}
