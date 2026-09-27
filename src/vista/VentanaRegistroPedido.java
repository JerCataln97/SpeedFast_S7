package vista;

import controlador.ControladorPedidos;
import modelo.Pedido;
import javax.swing.*;

public class VentanaRegistroPedido extends JFrame {

    private JPanel panelRegistro;
    private JLabel lblDireccion;
    private JLabel lblTipo;
    private JLabel lblVentRegistro;
    private JTextField txtDireccion;
    private JComboBox<String> cmbTipo;
    private JButton btnGuardar;
    private JButton btnLimpiar;

    //Controlador
    private final ControladorPedidos controlador;

    //Constructor
    public VentanaRegistroPedido(ControladorPedidos controlador) {
        this.controlador = controlador;

        setContentPane(panelRegistro);

        configurarVentana();
        configurarComponentes();
        configurarEventos();
    }

    //Configuracion de la ventana
    private void configurarVentana() {

        setTitle("Speed Fast - Registrar Pedido");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    //Configuracion de los componentes
    private void configurarComponentes() {

        cmbTipo.removeAllItems();

        cmbTipo.addItem("COMIDA");
        cmbTipo.addItem("ENCOMIENDA");
        cmbTipo.addItem("EXPRESS");
    }

    //Configuracion de eventos
    private void configurarEventos() {

        btnGuardar.addActionListener(e -> agregarPedido());

        btnLimpiar.addActionListener(e -> limpiarCampos());
    }

    //Metodo para agregar pedido
    private void agregarPedido() {

        try {

            //Obtiene la direccion y el tipo
            String direccion = txtDireccion.getText().trim();
            String tipo = (String) cmbTipo.getSelectedItem();

            //Validacion de direccion
            if (direccion.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Ingrese la dirección de entrega.",
                        "Datos incompletos",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            //Validacion de tipo
            if (tipo == null || tipo.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Seleccione un tipo de pedido.",
                        "Datos incompletos",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            //Crea objeto Pedido
            Pedido nuevoPedido = new Pedido(direccion, tipo);

            //Envia el pedido al controlador
            boolean guardado = controlador.agregarPedido(nuevoPedido);

            //Verifica si se guardo correctamente
            if (!guardado) {
                JOptionPane.showMessageDialog(
                        this,
                        "No se pudo guardar el pedido en la base de datos.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            int idGenerado = nuevoPedido.getId();

            JOptionPane.showMessageDialog(
                    this,
                    "Pedido guardado correctamente.\n"
                            + "ID generado: #" + idGenerado,
                    "Registro exitoso",
                    JOptionPane.INFORMATION_MESSAGE
            );

            limpiarCampos();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Ocurrió un error al registrar el pedido:\n"
                            + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }

    //Limpia los campos
    private void limpiarCampos() {

        txtDireccion.setText("");

        if (cmbTipo.getItemCount() > 0) {
            cmbTipo.setSelectedIndex(0);
        }

        txtDireccion.requestFocus();
    }
}