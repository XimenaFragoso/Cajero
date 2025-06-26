package BankSite.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Rolles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_roll")
    private int Id_roll;

    @Column(name = "nombre")
    private String Nombre;

    public int getId_roll() {
        return Id_roll;
    }

    public void setId_roll(int Id_roll) {
        this.Id_roll = Id_roll;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

}
