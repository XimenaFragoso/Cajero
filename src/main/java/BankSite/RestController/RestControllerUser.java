package BankSite.RestController;

import BankSite.DAO.IDenominacionDAO;
import BankSite.DAO.IUserDAO;
import BankSite.JPA.Denominacion;
import BankSite.JPA.Result;
import BankSite.JPA.Users;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Users")
public class RestControllerUser {

    @Autowired
    private IUserDAO iuserDAO;

    @Autowired
    private IDenominacionDAO idenominacionDAO;

    @GetMapping("/GetAll")
    public ResponseEntity<Result> GetAll() {
        Result<Users> result = new Result();
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

    @GetMapping("/Cashier")
    public ResponseEntity<Result> GetAllCajero() {

        Result result = new Result();

        try {
            idenominacionDAO.Cantidades();
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

    @GetMapping("/IdUser/{Id_user}")
    public ResponseEntity UsersById(@PathVariable int Id_user) {

        Result result = new Result();

        try {
            result.object = iuserDAO.findById(Id_user).orElseThrow();
            result.correct = true;
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }

        if (result.correct) {
            return ResponseEntity.ok(result);

        } else {
            return ResponseEntity.badRequest().build();
        }
    }
//    sirve para mostrar cantidad cajero

    @GetMapping("/GetCashier")
    public ResponseEntity Cashier() {
        Result<Denominacion> result = new Result();
        try {
            result.objects = idenominacionDAO.findAll();
            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        if (result.correct) {
            return ResponseEntity.ok(result);

        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Transactional
    @PostMapping("/Retiro/{monto}/{Id_user}")
    public ResponseEntity retirar(@PathVariable Double monto, @PathVariable int Id_user) {

        Result<Map<Double, Integer>> result = new Result();

        try {

            Users user = new Users();

            user = iuserDAO.findById(Id_user).orElseThrow();

            if (user.getSaldo() >= monto) {
                user.setSaldo(user.getSaldo() - monto);
                iuserDAO.save(user);
                return ResponseEntity.ok(user);
            }

            if (monto <= 0) {
                result.correct = false;
                result.errorMessage = "Ingrese una cantidad";
                return ResponseEntity.badRequest().body(result);
            }

            if (monto > user.getSaldo()) {
                result.correct = false;
                result.errorMessage = "No cuentas con ese saldo";
                return ResponseEntity.badRequest().body(result);
            }

            if (monto > 1250) {
                result.correct = false;
                result.errorMessage = "No puedes retirar mas de $1250";
                return ResponseEntity.badRequest().body(result);
            }

        } catch (Exception ex) {

            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;

            return ResponseEntity.internalServerError().body(result);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error");

    }
}
