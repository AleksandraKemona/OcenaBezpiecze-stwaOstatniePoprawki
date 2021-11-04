package pl.lodz.p.it.spjava.e11.sa.ejb.endpoint;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.inject.Inject;

import pl.lodz.p.it.spjava.e11.sa.dto.LabDTO;
import pl.lodz.p.it.spjava.e11.sa.ejb.manager.LabManager;
import pl.lodz.p.it.spjava.e11.sa.entity.LabTechnician;
import pl.lodz.p.it.spjava.e11.sa.exception.AccountException;
import pl.lodz.p.it.spjava.e11.sa.exception.AppBaseException;

@Stateful
@RolesAllowed({"Administrator"})
public class LabEndpoint implements Serializable {
    
    @Inject
    private LabManager labManager;


    protected SessionContext sctx;

    private int txRetryLimit;

    public void setTypeAsLabTechnician(LabDTO labDTO, String accountLogin, String newStamp) throws AppBaseException {
        LabTechnician labTechnician = new LabTechnician();
        labTechnician.setLabStamp(newStamp);
        boolean rollbackTX;
        int retryTXCounter = txRetryLimit;
        do {
            try {
                labManager.setTypeAsLabTechnician(labTechnician, accountLogin);
                rollbackTX = labManager.isLastTransactionRollback();
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
