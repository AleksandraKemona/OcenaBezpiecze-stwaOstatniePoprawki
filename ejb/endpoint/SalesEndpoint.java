package pl.lodz.p.it.spjava.e11.sa.ejb.endpoint;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.inject.Inject;

import pl.lodz.p.it.spjava.e11.sa.dto.SalesDTO;
import pl.lodz.p.it.spjava.e11.sa.ejb.manager.SalesManager;
import pl.lodz.p.it.spjava.e11.sa.entity.Sales;
import pl.lodz.p.it.spjava.e11.sa.exception.AccountException;
import pl.lodz.p.it.spjava.e11.sa.exception.AppBaseException;

@Stateful
@RolesAllowed({"Administrator"})
public class SalesEndpoint implements Serializable {
    
    @Inject
    private SalesManager salesManager;


    protected SessionContext sctx;

    private int txRetryLimit;

    public void setTypeAsSales(SalesDTO salesDTO, String accountLogin, String newStamp) throws AppBaseException {
        Sales sales = new Sales();
        sales.setSalesStamp(newStamp);
        boolean rollbackTX;
        int retryTXCounter = txRetryLimit;
        do {
            try {
                salesManager.setTypeAsSales(sales, accountLogin);
                rollbackTX = salesManager.isLastTransactionRollback();
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
