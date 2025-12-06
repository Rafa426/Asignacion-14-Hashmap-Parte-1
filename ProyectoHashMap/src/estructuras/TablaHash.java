package estructuras;

import java.util.LinkedList;

/**
 * Implementación de Tabla Hash utilizando Encadenamiento Separado.
 * Incluye rehash dinámico cuando el factor de carga supera 0.75.
 *
 * @param <K> Tipo de clave
 * @param <V> Tipo de valor
 */
public class TablaHash<K, V> implements diccionario<K, V> {

    /** Clase interna Nodo para almacenar pares clave-valor */
    private static class Nodo<K, V> {
        K key;
        V value;

        /**
         * Constructor del nodo.
         *
         * @param key   Clave del elemento
         * @param value Valor asociado
         */
        public Nodo(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private LinkedList<Nodo<K, V>>[] tabla; // Buckets
    private int size;                       // Cantidad de elementos (N)
    private int capacidad;                  // Tamaño del arreglo (M)

    private static final double FACTOR_CARGA_MAX = 0.75;

    /**
     * Constructor por defecto.
     * Capacidad inicial de 11 (número primo).
     */
    @SuppressWarnings("unchecked")
    public TablaHash() {
        this.capacidad = 11;
        this.tabla = new LinkedList[capacidad];
        this.size = 0;

        // Inicializar cada bucket
        for (int i = 0; i < capacidad; i++) {
            tabla[i] = new LinkedList<>();
        }
    }

    /**
     * Calcula el índice hash para una clave.
     * Se asegura de eliminar valores negativos.
     *
     * @param key La clave a procesar
     * @return Índice válido en el rango [0, capacidad-1]
     */
    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % capacidad;
    }

    /**
     * Inserta un par clave-valor en la tabla hash.
     * Si la clave ya existe, se actualiza su valor.
     * Verifica el factor de carga y redimensiona si es necesario.
     *
     * Complejidad: O(1) promedio, O(n) en caso de resize.
     *
     * @param key   Clave del elemento
     * @param value Valor asociado
     * @throws NullPointerException si la clave es null
     */
    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new NullPointerException("La clave no puede ser null.");
        }

        int indice = hash(key);
        LinkedList<Nodo<K, V>> lista = tabla[indice];

        // Buscar clave existente
        for (Nodo<K, V> nodo : lista) {
            if (nodo.key.equals(key)) {
                nodo.value = value;
                return;
            }
        }

        // Insertar nuevo nodo
        lista.add(new Nodo<>(key, value));
        size++;

        // Revisar factor de carga
        if ((double) size / capacidad >= FACTOR_CARGA_MAX) {
            resize();
        }
    }

    /**
     * Retorna el valor asociado a una clave.
     *
     * @param key Clave a buscar
     * @return Valor asociado o null si no existe
     */
    @Override
    public V get(K key) {
        int indice = hash(key);
        LinkedList<Nodo<K, V>> lista = tabla[indice];

        for (Nodo<K, V> nodo : lista) {
            if (nodo.key.equals(key)) {
                return nodo.value;
            }
        }

        return null;
    }

    /**
     * Verifica si una clave existe en la tabla hash.
     *
     * @param key Clave a verificar
     * @return true si existe, false si no
     */
    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    /**
     * Elimina una clave de la tabla hash.
     *
     * @param key Clave del elemento a eliminar
     * @return Valor eliminado o null si la clave no existe
     */
    @Override
    public V remove(K key) {
        int indice = hash(key);
        LinkedList<Nodo<K, V>> lista = tabla[indice];

        for (int i = 0; i < lista.size(); i++) {
            Nodo<K, V> nodo = lista.get(i);
            if (nodo.key.equals(key)) {
                lista.remove(i);
                size--;
                return nodo.value;
            }
        }
        return null;
    }

    /**
     * Retorna el número total de elementos almacenados.
     *
     * @return Tamaño actual
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Redimensiona la tabla (rehashing) duplicando su capacidad
     * y reinsertando todos los elementos.
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        LinkedList<Nodo<K, V>>[] tablaVieja = tabla;

        capacidad = capacidad * 2;
        tabla = new LinkedList[capacidad];

        for (int i = 0; i < capacidad; i++) {
            tabla[i] = new LinkedList<>();
        }

        size = 0; // Será actualizado por put()

        // Reinsertar todos los elementos
        for (LinkedList<Nodo<K, V>> bucket : tablaVieja) {
            for (Nodo<K, V> nodo : bucket) {
                put(nodo.key, nodo.value);
            }
        }
    }
}
