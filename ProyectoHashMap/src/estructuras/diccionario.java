package estructuras;

/**
 * Interfaz Diccionario para representar un mapa clave-valor.
 *
 * @param <K> Tipo de clave
 * @param <V> Tipo de valor
 */
public interface diccionario<K, V> {

    /**
     * Inserta un par clave-valor en la tabla.
     * Si la clave ya existe, su valor es actualizado.
     *
     * @param key   Clave del elemento
     * @param value Valor asociado
     */
    void put(K key, V value);

    /**
     * Recupera el valor asociado a una clave.
     *
     * @param key Clave a buscar
     * @return Valor asociado o null si no existe
     */
    V get(K key);

    /**
     * Elimina un par clave-valor a partir de su clave.
     *
     * @param key Clave del elemento a eliminar
     * @return El valor eliminado, o null si la clave no existe
     */
    V remove(K key);

    /**
     * Verifica si una clave existe en la tabla.
     *
     * @param key Clave a buscar
     * @return true si existe, false en caso contrario
     */
    boolean containsKey(K key);

    /**
     * Retorna el número total de elementos almacenados.
     *
     * @return Tamaño del diccionario
     */
    int size();
}
