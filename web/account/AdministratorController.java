package pl.lodz.p.it.spjava.e11.sa.web.account;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.spjava.e11.sa.dto.AccountDTO;
import pl.lodz.p.it.spjava.e11.sa.ejb.endpoint.AccountEndpoint;
import pl.lodz.p.it.spjava.e11.sa.web.utils.ContextUtils;
import java.io.*;
import lombok.Getter;
import pl.lodz.p.it.spjava.e11.sa.dto.AdministratorDTO;
import pl.lodz.p.it.spjava.e11.sa.dto.CosmeticDTO;
import pl.lodz.p.it.spjava.e11.sa.ejb.endpoint.AdministratorEndpoint;
import pl.lodz.p.it.spjava.e11.sa.entity.Administrator;
import pl.lodz.p.it.spjava.e11.sa.exception.AccountException;
import pl.lodz.p.it.spjava.e11.sa.exception.AppBaseException;


/**
 *
 * @author java
 */
@Named("administratorSession")
@SessionScoped
public class AdministratorController implements Serializable{

    private static final Logger LOG = Logger.getLogger(AdministratorController.class.getName());

    @Inject
    private AdministratorEndpoint administratorEndpoint;

    @Getter
    private AccountDTO validatedAccount;

    public AdministratorController() {
    }
    

    public String setTypeAsAdministrator (String accountLogin, String newStamp) throws AppBaseException{
        try {
        AdministratorDTO administratorDTO = new AdministratorDTO();
            System.out.println("--------------------------");
            System.out.println("Account controller set type administratorDTO"+ administratorDTO);
            System.out.println("Account controller set type newStamp "+newStamp);
            System.out.println("Account controller set type accountLogin "+accountLogin);
        administratorEndpoint.setTypeAsAdministrator(administratorDTO, accountLogin, newStamp);
        return newStamp;
        }catch (AccountException se) {
            System.out.println("SE Message" + se.getMessage());
            if (AccountException.KEY_ADMIN_STAMP_EXISTS.equals(se.getMessage())) {
                ContextUtils.emitInternationalizedMessage("validateAccountForm:adminStamp",
                        AccountException.KEY_ADMIN_STAMP_EXISTS);

            } else {
                Logger.getLogger(AccountException.class.getName()).log(Level.SEVERE,
                        "Zgłoszenie w metodzie akcji validateAccount-setType  wyjątku: ", se);
            }
            return null;
        } catch (AppBaseException abe) {
            Logger.getLogger(AdministratorController.class.getName()).log(Level.SEVERE,
                    "Zgłoszenie w metodzie akcji validateAccount-setType wyjątku typu: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            }
            return null;
        }
    }

    @PostConstruct
    private void init() {
        LOG.severe("Session started: " + ContextUtils.getSessionID());
    }
}
