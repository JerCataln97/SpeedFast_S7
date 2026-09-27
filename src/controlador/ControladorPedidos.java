package controlador;

import dao.EntregaDAO;
import dao.PedidoDAO;
import modelo.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ControladorPedidos {

    private final PedidoDAO pedidoDAO;
    private final EntregaDAO entregaDAO;

    //Constructor
    public ControladorPedidos() {
        pedidoDAO = new PedidoDAO();
        entregaDAO = new EntregaDAO();
    }

    //Metodo para guardar pedidos en la base de datos
    public boolean agregarPedido(Pedido pedido) {
        int idGenerado = pedidoDAO.guardar(pedido);
        return idGenerado != -1;
    }

    //Metodo para obtener los pedidos desde la base de datos
    public List<Pedido> obtenerPedidos() {
        return pedidoDAO.listar();
    }

    //Metodo para buscar pedidos por el ID
    public Pedido buscarPorId(int id) {
        return pedidoDAO.buscarPorId(id);
    }

    //Metodo para asignar repartidor a un pedido
    public boolean asignarRepartidor(int idPedido, Repartidor repartidor) {

        //Valida que haya repartidor
        if (repartidor == null) {
            return false;
        }

        //Busca pedidos en la base de datos
        Pedido pedido = pedidoDAO.buscarPorId(idPedido);
        if (pedido == null) {
            return false;
        }

        //Evita asignar otro repartidor si ya tiene repartidor asignado
        if (entregaDAO.existePorPedido(idPedido)) {
            return false;
        }

        //Crea un objeto Entrega
        Entrega entrega = new Entrega(
                idPedido,
                repartidor.getId(),
                LocalDate.now(),
                LocalTime.now()
        );

        //Guarda la entrega en la base de datos
        boolean guardado = entregaDAO.guardar(entrega);

        if (guardado) {
            pedido.setRepartidor(repartidor);
            return true;
        }

        return false;
    }

    //Metodo para eliminar pedido
    public boolean eliminarPedido(int id) {
        return pedidoDAO.eliminar(id);
    }
}
