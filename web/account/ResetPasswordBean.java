package pl.lodz.p.it.spjava.e11.sa.web.account;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.spjava.e11.sa.dto.AccountDTO;
import pl.lodz.p.it.spjava.e11.sa.exception.AppBaseException;

/**
 *
 * @author java
 */
@SessionScoped
@Named("resetPasswordBean")
public class ResetPasswordBean implements Serializable {

    @Inject
    private AccountController accountController;

    @Setter
    private AccountDTO accountForChangePassword;

    @Getter
    private AccountDTO accountForPassword;

    private AccountDTO requestedAccount;

    private String repeatPassword = "";

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

//początek fragmentu z reset
    public String begin() {
        accountForChangePassword = new AccountDTO();
        System.out.println("----------------------------------------Bean-----------------------");
        System.out.println("account For Change Password w Begin " + accountForChangePassword);
        return "resetPasswordRequest";
    }

    public AccountDTO getAccountForChangePassword() {
        if (null != accountForChangePassword) {
            System.out.println("----------------------------------------Bean-----------------------");
            System.out.println("konto nie jest null " + accountForChangePassword);
            return accountForChangePassword;
        } else {
            System.out.println("----------------------------------------Bean-----------------------");
            System.out.println("konto jest null " + accountForChangePassword);
            AccountDTO accountDTO = new AccountDTO();
            accountForChangePassword = accountDTO;
            return accountForChangePassword;
        }
    }

    public String requestResetPassword() throws AppBaseException {
        System.out.println("----------------------------------------Bean-----------------------");
        System.out.println("account For Change Password w resetPassword " + accountForChangePassword);
//        return accountController.downloadAccountForChangePassword(accountForChangePassword);
//        this.accountForPassword = 
                accountController.downloadAccountForChangePassword(accountForChangePassword);
//        return  accountForPassword;

//        System.out.println("account for Password " + accountForPassword);
        return "resetPassword";

    }

    public String resetPassword() throws AppBaseException {
        AccountDTO accountDTO = accountForPassword;
        System.out.println("accountDTO " + accountDTO);
        if (accountDTO.getPassword().equals(repeatPassword)) {
            return accountController.resetPassword(accountDTO);
        } else {
            FacesContext.getCurrentInstance().addMessage("createAccount:repeatPassword", new FacesMessage("Hasla nie zgadzaja sie"));
            return "";
        }
    }

    public void setAccountForPassword(AccountDTO accountDTO) {
        System.out.println("-------------------Reset Bean-----------------");
        System.out.println("accountDTO " + accountDTO);
        this.accountForPassword = accountDTO;
        System.out.println("-------------------Reset Bean-----------------");
        System.out.println("account for pasword DTO " + accountDTO);
        System.out.println("account for password " + accountForPassword);
    }

//    @PostConstruct
//    private void init(){
//        accountController = kontoController.getKontoZmienHaslo();
//        konto.setHaslo(new String());
//    }
    public AccountDTO getRequestedAccount() {
        if (null != requestedAccount) {
            return requestedAccount;
        } else {
            return new AccountDTO();
        }
    }
//
//    public void setRequestedAccount(AccountDTO accountForPassword) throws AppBaseException {
//        this.requestedAccount = accountController.getAccountForChangePassword(accountForPassword);
//    }

//    @Getter
//    @Setter
//    private String repeatPassword = "";

//    public String getAccountForChangePassword(){
//        accountForPassword=accountController.getAccountChangePassword();
//        return "resetPassword";
//    }
//    public String zmienHaslo() {
//        if (!(hasloPowtorz.equals(konto.getHaslo()))){
//            ContextUtils.emitInternationalizedMessage("zmienHasloKontaForm:passwordRepeat", "passwords.not.matching");
//            return null;
//        }
//            
//        return kontoController.zmienHasloKonta(konto.getHaslo());
//    }
    public String abort() {
        return "main";
    }

}
