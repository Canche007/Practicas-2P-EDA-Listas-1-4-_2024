import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class Producto {
    String nombre;
    String clave;
    double precio;

    public Producto(String clave, String nombre, double precio) {
        this.clave = clave;
        this.nombre = nombre;
        this.precio = precio;
    }

    @Override
    public String toString() {
        return String.format("%-10s %-15s %-10s", "Clave: " + clave, "Nombre: " + nombre, "Precio: $" + precio);
    }
}

public class EliminarOrdenarProductosGUI extends JFrame {
    private List<Producto> productos = new ArrayList<>();
    private JTextArea areaResultados;
    private JTextField campoClave;
    private JTextField campoNombre;
    private JTextField campoPrecio;

    public EliminarOrdenarProductosGUI() {
        // Configurar la ventana principal
        setTitle("Gestion de Productos");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel de entrada de datos
        JPanel panelEntrada = new JPanel();
        panelEntrada.setLayout(new GridLayout(4, 2));

        JLabel etiquetaClave = new JLabel("Clave del Producto:");
        campoClave = new JTextField();
        JLabel etiquetaNombre = new JLabel("Nombre del Producto:");
        campoNombre = new JTextField();
        JLabel etiquetaPrecio = new JLabel("Precio:");
        campoPrecio = new JTextField();

        panelEntrada.add(etiquetaClave);
        panelEntrada.add(campoClave);
        panelEntrada.add(etiquetaNombre);
        panelEntrada.add(campoNombre);
        panelEntrada.add(etiquetaPrecio);
        panelEntrada.add(campoPrecio);

        // Boton para agregar productos
        JButton botonAgregar = new JButton("Agregar Producto");
        botonAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarProducto();
            }
        });

        // Boton para eliminar productos por clave
        JButton botonEliminar = new JButton("Eliminar Producto");
        botonEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarProducto();
            }
        });

        // Boton para mostrar productos ordenados y el costo total
        JButton botonMostrar = new JButton("Mostrar Productos");
        botonMostrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarProductosOrdenados();
            }
        });

        panelEntrada.add(botonAgregar);
        panelEntrada.add(botonEliminar);

        // Area para mostrar los resultados
        areaResultados = new JTextArea();
        areaResultados.setFont(new Font("Monospaced", Font.PLAIN, 12));
        areaResultados.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaResultados);

        // Anadir paneles a la ventana
        add(panelEntrada, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(botonMostrar, BorderLayout.SOUTH);
    }

    // Metodo para agregar un producto
    private void agregarProducto() {
        String clave = campoClave.getText();
        String nombre = campoNombre.getText();
        String precioStr = campoPrecio.getText();

        if (!clave.isEmpty() && !nombre.isEmpty() && !precioStr.isEmpty()) {
            try {
                double precio = Double.parseDouble(precioStr);
                Producto producto = new Producto(clave, nombre, precio);
                productos.add(producto);
                JOptionPane.showMessageDialog(this, "Producto agregado correctamente.");
                campoClave.setText("");
                campoNombre.setText("");
                campoPrecio.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese un precio valido.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.");
        }
    }

    // Metodo para eliminar un producto por clave
    private void eliminarProducto() {
        String clave = JOptionPane.showInputDialog("Ingrese la clave del producto a eliminar:");

        if (clave != null && !clave.isEmpty()) {
            Producto productoAEliminar = null;

            for (Producto producto : productos) {
                if (producto.clave.equals(clave)) {
                    productoAEliminar = producto;
                    break;
                }
            }

            if (productoAEliminar != null) {
                productos.remove(productoAEliminar);
                JOptionPane.showMessageDialog(this, "Producto eliminado correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "Producto no encontrado.");
            }
        }
    }

    // Metodo para mostrar productos ordenados por nombre y el costo total
    private void mostrarProductosOrdenados() {
        productos.sort(Comparator.comparing(producto -> producto.nombre));

        StringBuilder sb = new StringBuilder();
        double costoTotal = 0;

        sb.append(String.format("%-10s %-15s %-10s\n", "Clave", "Nombre", "Precio"));
        sb.append("------------------------------------------------\n");

        for (Producto producto : productos) {
            sb.append(producto).append("\n");
            costoTotal += producto.precio;
        }

        sb.append("------------------------------------------------\n");
        sb.append(String.format("%-25s $%.2f", "Costo Total:", costoTotal));
        areaResultados.setText(sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                EliminarOrdenarProductosGUI gui = new EliminarOrdenarProductosGUI();
                gui.setVisible(true);
            }
        });
    }
}
