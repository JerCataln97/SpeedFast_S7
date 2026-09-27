package modelo;

import java.time.LocalDate;
import java.time.LocalTime;

public class Entrega {

    //Atributos
    private int id;
    private int pedidoId;
    private int repartidorId;
    private LocalDate fecha;
    private LocalTime hora;

    //Constructor
    public Entrega(int pedidoId, int repartidorId, LocalDate fecha, LocalTime hora) {
        this.pedidoId = pedidoId;
        this.repartidorId = repartidorId;
        this.fecha = fecha;
        this.hora = hora;
    }

    //Getters y Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getPedidoId() {
        return pedidoId;
    }
    public void setPedidoId(int pedidoId) {
        this.pedidoId = pedidoId;
    }

    public int getRepartidorId() {
        return repartidorId;
    }
    public void setRepartidorId(int repartidorId) {
        this.repartidorId = repartidorId;
    }

    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }
    public void setHora(LocalTime hora) {
        this.hora = hora;
    }
}