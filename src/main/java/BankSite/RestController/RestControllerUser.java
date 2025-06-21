package BankSite.RestController;

import BankSite.DAO.IUserDAO;
import BankSite.JPA.Result;
import BankSite.JPA.Users;
import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Users")
public class RestControllerUser {

    @Autowired
    private IUserDAO iuserDAO;

    @GetMapping()
    public ResponseEntity<Result> GetAll() {
        Result <Users> result = new Result();
        try {
            result.objects = iuserDAO.findAll();            
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        if (result.correct) {
            if (result.objects.isEmpty()) {
                return ResponseEntity.status(204).body(null);
            } else {
                return ResponseEntity.ok(result);
            }
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }
}
