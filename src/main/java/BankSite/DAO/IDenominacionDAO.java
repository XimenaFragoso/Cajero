package BankSite.DAO;

import BankSite.JPA.Denominacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

public interface IDenominacionDAO extends JpaRepository<Denominacion, Integer>{
    
    @Procedure(name = "Cantidades")
    void Cantidades();
    
}
