package pl.lodz.p.it.spjava.e11.sa.web.account;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Setter;
import pl.lodz.p.it.spjava.e11.sa.dto.AccountDTO;

@SessionScoped
@Named
public class CreateAccountBean implements Serializable {

    @Inject
    private AccountController accountController;

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
        String fromController = new String();
        if (null != newAccount && null != newAccount.getPassword() && newAccount.getPassword().equals(repeatPassword)) {
            fromController = accountController.confirmAccount(newAccount);
        } else {
            FacesContext.getCurrentInstance().addMessage("createAccount:repeatPassword", new FacesMessage("Hasla nie zgadzaja sie"));
        }
        return fromController;
    }

    public String createAccount() {
        if (null == newAccount) {
            return null;
        }
        AccountDTO createdAccount = newAccount;
        newAccount = null;
        return accountController.createAccount(createdAccount);
    }

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
