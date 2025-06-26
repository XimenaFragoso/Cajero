package BankSite.Controller;

import BankSite.JPA.Denominacion;
import BankSite.JPA.Result;
import BankSite.JPA.Rolles;
import BankSite.JPA.Users;
import ML.Monto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/User")
public class UsuarioController {

    private RestTemplate restTemplate = new RestTemplate();

    @GetMapping()
    public String Index(Model model) {
        ResponseEntity<Result<Users>> responseEntity = restTemplate.exchange("http://localhost:8080/Users/GetAll",
                HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<Result<Users>>() {
        });

        Result response = responseEntity.getBody();
        Users user = new Users();
        user.rolles = new Rolles();

        model.addAttribute("users", user);
        model.addAttribute("listaUsers", response.objects);

        return "Bank";
    }
    
//    rellenar cajero
    @GetMapping("/Cash")
    public String Cash() {
        ResponseEntity<Result> responseEntity = restTemplate.exchange("http://localhost:8080/Users/Cashier", 
                HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<Result>() {
                    
                });
        
        return "redirect:/User";
        
    }
    
//    mostrar cajero
    @GetMapping("/GetCashier")
    public String GetCashier(Model model){
        ResponseEntity<Result<Denominacion>> responseEntity = restTemplate.exchange("http://localhost:8080/Users/GetCashier",
                HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<Result<Denominacion>>() {
                    
                });
        
        Result response = responseEntity.getBody(); 
        
        model.addAttribute("Denominacion", response.objects);
        
        return "DetailCashier"; 
        
    }

    @GetMapping("/Retiro")
    public String BankRetiro(Model model, HttpSession session) {
        Monto monto = new Monto();
        
        String absolutePath = session.getAttribute("Id_user").toString();
        int IdUser = Integer.parseInt(absolutePath);
        
        String nombreU = session.getAttribute("Nombre").toString();
        
        String saldoU = session.getAttribute("Saldo").toString();
        double Saldo_U = Double.parseDouble(saldoU);
        
        model.addAttribute("monto", monto);
        model.addAttribute("Nombre", nombreU);
        model.addAttribute("Id_user", IdUser);
        model.addAttribute("Saldo", Saldo_U);

        return "Retiro";
    }

    @PostMapping("/Retirar")
    public String Retiro(@RequestParam int Id_user, @ModelAttribute Monto monto, Model model) {
        Result result = new Result();
        try {
            Monto monto1 = new Monto();
            ResponseEntity<Result> responseEntity = restTemplate.exchange("http://localhost:8080/Users/Retiro/" + monto.getMonto() + "/" + Id_user,
                    HttpMethod.POST,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result>() {
            });

            model.addAttribute("monto", monto1);

            if(responseEntity.getStatusCode().is2xxSuccessful()){
                model.addAttribute("Suficiente",true);                 
            } else {
                if(responseEntity.getStatusCode().is4xxClientError()){
                    System.out.println("");
                }
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return "redirect:/User/Retiro";
    }

    @GetMapping("Card/{Id_user}")
    public String UserById(@PathVariable int Id_user, Model model, HttpSession httpSession) {
        Result result = new Result();
        try {
            Users user = new Users();

            ResponseEntity<Result<Users>> responseEntity = restTemplate.exchange("http://localhost:8080/Users/IdUser/" + Id_user,
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<Users>>() {
            });

            httpSession.setAttribute("Id_user", Id_user);
            httpSession.setAttribute("Nombre", responseEntity.getBody().object.getNombre());
            httpSession.setAttribute("Saldo", responseEntity.getBody().object.getSaldo());
            model.addAttribute("User", responseEntity.getBody().object);

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return "DetailUser";
    }

}
