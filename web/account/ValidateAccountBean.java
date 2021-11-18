package pl.lodz.p.it.spjava.e11.sa.web.account;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Setter;
import pl.lodz.p.it.spjava.e11.sa.dto.AccountDTO;

@SessionScoped
@Named
public class ValidateAccountBean implements Serializable {

    @Inject
    private AccountController accountController;

    @Setter
    private AccountDTO validatedAccount = new AccountDTO();

    @PostConstruct
    private void init() {
        validatedAccount = accountController.getValidatedAccount();
    }

    public AccountDTO getValidatedAccount() {
        return validatedAccount;
    }

    public String saveValidatedAccount(String userName){
        return accountController.saveAccountAfterValidation(validatedAccount, userName);
        
        
    }

    public String abort() {
        return "listAccounts";
    }

}
