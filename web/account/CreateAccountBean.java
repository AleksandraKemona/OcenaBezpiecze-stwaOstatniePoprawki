package pl.lodz.p.it.spjava.e11.sa.web.account;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Setter;
import pl.lodz.p.it.spjava.e11.sa.dto.AccountDTO;
import pl.lodz.p.it.spjava.e11.sa.ejb.endpoint.AccountEndpoint;
import pl.lodz.p.it.spjava.e11.sa.exception.AppBaseException;

@SessionScoped
@Named
public class CreateAccountBean implements Serializable {

    @EJB
    private AccountEndpoint accountEndpoint;
    
    @Inject 
    private AccountController accountController;
//
//    @Inject
//    private Conversation conversation;

    @Setter
    private AccountDTO newAccount;

    public AccountDTO getNewAccount() {
        if (null != newAccount) {
            return newAccount;
        } else {
            return new AccountDTO();
        }
    }

    public String init() {

        newAccount = new AccountDTO();
        return "createAccount";
    }

    public String confirmAccount() {

        if (null != newAccount && null != newAccount.getPassword()&& newAccount.getPassword().equals(repeatPassword)) {

            System.out.println("newAccount " + newAccount);
            return "confirmAccount";
        } else {
            System.out.println("newAccount else " + newAccount);
            FacesContext.getCurrentInstance().addMessage("createAccount:repeatPassword", new FacesMessage("Hasla nie zgadzaja sie"));
            return "";
        }
    }

    public String createAccount() throws AppBaseException {
        if (null == newAccount) {
            return null;
        }
        AccountDTO createdAccount=newAccount;
        newAccount=null;
        return accountController.createAccount(createdAccount);
    }
    
    

//    public String utworzKontoJakoKlient() {
//        if (null == noweKonto) {
//            return "glowna";
//        }
//        //UWAGA! To uproszczenie. Powinno byc osobne klientDTO zamiast tej metody
//        kontoEndpoint.utworzKontoJakoKlient(noweKonto);
//        conversation.end();
//        return "sukces";
//    }
    private String repeatPassword = "";

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public String abort() {
       
        return "main";
    }

}
