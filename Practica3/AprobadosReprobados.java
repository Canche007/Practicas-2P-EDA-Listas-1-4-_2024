import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

class Alumno {
    String nombre;
    double calificacion;

    public Alumno(String nombre, double calificacion) {
        this.nombre = nombre;
        this.calificacion = calificacion;
    }

    @Override
    public String toString() {
        String estado = calificacion >= 7 ? "Aprobado" : "Reprobado";
        return nombre + " - Calificacion: " + calificacion + " (" + estado + ")";
    }
}

public class AprobadosReprobados extends JFrame {
    private List<Alumno> alumnos = new ArrayList<>();
    private JTextArea areaResultados;
    private JTextField campoNombre;
    private JTextField campoCalificacion;

    public AprobadosReprobados() {
        // Configurar la ventana principal
        setTitle("Registro de Alumnos");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel de entrada de datos
        JPanel panelEntrada = new JPanel();
        panelEntrada.setLayout(new GridLayout(3, 2));

        JLabel etiquetaNombre = new JLabel("Nombre del Alumno:");
        campoNombre = new JTextField();
        JLabel etiquetaCalificacion = new JLabel("Calificacion:");
        campoCalificacion = new JTextField();

        panelEntrada.add(etiquetaNombre);
        panelEntrada.add(campoNombre);
        panelEntrada.add(etiquetaCalificacion);
        panelEntrada.add(campoCalificacion);

        // Boton para registrar alumno
        JButton botonRegistrar = new JButton("Registrar Alumno");
        botonRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarAlumno();
            }
        });

        // Boton para mostrar resultados
        JButton botonMostrarResultados = new JButton("Mostrar Alumnos");
        botonMostrarResultados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarResultados();
            }
        });

        panelEntrada.add(botonRegistrar);
        panelEntrada.add(botonMostrarResultados);

        // Area para mostrar los resultados
        areaResultados = new JTextArea();
        areaResultados.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaResultados);

        // Añadir paneles a la ventana
        add(panelEntrada, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Metodo para registrar un alumno
    private void registrarAlumno() {
        String nombre = campoNombre.getText();
        String calificacionStr = campoCalificacion.getText();

        if (!nombre.isEmpty() && !calificacionStr.isEmpty()) {
            try {
                double calificacion = Double.parseDouble(calificacionStr);
                Alumno alumno = new Alumno(nombre, calificacion);
                alumnos.add(alumno);
                JOptionPane.showMessageDialog(this, "Alumno registrado correctamente.");
                campoNombre.setText("");  // Limpiar campos
                campoCalificacion.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese una calificacion valida.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.");
        }
    }

    // Metodo para mostrar los resultados
    private void mostrarResultados() {
        StringBuilder sb = new StringBuilder();
        for (Alumno alumno : alumnos) {
            sb.append(alumno).append("\n");
        }
        areaResultados.setText(sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                AprobadosReprobados gui = new AprobadosReprobados();
                gui.setVisible(true);
            }
        });
    }
}
