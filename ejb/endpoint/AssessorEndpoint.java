package pl.lodz.p.it.spjava.e11.sa.ejb.endpoint;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.inject.Inject;

import pl.lodz.p.it.spjava.e11.sa.dto.AssessorDTO;
import pl.lodz.p.it.spjava.e11.sa.ejb.manager.AssessorManager;
import pl.lodz.p.it.spjava.e11.sa.entity.Assessor;
import pl.lodz.p.it.spjava.e11.sa.exception.AccountException;
import pl.lodz.p.it.spjava.e11.sa.exception.AppBaseException;

@Stateful
@RolesAllowed({"Administrator"})
public class AssessorEndpoint implements Serializable {
    
    @Inject
    private AssessorManager assessorManager;


    protected SessionContext sctx;

    private int txRetryLimit;

    public void setTypeAsAssessor(AssessorDTO assessorDTO, String accountLogin, String newStamp) throws AppBaseException {
        Assessor assessor = new Assessor();
        assessor.setAssessorStamp(newStamp);
        boolean rollbackTX;
        int retryTXCounter = txRetryLimit;
        do {
            try {
                assessorManager.setTypeAsAssessor(assessor, accountLogin);
                rollbackTX = assessorManager.isLastTransactionRollback();
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
