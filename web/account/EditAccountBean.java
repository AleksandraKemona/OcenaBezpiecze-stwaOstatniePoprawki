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
        AccountDTO accountOldVersion = accountController.getMyAccountForEdition(editedAccount.getLogin());
        editedAccount.setType(accountOldVersion.getType());   
        }
        System.out.println("edited account in save user "+editedAccount);
        if (null == editedAccount) {
            return "main";
        } else if (!editedAccount.getNewPassword().equals(repeatPassword)) {
            FacesContext.getCurrentInstance().addMessage("createAccount:repeatPassword", new FacesMessage("Hasla nie zgadzaja sie"));
            return "";
        }
        return accountController.saveUserAccountAfterEdition(editedAccount);
    }

    public String saveMyAccount() {
        System.out.println("----------Save my account----------");
        System.out.println("editedAccount "+ editedAccount);
        String userLogin = editedAccount.getLogin();
        AccountDTO accountOldVersion = accountController.getMyAccountForEdition(userLogin);
        System.out.println("account Old version "+ accountOldVersion);
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
        } else if (!accountOldVersion.getAnswer().equals(editedAccount.getAnswer())&& (editedAccount.getAnswer())!=null) {
            FacesContext.getCurrentInstance().addMessage("createAccount:repeatPassword", new FacesMessage("Niepoprawna odpowiedź"));
            return "";   
        }
        return accountController.saveUserAccountAfterEdition(editedAccount);
    }
    
    



    public String abort() {
        return "listAccounts";
    }

}
