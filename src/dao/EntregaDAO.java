package dao;

import modelo.Entrega;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EntregaDAO {

    //Metodo para guardar entrega en la base de datos
    public boolean guardar(Entrega entrega) {

        String sql = """
                INSERT INTO entrega
                (id_pedido, id_repartidor, fecha, hora)
                VALUES (?, ?, ?, ?)
                """;

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement sentencia = conexion.prepareStatement(sql)
        ) {

            if (conexion == null) {
                return false;
            }

            sentencia.setInt(1, entrega.getPedidoId());
            sentencia.setInt(2, entrega.getRepartidorId());
            sentencia.setDate(3, java.sql.Date.valueOf(entrega.getFecha()));
            sentencia.setTime(4, java.sql.Time.valueOf(entrega.getHora()));

            sentencia.executeUpdate();

            System.out.println("Entrega guardada correctamente.");

            return true;

        } catch (SQLException e) {
            System.out.println("Error al guardar la entrega.");

            e.printStackTrace();

            return false;
        }
    }

    //Metodo para comprobar si un pedido ya tiene una entrega
    public boolean existePorPedido(int idPedido) {

        String sql = """
                SELECT COUNT(*)
                FROM entrega
                WHERE id_pedido = ?
                """;

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement sentencia = conexion.prepareStatement(sql)
        ) {

            if (conexion == null) {
                return false;
            }

            sentencia.setInt(1, idPedido);

            try (ResultSet resultado = sentencia.executeQuery()) {

                if (resultado.next()) {
                    return resultado.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            System.out.println ("Error al comprobar la entrega del pedido.");

            e.printStackTrace();
        }
        return false;
    }
}