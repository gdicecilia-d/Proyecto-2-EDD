package Estructuras;

/**
 * Montículo binario (min-heap) genérico
 * Los elementos deben ser comparables y tener un identificador único
 * Mantiene un mapeo de IDs a índices mediante una tabla hash para permitir
 * eliminación por ID en O(log n)
 * 
 * @param <T> Tipo de los elementos, debe extender Comparable e implementar Identificable
 * @author Elohym Casais, Grazia Di Cecilia, Andres Colmenares
 */
public class BinaryHeap<T extends Comparable<T> & Identificable> {
    private Object[] heap;
    private int size;
    private HashTable<Integer, Integer> idToIndex; // id es índice en el arreglo

    /**
     * Constructor con capacidad inicial
     * @param capacidadInicial Capacidad inicial del arreglo
     */
    public BinaryHeap(int capacidadInicial) {
        heap = new Object[capacidadInicial];
        size = 0;
        idToIndex = new HashTable<>();
    }

    /**
     * Asegura que haya espacio suficiente para un nuevo elemento
     */
    private void ensureCapacity() {
        if (size == heap.length) {
            Object[] newHeap = new Object[heap.length * 2];
            System.arraycopy(heap, 0, newHeap, 0, heap.length);
            heap = newHeap;
        }
    }

    /**
     * Inserta un nuevo elemento en el montículo
     * @param elemento Elemento a insertar
     */
    public void insertar(T elemento) {
        ensureCapacity();
        heap[size] = elemento;
        idToIndex.put(elemento.getId(), size);
        percolateUp(size);
        size++;
    }

    /**
     * Elimina y retorna el elemento de menor prioridad (la raíz)
     * @return El elemento mínimo, o null si el montículo está vacío
     */
    @SuppressWarnings("unchecked")
    public T eliminarMin() {
        if (size == 0) return null;
        T min = (T) heap[0];
        idToIndex.remove(min.getId());
        size--;
        if (size > 0) {
            heap[0] = heap[size];
            idToIndex.put(((T) heap[0]).getId(), 0);
            percolateDown(0);
        }
        heap[size] = null; // ayuda al GC
        return min;
    }

    /**
     * Retorna el elemento mínimo sin eliminarlo
     * @return Elemento mínimo, o null si está vacío
     */
    @SuppressWarnings("unchecked")
    public T obtenerMin() {
        return (size == 0) ? null : (T) heap[0];
    }

    /**
     * Elimina un elemento dado su identificador
     * @param id Identificador del elemento a eliminar
     * @return El elemento eliminado, o null si no se encontró
     */
    @SuppressWarnings("unchecked")
    public T eliminarPorId(int id) {
        Integer index = idToIndex.get(id);
        if (index == null) return null;

        T elemento = (T) heap[index];
        // Intercambiar con el último elemento
        int lastIndex = size - 1;
        if (index != lastIndex) {
            T lastElem = (T) heap[lastIndex];
            heap[index] = lastElem;
            idToIndex.put(lastElem.getId(), index);
            heap[lastIndex] = null;
            size--;
            // Reajustar el elemento movido
            percolateUp(index);
            percolateDown(index);
        } else {
            heap[lastIndex] = null;
            size--;
        }
        idToIndex.remove(id);
        return elemento;
    }

    /**
     * Obtiene el elemento con un identificador dado
     * @param id Identificador
     * @return Elemento o null si no existe
     */
    @SuppressWarnings("unchecked")
    public T obtenerElemento(int id) {
        Integer index = idToIndex.get(id);
        return (index == null) ? null : (T) heap[index];
    }

    /**
     * Devuelve el número de elementos en el montículo
     * @return tamaño
     */
    public int tamano() {
        return size;
    }

    /**
     * Indica si el montículo está vacío
     * @return true si está vacío
     */
    public boolean estaVacio() {
        return size == 0;
    }

    /**
     * Retorna una copia del arreglo interno 
     * El arreglo puede contener nulls en posiciones no ocupadas
     * @return Arreglo con los elementos (en orden de nivel)
     */
    public Object[] obtenerArreglo() {
        Object[] copy = new Object[size];
        System.arraycopy(heap, 0, copy, 0, size);
        return copy;
    }

    /**
     * Reubica un elemento hacia arriba en el montículo
     * @param index Índice del elemento
     */
    @SuppressWarnings("unchecked")
    private void percolateUp(int index) {
        T elemento = (T) heap[index];
        while (index > 0) {
            int parent = (index - 1) / 2;
            T parentElem = (T) heap[parent];
            if (elemento.compareTo(parentElem) >= 0) break;
            // Intercambiar con el padre
            heap[index] = parentElem;
            idToIndex.put(parentElem.getId(), index);
            index = parent;
        }
        heap[index] = elemento;
        idToIndex.put(elemento.getId(), index);
    }

    /**
     * Reubica un elemento hacia abajo en el montículo
     * @param index Índice del elemento
     */
    @SuppressWarnings("unchecked")
    private void percolateDown(int index) {
        T elemento = (T) heap[index];
        int child;
        while ((child = 2 * index + 1) < size) {
            // Seleccionar el hijo más pequeño
            if (child + 1 < size && ((T) heap[child + 1]).compareTo((T) heap[child]) < 0) {
                child++;
            }
            if (elemento.compareTo((T) heap[child]) <= 0) break;
            // Intercambiar con el hijo
            heap[index] = heap[child];
            idToIndex.put(((T) heap[child]).getId(), index);
            index = child;
        }
        heap[index] = elemento;
        idToIndex.put(elemento.getId(), index);
    }

    /**
     * Reubica un elemento por ID (cambia su prioridad al mínimo y lo elimina)
     * Este método se usa para eliminar un documento específico de la cola
     * según el mecanismo requerido por el proyecto: cambiar la etiqueta de tiempo
     * al valor más bajo, reubicarlo en la raíz y luego aplicar eliminarMin
     * 
     * @param id ID del elemento a eliminar
     * @return true si se eliminó, false si no se encontró
     */
    @SuppressWarnings("unchecked")
    public boolean reubicarPorId(int id) {
        Integer index = idToIndex.get(id);
        if (index == null) return false;
        
        // Cambiar prioridad al valor más bajo posible
        T elemento = (T) heap[index];
        
        // Se asume que T tiene un método setEtiquetaTiempo (RegistroImpresion lo tiene)
        // Esto permite modificar la prioridad del elemento
        ((Modelo.RegistroImpresion) elemento).setEtiquetaTiempo(Integer.MIN_VALUE);
        
        // Reubicar hacia arriba hasta la raíz
        percolateUp(index);
        
        // Eliminar la raíz (que ahora es este elemento)
        T eliminado = eliminarMin();
        return eliminado != null && eliminado.getId() == id;
    }
}
