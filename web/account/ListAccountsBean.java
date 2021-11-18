package pl.lodz.p.it.spjava.e11.sa.web.account;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import pl.lodz.p.it.spjava.e11.sa.dto.AccountDTO;


@ViewScoped
@Named
public class ListAccountsBean implements Serializable {
    
    @Inject
    private AccountController accountController;
    
    @Inject
    private EditAccountBean editAccountBean;
    
    @Inject
    private AccountDetailsBean accountDetailsBean;
    
    @Getter
    private List<AccountDTO> accounts;

    @PostConstruct
    public void init(){
        accounts = accountController.listAllAccounts();
    }
    
    public String editUserAccount(AccountDTO accountDTO) {
        editAccountBean.setUserAccountForEdition(accountDTO);
        return "editUserAccount";
    }
    
    public String editMyAccount(String userName) {
        editAccountBean.setMyAccountForEdition(userName);
        return "editMyAccount";
    }
    
    public String showDetails(AccountDTO accountDTO){
        accountDetailsBean.setShowedAccountChoice(accountDTO);
        return "userAccountDetails";
    }
    
    public String showMyAccountDetails(String userName){
        String name = userName;
        return accountDetailsBean.setMyAccountChoice(name);
    }

    public void activate(AccountDTO accountDTO) {
        accountController.activateAccount(accountDTO);
        init();
    }
    
    public void deactivate(AccountDTO accountDTO) {
        accountController.deactivateAccount(accountDTO);
        init();
    }

    public String validateAccount(AccountDTO accountDTO){
        return accountController.getAccountForValidation(accountDTO);
    }
    
    public String refresh(){
        return "listaKont";
    }

}
