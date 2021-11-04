package pl.lodz.p.it.spjava.e11.sa.security;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;


@Named
@RequestScoped
public class LoginController {
    
    @Inject
    private HttpServletRequest request;

    @NotNull
    private String username;

    @NotNull
    private String password;

     /**
     * Dokonuje programowo uwierzytelnienia na podstawie loginu i hasła.
     * Dane pochodzą z formularza uwierzytelniania.
     * Dzięki samodzielnemu wywoływaniu login() można przechwycić wyjątek który jest rzucany w przypadku niepoprawnego uwierzytelnienia.
     * Można to wykorzystać np. do blokowania konta po pewnej liczbie nieudanych prób.
     */
    public String login() {
        try {
            System.out.println("username "+username +", password "+password);
            request.login(username, password);
        } catch (ServletException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            return "loginError";
        }
        return "main";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
