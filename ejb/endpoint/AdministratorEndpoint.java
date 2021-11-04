package pl.lodz.p.it.spjava.e11.sa.ejb.endpoint;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.inject.Inject;

import pl.lodz.p.it.spjava.e11.sa.dto.AccountDTO;
import pl.lodz.p.it.spjava.e11.sa.dto.AdministratorDTO;
import pl.lodz.p.it.spjava.e11.sa.ejb.facade.AccountFacade;
import pl.lodz.p.it.spjava.e11.sa.ejb.facade.AdministratorFacade;
import pl.lodz.p.it.spjava.e11.sa.ejb.facade.AssessorFacade;
import pl.lodz.p.it.spjava.e11.sa.ejb.facade.LabTechnicianFacade;
import pl.lodz.p.it.spjava.e11.sa.ejb.facade.SalesFacade;
import pl.lodz.p.it.spjava.e11.sa.ejb.manager.AccountManager;
import pl.lodz.p.it.spjava.e11.sa.ejb.manager.AdministratorManager;
import pl.lodz.p.it.spjava.e11.sa.entity.Account;

import pl.lodz.p.it.spjava.e11.sa.entity.Administrator;
import pl.lodz.p.it.spjava.e11.sa.exception.AccountException;
import pl.lodz.p.it.spjava.e11.sa.exception.AppBaseException;
import pl.lodz.p.it.spjava.e11.sa.security.HashGenerator;
import pl.lodz.p.it.spjava.e11.sa.utils.converter.AccountsConverter;
import pl.lodz.p.it.spjava.e11.sa.web.utils.AccountUtils;

@Stateful
@RolesAllowed({"Administrator"})
public class AdministratorEndpoint implements Serializable {


    @Inject
    private AdministratorManager administratorManager;

    protected SessionContext sctx;

    private int txRetryLimit;


    public void setTypeAsAdministrator(AdministratorDTO administratorDTO, String accountLogin, String newStamp) throws AppBaseException {
        Administrator administrator = new Administrator();      
        administrator.setAdminStamp(newStamp);
        boolean rollbackTX;
        int retryTXCounter = txRetryLimit;
        do {
            try {
                administratorManager.setTypeAsAdministrator(administrator, accountLogin);
                rollbackTX = administratorManager.isLastTransactionRollback();
            } catch (AccountException ae) {
                throw ae;
            } catch (AppBaseException | EJBTransactionRolledbackException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy:"
                        + ex.getClass().getName());
                rollbackTX = true;
            }

        } while (rollbackTX && --retryTXCounter > 0);
        if (rollbackTX && retryTXCounter == 0) {
            throw AccountException.createAccountExceptionWithTxRetryRollback();
        }

    }

}
