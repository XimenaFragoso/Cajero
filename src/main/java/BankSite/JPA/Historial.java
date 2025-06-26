package BankSite.JPA;

public class Historial {
    private int Id_historial; 
    private double Retiro; 
    public Users users;

    public int getId_historial() {
        return Id_historial;
    }

    public void setId_historial(int Id_historial) {
        this.Id_historial = Id_historial;
    }

    public double getRetiro() {
        return Retiro;
    }

    public void setRetiro(double Retiro) {
        this.Retiro = Retiro;
    }
    
    
}
