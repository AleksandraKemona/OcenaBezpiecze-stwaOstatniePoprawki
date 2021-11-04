package pl.lodz.p.it.spjava.e11.sa.web.account;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.spjava.e11.sa.dto.AccountDTO;
import pl.lodz.p.it.spjava.e11.sa.ejb.endpoint.AccountEndpoint;
import pl.lodz.p.it.spjava.e11.sa.exception.AppBaseException;

@SessionScoped
@Named
public class ValidateAccountBean implements Serializable {

    @Inject
    private AccountController accountController;

    @EJB
    private AccountEndpoint accountEndpoint;

    @Setter
    private AccountDTO validatedAccount = new AccountDTO();

    @PostConstruct
    private void init() {
        validatedAccount = accountController.getValidatedAccount();
        System.out.println("validation bean metoda init validatedAccount "+ validatedAccount);
    }//ToDo podpiąć czy zostawić?

    public AccountDTO getValidatedAccount() {
//        if (null != editedCosmetic) {
        System.out.println("validated Account" + validatedAccount);
        return validatedAccount;
//        } else {
//            return new CosmeticDTO(); // Dla unikniecia błędu formularza. Dane nie zostana zachowane.
//        }
    }

    public String saveValidatedAccount(String userName) throws AppBaseException {
        accountController.saveAccountAfterValidation(validatedAccount, userName);
        return "listAccounts";
        
    }

//    public String setEditedCosmetic(CosmeticDTO editedCosmetic) {
////        this.editedCosmetic = cosmeticController.getCosmeticForEdition(editedCosmetic.getId());
//        return cosmeticController.saveCosmeticAfterEdition(editedCosmetic);
//    } ToDo podpiąć czy zostawić?
//    public String saveCosmetic() {
////        if (null == editedCosmetic) {
////            return "main";
////        }
//        String categoryName = editedCosmetic.getCategoryId().getCategoryName();
//        cosmeticController.saveCosmeticAfterEdition(editedCosmetic, categoryName);
//        System.out.println("EditCosmeticBean" + editedCosmetic);
//        return "listCosmetics";
//    }
    public String abort() {
        return "listAccounts";
    }

//    public String categoryChoiceAction() {
//        editedCosmetic.setCategoryId(CategoryConverter.createCategoryDTOFromEntity
//            (categoryFacade.findByCategoryId(categoryChoiceController.getSelectedCategoryDTO().getCategoryId())));
//        categoryEndpoint.chooseCategory(editedCosmetic);
//        return "confirmCosmetic";
//   
}
