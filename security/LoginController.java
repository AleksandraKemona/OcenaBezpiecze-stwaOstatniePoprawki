package pl.lodz.p.it.spjava.e11.sa.security;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import pl.lodz.p.it.spjava.e11.sa.ejb.endpoint.AccountEndpoint;
import pl.lodz.p.it.spjava.e11.sa.exception.AccountException;
import pl.lodz.p.it.spjava.e11.sa.exception.AppBaseException;
import pl.lodz.p.it.spjava.e11.sa.web.analysis.AnalysisController;
import pl.lodz.p.it.spjava.e11.sa.web.utils.ContextUtils;

@Named
@RequestScoped
public class LoginController {

    @Inject
    private HttpServletRequest request;

    @Inject
    private AccountEndpoint accountEndpoint;

    @NotNull
    private String username;

    @NotNull
    private String password;

    public String login() {
        boolean isActive = false;
        String stringForReturn = "";
        try {
            isActive = accountEndpoint.downloadAccountForLogin(username).isActive();
            if (isActive == false) {
                stringForReturn = "formNotActiveError";
            } else if (isActive == true) {
                request.login(username, password);
                stringForReturn = "main";
            }
        }catch(AccountException ae){
            if (AccountException.KEY_LOGIN_DOES_NOT_EXIST.equals(ae.getMessage())) {
                stringForReturn= "formLoginError";
            }
        } catch (ServletException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            return "loginError";
        } catch (AppBaseException abe) {
            Logger.getLogger(AnalysisController.class.getName()).log(Level.SEVERE,
                    "Zgłoszenie w metodzie akcji createAnalysis wyjątku typu: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            }
            return null;
        }

        return stringForReturn;
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
