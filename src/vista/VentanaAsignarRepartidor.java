package vista;

import controlador.ControladorPedidos;
import dao.RepartidorDAO;
import modelo.Pedido;
import modelo.Repartidor;
import javax.swing.*;

public class VentanaAsignarRepartidor extends JFrame {

    private JPanel panelAsignar;
    private JLabel lblPedido;
    private JLabel lblRepartidor;
    private JComboBox<Pedido> cmbPedido;
    private JComboBox<Repartidor> cmbRepartidor;
    private JButton btnAsignar;
    private JLabel lblVentAsignar;

    //Controlador
    private final ControladorPedidos controlador;

    //Constructor
    public VentanaAsignarRepartidor(ControladorPedidos controlador) {
        this.controlador = controlador;

        setContentPane(panelAsignar);

        configurarVentana();
        configurarComponentes();
        configurarEventos();
    }

    //Configuracion de la ventana
    private void configurarVentana() {

        setTitle("SpeedFast - Asignar Repartidor");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    //Configuracion de los componentes
    private void configurarComponentes() {

        //Carga los pedidos
        cmbPedido.removeAllItems();

        for (Pedido pedido : controlador.obtenerPedidos()) {

            cmbPedido.addItem(pedido);
        }

        //Carga los repartidores
        cmbRepartidor.removeAllItems();

        RepartidorDAO repartidorDAO = new RepartidorDAO();

        for (Repartidor repartidor : repartidorDAO.listarTodos()) {

            cmbRepartidor.addItem(repartidor);
        }
    }

    //Configuracion de los eventos
    private void configurarEventos() {

        btnAsignar.addActionListener(e -> asignarRepartidor());
    }

    //Metodo para asignar repartidor
    private void asignarRepartidor() {

        Pedido pedSeleccionado = (Pedido) cmbPedido.getSelectedItem();
        Repartidor repSeleccionado = (Repartidor) cmbRepartidor.getSelectedItem();

        //Validacion de pedido
        if (pedSeleccionado == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar un pedido.",
                    "Datos incompletos",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        //Validacion de repartidor
        if (repSeleccionado == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar un repartidor.",
                    "Datos incompletos",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        //Le entrega los datos al controlador
        boolean asignado = controlador.asignarRepartidor(
                pedSeleccionado.getId(),
                repSeleccionado
        );

        if (asignado) {

            JOptionPane.showMessageDialog(
                    this,
                    "Pedido #" + pedSeleccionado.getId()
                            + " asignado correctamente a "
                            + repSeleccionado.getNombre()
                            + ".",
                    "Asignación exitosa",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No fue posible asignar el repartidor.\n"
                            + "El pedido puede tener un repartidor "
                            + "ya asignado.",
                    "Error de asignación",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
