package vista;

import controlador.ControladorPedidos;
import modelo.Pedido;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VentanaListaPedidos extends JFrame {

    private JPanel panelLista;
    private JTable tablaPedidos;
    private JLabel lblVentListar;
    private JButton btnEliminar;

    //Controlador
    private final ControladorPedidos controlador;

    private DefaultTableModel modeloTabla;

    //Constructor
    public VentanaListaPedidos(ControladorPedidos controlador) {
        this.controlador = controlador;

        setTitle("SpeedFast - Lista de Pedidos");
        setSize(650, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setContentPane(panelLista);

        configurarTabla();
        configurarEventos();
        actualizarTabla();
    }

    //Configuracion de eventos
    private void configurarEventos() {

        btnEliminar.addActionListener(e -> eliminarPedido());
    }

    //Configuracion de la tabla
    private void configurarTabla() {

        modeloTabla = new DefaultTableModel(
                new Object[]{
                        "ID",
                        "Dirección",
                        "Tipo",
                        "Repartidor"
                },
                0
        ) {

            //Evita que la tabla pueda mofificarse
            @Override
            public boolean isCellEditable(
                    int row,
                    int column
            ) {
                return false;
            }
        };

        tablaPedidos.setModel(modeloTabla);
    }

    //Metodo para elminar pedido
    private void eliminarPedido() {

        int fila = tablaPedidos.getSelectedRow();

        //Comprueba si selecciono un pedido
        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un pedido para eliminar.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        //Obtiene el ID de la fila seleccionada
        int id = (int) modeloTabla.getValueAt(fila, 0);

        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de eliminar el pedido #"
                        + id + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }

        //Elimina mediante el controlador
        boolean eliminado = controlador.eliminarPedido(id);

        if (eliminado) {
            JOptionPane.showMessageDialog(
                    this,
                    "Pedido eliminado correctamente.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE
            );

            actualizarTabla();

        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo eliminar el pedido.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    //Metodo para actualizar la tabla
    public void actualizarTabla() {

        modeloTabla.setRowCount(0);

        for (Pedido pedido : controlador.obtenerPedidos()) {

            String nombreRepartidor = "Sin asignar";

            if (pedido.getRepartidor() != null) {

                nombreRepartidor = pedido.getRepartidor().getNombre();
            }

            modeloTabla.addRow(
                    new Object[]{
                            pedido.getId(),
                            pedido.getDireccionEntrega(),
                            pedido.getTipo(),
                            nombreRepartidor
                    }
            );
        }
    }
}