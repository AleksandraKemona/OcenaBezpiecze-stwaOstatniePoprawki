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
import pl.lodz.p.it.spjava.e11.sa.dto.CosmeticDTO;
import pl.lodz.p.it.spjava.e11.sa.dto.SubstrateDTO;
import pl.lodz.p.it.spjava.e11.sa.dto.ToxicologyDTO;
import pl.lodz.p.it.spjava.e11.sa.ejb.facade.CosmeticFacade;
import pl.lodz.p.it.spjava.e11.sa.ejb.facade.ToxicologyFacade;
import pl.lodz.p.it.spjava.e11.sa.ejb.manager.ToxicologyManager;
import pl.lodz.p.it.spjava.e11.sa.entity.Toxicology;
import pl.lodz.p.it.spjava.e11.sa.exception.AppBaseException;
import pl.lodz.p.it.spjava.e11.sa.exception.ToxicologyException;
import pl.lodz.p.it.spjava.e11.sa.utils.converter.CosmeticConverter;

@Stateful
@RolesAllowed({"Assessor"})
public class ToxicologyEndpoint implements Serializable{
    @EJB
    private ToxicologyManager toxicologyManager;

    @EJB
    private ToxicologyFacade toxicologyFacade;
    
    @EJB
    private CosmeticFacade cosmeticFacade;

    @Resource(name = "txRetryLimit")
    private int txRetryLimit;

    @TransactionAttribute(TransactionAttributeType.NEVER)
    @RolesAllowed({"Assessor"})
    public void createToxicology(ToxicologyDTO newToxicology, List<SubstrateDTO> appliedSubstratesList, Long connectedCosmeticId) throws AppBaseException {
 
        Toxicology toxicology =new Toxicology();
        CosmeticDTO connectedCosmetic = CosmeticConverter.createCosmeticDTOFromEntity(cosmeticFacade.findById(connectedCosmeticId));
        boolean rollbackTX;
        int retryTXCounter = txRetryLimit;

        do {
            try {
                toxicologyManager.createToxicology(toxicology, appliedSubstratesList, connectedCosmetic);
                rollbackTX = toxicologyManager.isLastTransactionRollback();
            } catch (AppBaseException | EJBTransactionRolledbackException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy:"
                        + ex.getClass().getName());
                rollbackTX = true;
            }

        } while (rollbackTX && --retryTXCounter > 0);
        if (rollbackTX && retryTXCounter == 0) {
            throw ToxicologyException.createToxicologyExceptionWithTxRetryRollback();
        }
    }
    
}
