package vista;

import controlador.ControladorPedidos;
import javax.swing.*;

public class VentanaPrincipal extends JFrame {

    private JPanel panelPrincipal;
    private JLabel lblVenPrincipal;
    private JButton btnRegistrar;
    private JButton btnListar;
    private JButton btnAsignar;
    private JButton btnRegistrarR;
    private JButton btnListarR;

    //Controlador
    private final ControladorPedidos controlador;

    //Constructor
    public VentanaPrincipal() {
        controlador = new ControladorPedidos();

        setContentPane(panelPrincipal);

        configurarVentana();
        configurarEventos();
    }

    //Configuracion de la ventana
    private void configurarVentana() {

        setTitle("SPEED FAST");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    //Configuracion de eventos
    private void configurarEventos() {

        btnRegistrar.addActionListener(e -> abrirRegistro());

        btnListar.addActionListener(e -> abrirListado());

        btnAsignar.addActionListener(e -> abrirAsignacion());

        btnRegistrarR.addActionListener(e -> abrirRegistroRepartidor());

        btnListarR.addActionListener(e -> abrirListadoRepartidores());
    }

    //Abre la ventana para registrar pedidos
    private void abrirRegistro() {

        VentanaRegistroPedido ventana = new VentanaRegistroPedido(controlador);

        ventana.setVisible(true);
    }

    //Abre la ventana con la lista de pedidos
    private void abrirListado() {

        VentanaListaPedidos ventana = new VentanaListaPedidos(controlador);

        ventana.setVisible(true);
    }

    //Abre la ventana para asignar repartidor
    private void abrirAsignacion() {

        if (controlador.obtenerPedidos().isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "No existen pedidos registrados.",
                    "SpeedFast",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        VentanaAsignarRepartidor ventana = new VentanaAsignarRepartidor(controlador);

        ventana.setVisible(true);
    }

    //Abre la ventana para registrar un nuevo repartidor
    private void abrirRegistroRepartidor() {

        VentanaRegistroRepartidor ventana = new VentanaRegistroRepartidor();

        ventana.setVisible(true);
    }

    //Abre la ventana con la lista de los repartidores
    private void abrirListadoRepartidores() {

        VentanaListaRepartidores ventana = new VentanaListaRepartidores();

        ventana.setVisible(true);
    }
}