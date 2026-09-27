package vista;

import dao.RepartidorDAO;
import modelo.Repartidor;
import javax.swing.*;

public class VentanaRegistroRepartidor extends JFrame {

    private JPanel panelRegistroR;
    private JLabel lblNombre;
    private JTextField txtNombre;
    private JButton btnGuardar;
    private JButton btnLimpiar;
    private JLabel lblVentRegistroR;

    private final RepartidorDAO repartidorDAO;

    //Constructor
    public VentanaRegistroRepartidor() {
        repartidorDAO = new RepartidorDAO();

        setContentPane(panelRegistroR);

        configurarVentana();
        configurarEventos();
    }

    //Configurcion de la ventana
    private void configurarVentana() {

        setTitle("SpeedFast - Registrar Repartidor");
        setSize(400, 220);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    //Configuracion de eventos
    private void configurarEventos() {

        btnGuardar.addActionListener(e -> guardarRepartidor());

        btnLimpiar.addActionListener(e -> limpiarCampos());
    }

    //Metodo para registrar repartidor
    private void guardarRepartidor() {

        String nombre = txtNombre.getText().trim();

        //Validacion de nombre
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Ingrese el nombre del repartidor.",
                    "Datos incompletos",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        //Crea el objeto repartidor
        Repartidor repartidor = new Repartidor(0, nombre);

        boolean guardado = repartidorDAO.guardar(repartidor);

        if (guardado) {
            JOptionPane.showMessageDialog(
                    this,
                    "Repartidor registrado correctamente.",
                    "Registro exitoso",
                    JOptionPane.INFORMATION_MESSAGE
            );

            limpiarCampos();

        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo registrar el repartidor.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    //Limpia los campos
    private void limpiarCampos() {

        txtNombre.setText("");
        txtNombre.requestFocus();
    }
}