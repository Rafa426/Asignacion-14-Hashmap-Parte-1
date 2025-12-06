package test;

import estructuras.TablaHash;

public class TestTablaHash {
    public static void main(String[] args) {

        TablaHash<String, Integer> tabla = new TablaHash<>();

        tabla.put("A", 10);
        tabla.put("B", 20);
        tabla.put("C", 30);

        System.out.println("A: " + tabla.get("A"));
        System.out.println("B: " + tabla.get("B"));
        System.out.println("Existe C? " + tabla.containsKey("C"));

        System.out.println("Eliminando B: " + tabla.remove("B"));
        System.out.println("Existe B? " + tabla.containsKey("B"));

        System.out.println("Size final: " + tabla.size());
    }
}
