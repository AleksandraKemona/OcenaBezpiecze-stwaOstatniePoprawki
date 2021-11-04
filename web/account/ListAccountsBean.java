/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.spjava.e11.sa.web.account;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import pl.lodz.p.it.spjava.e11.sa.dto.AccountDTO;
import pl.lodz.p.it.spjava.e11.sa.ejb.endpoint.AccountEndpoint;
import pl.lodz.p.it.spjava.e11.sa.exception.AppBaseException;


@ViewScoped
@Named
public class ListAccountsBean implements Serializable {
    
    @Inject
    private AccountController accountController;
    
    @Inject
    private ValidateAccountBean validateAccountBean;
    
    @Inject
    private EditAccountBean editAccountBean;
    
    @Inject
    private AccountDetailsBean accountDetailsBean;
    
    @Getter
    private List<AccountDTO> accounts;

    
//    @Inject
//    private SzczegolyKontaBean szczegolyKontaBean;
    
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
    
    public String showDetails(AccountDTO accountDTO) throws AppBaseException{
        accountDetailsBean.setShowedAccountChoice(accountDTO);
        return "userAccountDetails";
    }
    
    public String showMyAccountDetails(String userName) throws AppBaseException{
        String name = userName;
        System.out.println("user name w show my account details LIST "+name);
        accountDetailsBean.setMyAccountChoice(name);
        return "myAccountDetails";
    }
    
    
    
    
    

    

    
    public void activate(AccountDTO accountDTO) {
        accountController.activateAccount(accountDTO);
        init();
    }
    
    public void deactivate(AccountDTO accountDTO) {
        accountController.deactivateAccount(accountDTO);
        init();
    }
    
//    public String pokazSzczegoly(KontoDTO konto) {
//        szczegolyKontaBean.setPokazywaneKontoWybor(konto);
//        return "szczegolyKonta";
//    }
    
    public String validateAccount(AccountDTO accountDTO){
        return accountController.getAccountForValidation(accountDTO);
    }

}
