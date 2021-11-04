package pl.lodz.p.it.spjava.e11.sa.web.account;

import pl.lodz.p.it.spjava.e11.sa.web.cosmetic.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.spjava.e11.sa.dto.AccountDTO;
import pl.lodz.p.it.spjava.e11.sa.dto.AnalysisDTO;
import pl.lodz.p.it.spjava.e11.sa.dto.CategoryDTO;
import pl.lodz.p.it.spjava.e11.sa.dto.CosmeticDTO;
import pl.lodz.p.it.spjava.e11.sa.dto.ToxicologyDTO;
import pl.lodz.p.it.spjava.e11.sa.exception.AppBaseException;
import pl.lodz.p.it.spjava.e11.sa.web.analysis.AnalysisController;
import pl.lodz.p.it.spjava.e11.sa.web.category.CategoryController;

@SessionScoped
@Named
public class AccountDetailsBean implements Serializable {

    @Inject
    private AccountController accountController;


    private AccountDTO showedAccount;


    private String showedAccountChoice;
    


    public void setShowedAccountChoice(AccountDTO accountDTO) throws AppBaseException{
        System.out.println("-----------setShowedAccountChoice------------");
        System.out.println("accountDTO "+ accountDTO);
        this.showedAccountChoice = accountDTO.getLogin();
        init();
    }
    
    public void setMyAccountChoice(String username) throws AppBaseException{
        System.out.println("-------------My account Choice-------");
        System.out.println("uesename "+ username);
        this.showedAccountChoice = username;
        init();
    }

    private void init() throws AppBaseException{
        System.out.println("-------------init-----------");
        System.out.println("showed account choice "+ showedAccountChoice);
        showedAccount = accountController.downloadAccountForDetails(showedAccountChoice);
        System.out.println("showed account "+showedAccount);
//        return sh;
    }

    public AccountDTO getShowedAccount() {
        if (null != showedAccount) {

            return showedAccount;
        } else {
            return new AccountDTO();
        }
    }

    public String abort() {

        return "listCosmetics";
    }

    public String refresh() throws AppBaseException{
        init();
        return "";
    }

}
