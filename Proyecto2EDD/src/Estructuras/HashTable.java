package Estructuras;

/**
 * Tabla de dispersión genérica con resolución de colisiones mediante encadenamiento
 * Implementa un factor de carga de 0.75 y redimensionamiento automático
 * 
 * @param <K> Tipo de las claves
 * @param <V> Tipo de los valores
 * @author Elohym Casais, Grazia Di Cecilia, Andres Colmenares
 */
public class HashTable<K, V> {
    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node<K, V>[] buckets;
    private int size;
    private static final float LOAD_FACTOR = 0.75f;

    /**
     * Constructor que inicializa la tabla con capacidad inicial de 16
     */
    @SuppressWarnings("unchecked")
    public HashTable() {
        buckets = new Node[16];
        size = 0;
    }

    /**
     * Calcula el índice de dispersión para una clave
     * @param key Clave
     * @return Índice en el arreglo de cubetas
     */
    private int hash(K key) {
        return Math.abs(key.hashCode()) % buckets.length;
    }

    /**
     * Inserta o actualiza el valor asociado a una clave
     * @param key Clave
     * @param value Valor
     */
    public void put(K key, V value) {
        int idx = hash(key);
        Node<K, V> current = buckets[idx];
        while (current != null) {
            if (current.key.equals(key)) {
                current.value = value;
                return;
            }
            current = current.next;
        }
        // Insertar al inicio de la lista
        Node<K, V> newNode = new Node<>(key, value);
        newNode.next = buckets[idx];
        buckets[idx] = newNode;
        size++;

        // Redimensionar si es necesario
        if ((float) size / buckets.length > LOAD_FACTOR) {
            rehash();
        }
    }

    /**
     * Obtiene el valor asociado a una clave
     * @param key Clave.
     * @return Valor asociado o null si no existe
     */
    public V get(K key) {
        int idx = hash(key);
        Node<K, V> current = buckets[idx];
        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    /**
     * Elimina la entrada correspondiente a una clave
     * @param key Clave.
     * @return Valor que estaba asociado, o null si no existía
     */
    public V remove(K key) {
        int idx = hash(key);
        Node<K, V> current = buckets[idx];
        Node<K, V> prev = null;
        while (current != null) {
            if (current.key.equals(key)) {
                if (prev == null) {
                    buckets[idx] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return current.value;
            }
            prev = current;
            current = current.next;
        }
        return null;
    }

    /**
     * Verifica si existe una clave en la tabla
     * @param key Clave.
     * @return true si existe, false en caso contrario
     */
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    /**
     * Devuelve un arreglo con todas las claves almacenadas
     * @return Arreglo de claves (puede contener null si la tabla está vacía)
     */
    @SuppressWarnings("unchecked")
    public Object[] keys() {
        Object[] keysArray = new Object[size];
        int index = 0;
        for (Node<K, V> bucket : buckets) {
            Node<K, V> current = bucket;
            while (current != null) {
                keysArray[index++] = current.key;
                current = current.next;
            }
        }
        return keysArray;
    }

    /**
     * Devuelve el número de entradas en la tabla
     * @return tamaño.
     */
    public int size() {
        return size;
    }

    /**
     * Redimensiona la tabla al doble de su capacidad actual y reinserta todas las entradas
     */
    @SuppressWarnings("unchecked")
    private void rehash() {
        Node<K, V>[] oldBuckets = buckets;
        buckets = new Node[oldBuckets.length * 2];
        size = 0;
        for (Node<K, V> node : oldBuckets) {
            while (node != null) {
                put(node.key, node.value);
                node = node.next;
            }
        }
    }
}
