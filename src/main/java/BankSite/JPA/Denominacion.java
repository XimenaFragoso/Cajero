package BankSite.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Denominacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_denominacion")
    private int Id_Denominacion;

    @Column(name = "nombre")
    private double Nombre;
    
    @Column(name = "cantidad")
    private double Cantidad; 
    
    @JoinColumn(name = "id_tipo")
    @ManyToOne
    public Tipo Tipo;    

    public int getId_Denominacion() {
        return Id_Denominacion;
    }

    public void setId_Denominacion(int Id_Denominacion) {
        this.Id_Denominacion = Id_Denominacion;
    }

    public double getNombre() {
        return Nombre;
    }

    public void setNombre(double Nombre) {
        this.Nombre = Nombre;
    }

    public double getCantidad() {
        return Cantidad;
    }
    
    public void setCantidad(double Cantidad) {
        this.Cantidad = Cantidad;
    }
}
