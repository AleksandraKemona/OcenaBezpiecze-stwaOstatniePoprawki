package pl.lodz.p.it.spjava.e11.sa.web.account;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.it.spjava.e11.sa.dto.AccountDTO;
import pl.lodz.p.it.spjava.e11.sa.security.HashGenerator;

@Named
@NoArgsConstructor
@SessionScoped

public class EditAccountBean implements Serializable {

    @Inject
    private AccountController accountController;
    
    @Inject
    private HashGenerator hashGenerator;

    private AccountDTO editedAccount;
    
    private String oldLogin;

    @Getter
    @Setter
    private String repeatPassword = "";

    public AccountDTO getEditedAccount() {
        if (null != editedAccount) {
            editedAccount.setAnswer(null);
            return editedAccount;
        } else {
            return new AccountDTO();
        }
    }

    public void setUserAccountForEdition(AccountDTO editedAccountDTO) {
        this.editedAccount = accountController.getUserAccountForEdition(editedAccountDTO);
        this.oldLogin = editedAccount.getLogin();
    }

    public void setMyAccountForEdition(String userName) {
        this.editedAccount = accountController.getMyAccountForEdition(userName);
    }
    
        
    public String editMyAccount(String userName) {
        setMyAccountForEdition(userName);
        return "editMyAccount";
    }

    public String saveUserAccount() {
        if ((editedAccount.getType()).equals("empty")) {
        AccountDTO accountOldVersion = accountController.getMyAccountForEdition(oldLogin);
        editedAccount.setType(accountOldVersion.getType());   
        }
        if (null == editedAccount) {
            return "main";
        } else if (!editedAccount.getNewPassword().equals(repeatPassword)) {
            FacesContext.getCurrentInstance().addMessage("createAccount:repeatPassword", new FacesMessage("Hasla nie zgadzaja sie"));
            return "";
        }
        return accountController.saveUserAccountAfterEdition(editedAccount, oldLogin);
    }

    public String saveMyAccount(String userName) {
        String userLogin = userName;
        AccountDTO accountOldVersion = accountController.getMyAccountForEdition(userLogin);
        String oldPassword = accountOldVersion.getPassword();
        String checkedPassword= hashGenerator.generateHash(editedAccount.getPassword());
        if (null == editedAccount) {
            return "main";
       
        }else if(!oldPassword.equals(checkedPassword)){
            FacesContext.getCurrentInstance().addMessage("createAccount:repeatPassword", new FacesMessage("Nie poprawne stare hasło"));
            return "";
        } else if (!editedAccount.getNewPassword().equals(repeatPassword)) {
            FacesContext.getCurrentInstance().addMessage("createAccount:repeatPassword", new FacesMessage("Nowe hasła nie zgadzaja sie"));
            return "";
        } else if ((editedAccount.getAnswer())!=null && !accountOldVersion.getAnswer().equals(editedAccount.getAnswer())) {
            FacesContext.getCurrentInstance().addMessage("createAccount:repeatPassword", new FacesMessage("Niepoprawna odpowiedź"));
            return "";   
        }        
        return accountController.saveMyAccountAfterEdition(editedAccount);
    }
    
    public String abortMy() {
        return "myAccountDetails";
    }
    
    public String abortUser() {
        return "listAccounts";
    }

}
