package vista;

import dao.RepartidorDAO;
import modelo.Repartidor;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VentanaListaRepartidores extends JFrame {

    private JPanel panelListaR;
    private JTable tablaRepartidores;
    private JButton btnEliminar;
    private JLabel lblVentListarR;

    private DefaultTableModel modeloTabla;

    private final RepartidorDAO repartidorDAO;

    //Constructor
    public VentanaListaRepartidores() {
        repartidorDAO = new RepartidorDAO();

        setContentPane(panelListaR);

        configurarVentana();
        configurarTabla();
        configurarEventos();

        actualizarTabla();
    }

    //Configuracion de la ventana
    private void configurarVentana() {

        setTitle("SpeedFast - Lista de Repartidores");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    //Configuracion de la tabla
    private void configurarTabla() {

        modeloTabla = new DefaultTableModel(
                new Object[]{
                        "ID",
                        "Nombre"
                },
                0
        ) {

            //Evita que se pueda editar la tabla
            @Override
            public boolean isCellEditable(
                    int row,
                    int column
            ) {
                return false;
            }
        };

        tablaRepartidores.setModel(modeloTabla);
    }

    //Configuracion de eventos
    private void configurarEventos() {

        btnEliminar.addActionListener(e -> eliminarRepartidor());
    }

    //Metodo actualiza los repartidores desde MySQL
    private void actualizarTabla() {

        modeloTabla.setRowCount(0);

        for (Repartidor repartidor :
                repartidorDAO.listarTodos()) {

            modeloTabla.addRow(
                    new Object[]{
                            repartidor.getId(),
                            repartidor.getNombre()
                    }
            );
        }
    }

    //Metodo para elminar repartidor
    private void eliminarRepartidor() {

        int fila = tablaRepartidores.getSelectedRow();

        //Comprueba si selecciono repartidor
        if (fila == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un repartidor para eliminar.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        //Obtiene el ID de la columna 0
        int id = (int) modeloTabla.getValueAt(fila, 0);

        String nombre = (String) modeloTabla.getValueAt(fila, 1);

        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de eliminar al repartidor\n"
                        + nombre + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }

        boolean eliminado = repartidorDAO.eliminar(id);

        if (eliminado) {
            JOptionPane.showMessageDialog(
                    this,
                    "Repartidor eliminado correctamente.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE
            );

            actualizarTabla();

        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "No se puede eliminar el repartidor.\n"
                            + "Puede tener entregas asociadas.",
                    "No se puede eliminar",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }
}