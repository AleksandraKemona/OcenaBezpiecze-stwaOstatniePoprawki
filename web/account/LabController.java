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
import pl.lodz.p.it.spjava.e11.sa.dto.AssessorDTO;
import pl.lodz.p.it.spjava.e11.sa.dto.CosmeticDTO;
import pl.lodz.p.it.spjava.e11.sa.dto.LabDTO;
import pl.lodz.p.it.spjava.e11.sa.ejb.endpoint.AdministratorEndpoint;
import pl.lodz.p.it.spjava.e11.sa.ejb.endpoint.AssessorEndpoint;
import pl.lodz.p.it.spjava.e11.sa.ejb.endpoint.LabEndpoint;
import pl.lodz.p.it.spjava.e11.sa.entity.Administrator;
import pl.lodz.p.it.spjava.e11.sa.exception.AccountException;
import pl.lodz.p.it.spjava.e11.sa.exception.AppBaseException;


/**
 *
 * @author java
 */
@Named("labSession")
@SessionScoped
public class LabController implements Serializable{

    private static final Logger LOG = Logger.getLogger(LabController.class.getName());

    @Inject
    private LabEndpoint labEndpoint;

    @Getter
    private AccountDTO validatedAccount;

    public LabController() {
    }
    

    public String setTypeAsLab(String accountLogin, String newStamp) throws AppBaseException{
        try {
            LabDTO labDTO = new LabDTO();
        labEndpoint.setTypeAsLabTechnician(labDTO, accountLogin, newStamp);
        return newStamp;
        }catch (AccountException se) {
            if (AccountException.KEY_ADMIN_STAMP_EXISTS.equals(se.getMessage())) {
                ContextUtils.emitInternationalizedMessage("validateAccountForm:adminStamp",
                        AccountException.KEY_ADMIN_STAMP_EXISTS);

            } else {
                Logger.getLogger(AccountException.class.getName()).log(Level.SEVERE,
                        "Zgłoszenie w metodzie akcji validateAccount-setType  wyjątku: ", se);
            }
            return null;
        } catch (AppBaseException abe) {
            Logger.getLogger(LabController.class.getName()).log(Level.SEVERE,
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
