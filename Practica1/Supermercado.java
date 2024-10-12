import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Producto {
    String nombre;
    int cantidad;
    double precio;

    public Producto(String nombre, int cantidad, double precio) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    @Override
    public String toString() {
        return nombre + " - Cantidad: " + cantidad + ", Precio: $" + String.format("%.2f", precio);
    }
}

public class Supermercado {
    private List<Producto> productosDisponibles = new ArrayList<>();
    private List<Producto> productosRetirados = new ArrayList<>();
    private int contadorProductos = 1;
    private Random random = new Random();

    // Método para agregar productos (recursivo)
    public void agregarProducto(int cantidadProductos) {
        if (cantidadProductos == 0) return;  // Caso base

        String nombreProducto = "producto" + contadorProductos;
        int cantidad = random.nextInt(10) + 1;  // Genera una cantidad aleatoria entre 1 y 10
        double precio = random.nextDouble() * 100;  // Genera un precio aleatorio entre 0 y 100

        Producto nuevoProducto = new Producto(nombreProducto, cantidad, precio);
        productosDisponibles.add(nuevoProducto);
        mostrarMensaje("Producto agregado: " + nuevoProducto, "Producto Agregado");

        contadorProductos++;
        agregarProducto(cantidadProductos - 1);  // Llamada recursiva
    }

    // Método para retirar un producto (recursivo)
    public void retirarProducto() {
        if (productosDisponibles.isEmpty()) {
            mostrarMensaje("No hay productos disponibles para retirar.", "Error");
            return;
        }

        // Mostrar productos disponibles en la ventana de retiro
        StringBuilder disponibles = new StringBuilder("Ingrese el nombre del producto a retirar:\n\nProductos Disponibles:\n");
        mostrarLista(productosDisponibles, disponibles, 0);

        // Crear un diálogo que muestre los productos disponibles
        String nombreProducto = JOptionPane.showInputDialog(null, disponibles.toString(), "Retirar Producto", JOptionPane.QUESTION_MESSAGE);

        if (nombreProducto != null) {
            Producto productoARetirar = encontrarProducto(nombreProducto, productosDisponibles, 0);
            if (productoARetirar != null) {
                productosDisponibles.remove(productoARetirar);
                productosRetirados.add(productoARetirar);
                mostrarMensaje("Producto retirado: " + productoARetirar, "Producto Retirado");
            } else {
                mostrarMensaje("Producto no encontrado.", "Error");
            }
        }
    }

    // Método recursivo para encontrar un producto en una lista
    private Producto encontrarProducto(String nombreProducto, List<Producto> lista, int index) {
        if (index >= lista.size()) return null;  // Caso base

        Producto producto = lista.get(index);
        if (producto.nombre.equals(nombreProducto)) {
            return producto;
        } else {
            return encontrarProducto(nombreProducto, lista, index + 1);  // Llamada recursiva
        }
    }

    // Método para mostrar productos disponibles y retirados
    public void mostrarProductos() {
        StringBuilder disponibles = new StringBuilder("Productos Disponibles:\n");
        mostrarLista(productosDisponibles, disponibles, 0);

        StringBuilder retirados = new StringBuilder("Productos Retirados:\n");
        mostrarLista(productosRetirados, retirados, 0);

        mostrarMensaje(disponibles.toString(), "Productos Disponibles");
        mostrarMensaje(retirados.toString(), "Productos Retirados");
    }

    // Método recursivo para mostrar una lista de productos
    private void mostrarLista(List<Producto> lista, StringBuilder sb, int index) {
        if (index >= lista.size()) return;  // Caso base

        sb.append(lista.get(index)).append("\n");
        mostrarLista(lista, sb, index + 1);  // Llamada recursiva
    }

    // Método para mostrar un mensaje en una ventana emergente
    private void mostrarMensaje(String mensaje, String titulo) {
        JTextArea textArea = new JTextArea(mensaje);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(350, 150));

        JOptionPane.showMessageDialog(null, scrollPane, titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        Supermercado supermercado = new Supermercado();

        String[] opciones = {"Agregar Producto", "Retirar Producto", "Mostrar Productos", "Salir"};
        int opcion;

        do {
            opcion = JOptionPane.showOptionDialog(null, "Seleccione una opcion:", "Supermercado",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);

            switch (opcion) {
                case 0: // Agregar Producto
                    String cantidadStr = JOptionPane.showInputDialog("Ingrese la cantidad de productos a agregar:");
                    if (cantidadStr != null) {
                        int cantidad = Integer.parseInt(cantidadStr);
                        supermercado.agregarProducto(cantidad);
                    }
                    break;
                case 1: // Retirar Producto
                    supermercado.retirarProducto();  // Ahora muestra los productos disponibles en el diálogo
                    break;
                case 2: // Mostrar Productos
                    supermercado.mostrarProductos();
                    break;
                case 3: // Salir
                    JOptionPane.showMessageDialog(null, "Gracias por usar el sistema de supermercado.");
                    break;
            }
        } while (opcion != 3);
    }
}
