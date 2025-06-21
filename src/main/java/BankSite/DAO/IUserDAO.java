package BankSite.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import BankSite.JPA.Users;

public interface IUserDAO extends JpaRepository<Users,Integer>{
    
}
