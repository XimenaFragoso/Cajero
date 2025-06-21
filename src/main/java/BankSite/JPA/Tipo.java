package BankSite.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Tipo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo")
    private int Id_Tipo;
    
    @Column(name = "nombre")
    private String Nombre;

    public int getId_Tipo() {
        return Id_Tipo;
    }

    public void setId_Tipo(int Id_Tipo) {
        this.Id_Tipo = Id_Tipo;
    }
    
    public String getNombre() {
        return Nombre; 
    }
    
    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }
    
}
