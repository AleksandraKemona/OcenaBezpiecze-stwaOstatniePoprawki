package pl.lodz.p.it.spjava.e11.sa.web.account;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.spjava.e11.sa.dto.AccountDTO;

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

    public String begin() {
        accountForChangePassword = new AccountDTO();
        return "resetPasswordRequest";
    }

    public AccountDTO getAccountForChangePassword() {
        if (null != accountForChangePassword) {
            return accountForChangePassword;
        } else {
            AccountDTO accountDTO = new AccountDTO();
            accountForChangePassword = accountDTO;
            return accountForChangePassword;
        }
    }

    public String requestResetPassword() {
        return accountController.downloadAccountForChangePassword(accountForChangePassword);
    }

    public String resetPassword() {
        AccountDTO accountDTO = accountForChangePassword;
        if (accountDTO.getPassword().equals(repeatPassword)) {
            return accountController.resetPassword(accountDTO);
        } else {
            FacesContext.getCurrentInstance().addMessage("createAccount:repeatPassword", new FacesMessage("Hasla nie zgadzaja sie"));
            return "";
        }
    }

    public void setAccountForPassword(AccountDTO accountDTO) {
        this.accountForPassword = accountDTO;
    }

    public AccountDTO getRequestedAccount() {
        if (null != requestedAccount) {
            return requestedAccount;
        } else {
            return new AccountDTO();
        }
    }

    public String abort() {
        return "main";
    }

}
