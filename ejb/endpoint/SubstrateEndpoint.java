/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.spjava.e11.sa.ejb.endpoint;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import pl.lodz.p.it.spjava.e11.sa.dto.SubstrateDTO;
import pl.lodz.p.it.spjava.e11.sa.ejb.facade.SubstrateFacade;
import pl.lodz.p.it.spjava.e11.sa.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.it.spjava.e11.sa.ejb.manager.SubstrateManager;
import pl.lodz.p.it.spjava.e11.sa.ejb.manager.ToxicologyManager;
import pl.lodz.p.it.spjava.e11.sa.entity.Substrate;
import pl.lodz.p.it.spjava.e11.sa.exception.AppBaseException;
import pl.lodz.p.it.spjava.e11.sa.exception.SubstrateException;
import pl.lodz.p.it.spjava.e11.sa.utils.converter.SubstrateConverter;

@Stateful
@Interceptors(LoggingInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)

public class SubstrateEndpoint implements Serializable {

    @EJB
    private SubstrateManager substrateManager;

    @EJB
    private ToxicologyManager toxicologyManager;

    @Inject
    private SubstrateFacade substrateFacade;

    @Resource(name = "txRetryLimit")
    private int txRetryLimit;

    private Substrate substrateState;

    @TransactionAttribute(TransactionAttributeType.NEVER)
    @RolesAllowed({"Assessor"})
    public void createSubstrate(SubstrateDTO newSubstrate) throws AppBaseException {

        Substrate substrate = new Substrate();
        substrate.setSubstrateName(newSubstrate.getSubstrateName());
        substrate.setSubstrateDescription(newSubstrate.getSubstrateDescription());

        boolean rollbackTX;
        int retryTXCounter = txRetryLimit;

        do {
            try {
                substrateManager.createSubstrate(substrate);
                rollbackTX = substrateManager.isLastTransactionRollback();
            } catch (SubstrateException se) {
                throw se;
            } catch (AppBaseException | EJBTransactionRolledbackException ex) {
                System.out.println("----------------endpoint--------------------");
                System.out.println("wiadomość  ABE "+ ex.getCause().getMessage());
                System.out.println("wiadomość  ABE 2lvl"+ ex.getCause().getCause().getMessage());
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy:"
                        + ex.getClass().getName());
                rollbackTX = true;
            }

        } while (rollbackTX && --retryTXCounter > 0);
        if (rollbackTX && retryTXCounter == 0) {
            throw SubstrateException.createSubstrateExceptionWithTxRetryRollback();
        }
    }

    public List<SubstrateDTO> listAllSubstrates() throws AppBaseException{
        
        return SubstrateConverter.createListSubstrateDTOFromEntity(substrateManager.downloadAllSubstrates());
    }

@RolesAllowed({"Assessor"})
    public SubstrateDTO getSubstrateForEdition(SubstrateDTO substrateDTO) throws AppBaseException{
        substrateState = substrateFacade.findBySubstrateId(substrateDTO.getSubstrateId());
        return SubstrateConverter.createSubstrateDTOFromEntity(substrateState);
    }
@RolesAllowed({"Assessor"})
    public void saveSubstrateAfterEdition(SubstrateDTO substrateDTO) throws AppBaseException {
        if (null == substrateState) {
            throw SubstrateException.createExceptionWrongState(substrateState);
        }       
        substrateState.setSubstrateName(substrateDTO.getSubstrateName());
        substrateState.setSubstrateDescription(substrateDTO.getSubstrateDescription());
        boolean rollbackTX;
        int retryTXCounter = txRetryLimit;
        do {
            try {
                substrateManager.saveSubstrateAfterEdition(substrateState);
                rollbackTX = substrateManager.isLastTransactionRollback();
            } catch (SubstrateException se) {
                throw se;
            } catch (AppBaseException | EJBTransactionRolledbackException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy:"
                        + ex.getClass().getName());
                rollbackTX = true;
            }
        } while (rollbackTX && --retryTXCounter > 0);
        if (rollbackTX && retryTXCounter
                == 0) {
            throw SubstrateException.createSubstrateExceptionWithTxRetryRollback();
        }
    }

    public SubstrateDTO getSubstrateForCosmetic(String substrateName) throws AppBaseException{
        Substrate substrate = substrateManager.downloadSubstrateForCosmetic(substrateName);
        return SubstrateConverter.createSubstrateDTOFromEntity(substrate);

    }

}
