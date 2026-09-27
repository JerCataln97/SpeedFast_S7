package dao;

import modelo.Pedido;
import modelo.Repartidor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    //Metodo para guardar pedido en la base de datos
    public int guardar(Pedido pedido) {

        String sql = """
                INSERT INTO pedido (direccion, tipo, estado)
                VALUES (?, ?, ?)
                """;

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement sentencia = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {

            if (conexion == null) {
                return -1;
            }

            sentencia.setString(1, pedido.getDireccionEntrega());
            sentencia.setString(2, pedido.getTipo());
            sentencia.setString(3, "PENDIENTE");

            sentencia.executeUpdate();

            //Obtiene el ID generado por MySQL
            try (ResultSet resultado = sentencia.getGeneratedKeys()) {

                if (resultado.next()) {
                    int idGenerado = resultado.getInt(1);
                    pedido.setId(idGenerado);

                    return idGenerado;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al guardar el pedido.");

            e.printStackTrace();
        }

        return -1;
    }

    //Metodo para obtener todoss los pedidos desde MySQL
    public List<Pedido> listar() {

        List<Pedido> pedidos = new ArrayList<>();

        String sql = """
                SELECT
                    p.id,
                    p.direccion,
                    p.tipo,
                    p.estado,
                    r.id AS repartidor_id,
                    r.nombre AS repartidor_nombre
                FROM pedido p
                LEFT JOIN entrega e
                    ON p.id = e.id_pedido
                LEFT JOIN repartidor r
                    ON e.id_repartidor = r.id
                ORDER BY p.id
                """;

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement sentencia = conexion.prepareStatement(sql);
                ResultSet resultado = sentencia.executeQuery()
        ) {

            if (conexion == null) {
                return pedidos;
            }

            while (resultado.next()) {

                Pedido pedido = new Pedido(
                        resultado.getInt("id"),
                        resultado.getString("direccion"),
                        resultado.getString("tipo")
                );

                int repartidorId = resultado.getInt("repartidor_id");

                if (!resultado.wasNull()) {

                    String nombreRepartidor = resultado.getString("repartidor_nombre");

                    Repartidor repartidor = new Repartidor(repartidorId, nombreRepartidor);

                    pedido.setRepartidor(repartidor);
                }

                pedidos.add(pedido);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar los pedidos.");

            e.printStackTrace();
        }

        return pedidos;
    }

    //Metodo para buscar pedido por ID
    public Pedido buscarPorId(int id) {

        String sql = """
                SELECT
                    p.id,
                    p.direccion,
                    p.tipo,
                    p.estado,
                    r.id AS repartidor_id,
                    r.nombre AS repartidor_nombre
                FROM pedido p
                LEFT JOIN entrega e
                    ON p.id = e.id_pedido
                LEFT JOIN repartidor r
                    ON e.id_repartidor = r.id
                WHERE p.id = ?
                """;

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement sentencia = conexion.prepareStatement(sql)
        ) {

            if (conexion == null) {
                return null;
            }

            sentencia.setInt(1, id);

            try (ResultSet resultado = sentencia.executeQuery()) {

                if (resultado.next()) {

                    Pedido pedido = new Pedido(
                            resultado.getInt("id"),
                            resultado.getString("direccion"),
                            resultado.getString("tipo")
                    );

                    int repartidorId = resultado.getInt("repartidor_id");

                    if (!resultado.wasNull()) {

                        String nombreRepartidor = resultado.getString("repartidor_nombre");

                        Repartidor repartidor = new Repartidor(repartidorId, nombreRepartidor);

                        pedido.setRepartidor(repartidor);
                    }

                    return pedido;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar el pedido.");

            e.printStackTrace();
        }

        return null;
    }

    //Metodo para actualizar el estado de un pedido
    public boolean actualizarEstado(
            int idPedido,
            String estado
    ) {

        String sql = """
                UPDATE pedido
                SET estado = ?
                WHERE id = ?
                """;

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement sentencia = conexion.prepareStatement(sql)
        ) {

            if (conexion == null) {
                return false;
            }

            sentencia.setString(1, estado);
            sentencia.setInt(2, idPedido);

            int filas = sentencia.executeUpdate();

            return filas > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar el estado del pedido.");

            e.printStackTrace();

            return false;
        }
    }

    //Metodo para eliminar un pedido
    public boolean eliminar(int id) {

        //SQL para eliminar entrega y pedido
        String eliminarEntrega = "DELETE FROM entrega WHERE id_pedido = ?";
        String eliminarPedido = "DELETE FROM pedido WHERE id = ?";

        Connection conexion = null;

        try {
            conexion = ConexionBD.conectar();

            if (conexion == null) {
                return false;
            }

            conexion.setAutoCommit(false);

            //Elmina primero la entrega
            try (PreparedStatement sentencia = conexion.prepareStatement(eliminarEntrega)
            ) {
                sentencia.setInt(1, id);
                sentencia.executeUpdate();
            }

            //Luego elimina el pedido
            try (PreparedStatement sentencia = conexion.prepareStatement(eliminarPedido)
            ) {

                sentencia.setInt(1, id);

                int filas = sentencia.executeUpdate();

                if (filas > 0) {
                    conexion.commit();
                    return true;

                } else {
                    conexion.rollback();
                    return false;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al eliminar el pedido.");

            e.printStackTrace();

            try {

                if (conexion != null) {
                    conexion.rollback();
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            return false;

        } finally {

            try {

                if (conexion != null) {

                    conexion.setAutoCommit(true);
                    conexion.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}