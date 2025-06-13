package estructuras;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementación genérica de una lista enlazada simple.
 */
public class ListaEnlazada<T> implements Iterable<T> {
    
    private Nodo<T> cabeza;
    private int tamanio;

    /**
     * Nodo interno de la lista.
     */
    private static class Nodo<T> {
        T dato;
        Nodo<T> siguiente;

        Nodo(T dato) {
            this.dato = dato;
        }
    }

    public ListaEnlazada() {
        cabeza = null;
        tamanio = 0;
    }

    // Agrega un elemento al final
    public void agregar(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            Nodo<T> actual = cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevo;
        }
        tamanio++;
    }

    // Obtiene el dato en una posición
    public T obtener(int indice) {
        verificarIndice(indice);
        Nodo<T> actual = cabeza;
        for (int i = 0; i < indice; i++) {
            actual = actual.siguiente;
        }
        return actual.dato;
    }

    // Elimina el dato en una posición
    public void eliminar(int indice) {
        verificarIndice(indice);
        if (indice == 0) {
            cabeza = cabeza.siguiente;
        } else {
            Nodo<T> anterior = cabeza;
            for (int i = 0; i < indice - 1; i++) {
                anterior = anterior.siguiente;
            }
            anterior.siguiente = anterior.siguiente.siguiente;
        }
        tamanio--;
    }

    // Retorna el tamaño actual
    public int tamanio() {
        return tamanio;
    }

    // Verifica si la lista está vacía
    public boolean estaVacia() {
        return tamanio == 0;
    }

    // Limpia la lista
    public void limpiar() {
        cabeza = null;
        tamanio = 0;
    }

    private void verificarIndice(int indice) {
        if (indice < 0 || indice >= tamanio) {
            throw new IndexOutOfBoundsException("Índice inválido: " + indice);
        }
    }

    // Permite usar foreach con ListaEnlazada
    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private Nodo<T> actual = cabeza;

            @Override
            public boolean hasNext() {
                return actual != null;
            }

            @Override
            public T next() {
                if (!hasNext()) throw new NoSuchElementException();
                T dato = actual.dato;
                actual = actual.siguiente;
                return dato;
            }
        };
    }
}
