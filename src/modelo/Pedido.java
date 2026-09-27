package modelo;

public class Pedido {

    //Atributos
    private int id;
    private String direccionEntrega;
    private String tipo;
    private Repartidor repartidor;

    //Constructores
    public Pedido(String direccionEntrega, String tipo) {
        this.direccionEntrega = direccionEntrega;
        this.tipo = tipo;
        this.repartidor = null;
    }

    public Pedido(int id, String direccionEntrega, String tipo) {
        this.id = id;
        this.direccionEntrega = direccionEntrega;
        this.tipo = tipo;
        this.repartidor = null;
    }

    //Getters y Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }
    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Repartidor getRepartidor() {
        return repartidor;
    }
    public void setRepartidor(Repartidor repartidor) {
        this.repartidor = repartidor;
    }

    //toString
    @Override
    public String toString() {
        return "#" + id + " - " + direccionEntrega + " - " + tipo;
    }
}