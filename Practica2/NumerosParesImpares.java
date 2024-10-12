import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NumerosParesImpares {

    private List<Integer> numeros = new ArrayList<>();
    private List<Integer> pares = new ArrayList<>();
    private List<Integer> impares = new ArrayList<>();
    private Random random = new Random();

    // Metodo recursivo para generar una lista de numeros aleatorios
    public void generarNumeros(int cantidad) {
        if (cantidad == 0) return;  // Caso base

        int numero = random.nextInt(100) + 1;  // Generar un numero entre 1 y 100
        numeros.add(numero);

        generarNumeros(cantidad - 1);  // Llamada recursiva
    }

    // Metodo recursivo para separar los numeros pares e impares
    public void separarParesImpares(int index) {
        if (index >= numeros.size()) return;  // Caso base

        int numero = numeros.get(index);
        if (numero % 2 == 0) {
            pares.add(numero);
        } else {
            impares.add(numero);
        }

        separarParesImpares(index + 1);  // Llamada recursiva
    }

    // Metodo para mostrar los resultados en ventanas emergentes
    public void mostrarResultados() {
        StringBuilder listaNumeros = new StringBuilder("Lista de Numeros Generados:\n");
        mostrarLista(numeros, listaNumeros, 0);
        mostrarMensaje(listaNumeros.toString(), "Numeros Generados");

        StringBuilder listaPares = new StringBuilder("Lista de Numeros Pares:\n");
        mostrarLista(pares, listaPares, 0);
        mostrarMensaje(listaPares.toString(), "Numeros Pares");

        StringBuilder listaImpares = new StringBuilder("Lista de Numeros Impares:\n");
        mostrarLista(impares, listaImpares, 0);
        mostrarMensaje(listaImpares.toString(), "Numeros Impares");
    }

    // Metodo recursivo para mostrar una lista de numeros
    private void mostrarLista(List<Integer> lista, StringBuilder sb, int index) {
        if (index >= lista.size()) return;  // Caso base

        sb.append(lista.get(index)).append("\n");
        mostrarLista(lista, sb, index + 1);  // Llamada recursiva
    }

    // Metodo para mostrar un mensaje en una ventana emergente
    private void mostrarMensaje(String mensaje, String titulo) {
        JTextArea textArea = new JTextArea(mensaje);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(300, 200));

        JOptionPane.showMessageDialog(null, scrollPane, titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        NumerosParesImpares app = new NumerosParesImpares();

        String cantidadStr = JOptionPane.showInputDialog("Ingrese la cantidad de numeros a generar:");
        if (cantidadStr != null) {
            int cantidad = Integer.parseInt(cantidadStr);

            app.generarNumeros(cantidad);  // Generar los numeros aleatorios
            app.separarParesImpares(0);    // Separar numeros pares e impares
            app.mostrarResultados();       // Mostrar los resultados
        }
    }
}
