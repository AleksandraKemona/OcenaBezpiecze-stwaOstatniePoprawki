package pl.lodz.p.it.spjava.e11.sa.ejb.endpoint;

import java.io.Serializable;
import java.util.ArrayList;
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
import pl.lodz.p.it.spjava.e11.sa.dto.ToxicologyDTO;
import pl.lodz.p.it.spjava.e11.sa.ejb.facade.AdministratorFacade;
import pl.lodz.p.it.spjava.e11.sa.ejb.facade.AssessorFacade;
import pl.lodz.p.it.spjava.e11.sa.ejb.facade.CategoryFacade;
import pl.lodz.p.it.spjava.e11.sa.ejb.facade.CosmeticFacade;
import pl.lodz.p.it.spjava.e11.sa.ejb.facade.SalesFacade;
import pl.lodz.p.it.spjava.e11.sa.ejb.facade.ToxicologyFacade;
import pl.lodz.p.it.spjava.e11.sa.ejb.manager.AccountManager;
import pl.lodz.p.it.spjava.e11.sa.ejb.manager.CosmeticManager;
import pl.lodz.p.it.spjava.e11.sa.entity.Administrator;
import pl.lodz.p.it.spjava.e11.sa.entity.Assessor;
import pl.lodz.p.it.spjava.e11.sa.entity.Cosmetic;
import pl.lodz.p.it.spjava.e11.sa.entity.Sales;
import pl.lodz.p.it.spjava.e11.sa.exception.AppBaseException;
import pl.lodz.p.it.spjava.e11.sa.exception.CosmeticException;
import pl.lodz.p.it.spjava.e11.sa.utils.converter.CategoryConverter;
import pl.lodz.p.it.spjava.e11.sa.utils.converter.CosmeticConverter;
import pl.lodz.p.it.spjava.e11.sa.utils.converter.ToxicologyConverter;

@Stateful
public class CosmeticEndpoint implements Serializable {

    @EJB
    private CosmeticManager cosmeticManager;

    @EJB
    private AccountManager accountManager;

    @EJB
    private CosmeticFacade cosmeticFacade;

    @EJB
    private SalesFacade salesFacade;

    @EJB
    private AssessorFacade assessorFacade;

    @EJB
    private AdministratorFacade administratorFacade;

    @EJB
    private CategoryFacade categoryFacade;

    private Cosmetic cosmeticState;

    @Resource(name = "txRetryLimit")
    private int txRetryLimit;

    @TransactionAttribute(TransactionAttributeType.NEVER)
    @RolesAllowed({"Sales"})
    public void createCosmetic(CosmeticDTO newCosmetic, String categoryName, String userName) throws AppBaseException {
        Long salesId = accountManager.getUserId(userName);
        Sales sales = salesFacade.findById(salesId);
        Cosmetic cosmetic = new Cosmetic();
        cosmetic.setOrderNb(newCosmetic.getOrderNb());
        cosmetic.setName(newCosmetic.getName());
        cosmetic.setComposition(newCosmetic.getComposition());
        cosmetic.setCreatedBy(sales);
        cosmetic.setAssessedBy(null);
        cosmetic.setTestedBy(null);
        cosmetic.setToxicology(null);
        boolean rollbackTX;
        int retryTXCounter = txRetryLimit;

        do {
            try {
                cosmeticManager.createCosmetic(cosmetic, categoryName);
                rollbackTX = cosmeticManager.isLastTransactionRollback();
            } catch (CosmeticException se) {
                throw se;
            } catch (AppBaseException | EJBTransactionRolledbackException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy:"
                        + ex.getClass().getName());
                rollbackTX = true;
            }

        } while (rollbackTX && --retryTXCounter > 0);
        if (rollbackTX && retryTXCounter == 0) {
            throw CosmeticException.createCosmeticExceptionWithTxRetryRollback();
        }
    }

    @TransactionAttribute(TransactionAttributeType.NEVER)
    public void confirmCosmetic(CosmeticDTO newCosmetic) throws AppBaseException {
        String cosmeticName = newCosmetic.getName();
        List<String> cosmeticNamesList = new ArrayList<>();
        boolean rollbackTX;
        int retryTXCounter = txRetryLimit;

        try {
            List<Cosmetic> cosmetics = cosmeticFacade.findAll();
            for (Cosmetic cosmetic : cosmetics) {
                String cosmeticNameForList = cosmetic.getName();
                cosmeticNamesList.add(cosmeticNameForList);
            }
            if (cosmeticNamesList.contains(cosmeticName)) {
                throw CosmeticException.createCosmeticDoesExistException(cosmeticName);
            }
        } catch (CosmeticException ce) {
            throw ce;
        } catch (AppBaseException | EJBTransactionRolledbackException ex) {
            Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                    + " wykonania metody biznesowej zakończona wyjątkiem klasy:"
                    + ex.getClass().getName());
            rollbackTX = true;
        }
    }

    @RolesAllowed({"Sales", "LabTechnician", "Assessor"})
    public List<CosmeticDTO> listAllCosmetics() throws AppBaseException {
        return CosmeticConverter.createListCosmeticDTOFromEntityTest(cosmeticManager.downloadAllCosmetics());
    }

    @RolesAllowed({"Sales"})
    public CosmeticDTO getCosmeticForEdition(CosmeticDTO cosmeticDTO) throws AppBaseException {
        cosmeticState = cosmeticFacade.findById(cosmeticDTO.getId());
        return CosmeticConverter.createCosmeticDTOFromEntityTest(cosmeticState);
    }

    @RolesAllowed({"Sales"})
    public CosmeticDTO getCosmeticForDeletion(Long cosmeticId) throws AppBaseException {
        cosmeticState = cosmeticFacade.findById(cosmeticId);
        return CosmeticConverter.createCosmeticDTOFromEntityTest(cosmeticState);
    }

    @TransactionAttribute(TransactionAttributeType.NEVER)
    @RolesAllowed({"Sales"})
    public void confirmDeleteCosmetic(CosmeticDTO cosmeticDTO) throws AppBaseException {
        if (null == cosmeticState) {
            throw CosmeticException.createExceptionWrongStateDeletion(cosmeticState);
        }
        boolean rollbackTX;
        int retryTXCounter = txRetryLimit;
        do {
            try {
                cosmeticManager.confirmDeleteCosmetic(cosmeticState);
                rollbackTX = cosmeticManager.isLastTransactionRollback();
            } catch (CosmeticException ce) {
                throw ce;

            } catch (AppBaseException | EJBTransactionRolledbackException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy:"
                        + ex.getClass().getName());
                rollbackTX = true;
            }
        } while (rollbackTX && --retryTXCounter > 0);
        if (rollbackTX && retryTXCounter == 0) {
            throw CosmeticException.createCosmeticExceptionWithTxRetryRollback();
        }
    }

    @RolesAllowed({"Sales"})
    public void saveCosmeticAfterEdition(CosmeticDTO cosmeticDTO, String categoryName) throws AppBaseException {
        if (null == cosmeticState) {
            throw CosmeticException.createExceptionWrongState(cosmeticState);
        }
        cosmeticState.setName(cosmeticDTO.getName());
        cosmeticState.setCategoryId(CategoryConverter.createCategoryEntityFromDTOForCosmetic(cosmeticDTO.getCategory()));
        cosmeticState.setComposition(cosmeticDTO.getComposition());
        boolean rollbackTX;
        int retryTXCounter = txRetryLimit;
        do {
            try {
                cosmeticFacade.edit(cosmeticState);
                cosmeticState = null;
                rollbackTX = cosmeticManager.isLastTransactionRollback();
            } catch (CosmeticException ce) {
                throw ce;
            } catch (AppBaseException | EJBTransactionRolledbackException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy:"
                        + ex.getClass().getName());
                rollbackTX = true;
            }
        } while (rollbackTX && --retryTXCounter > 0);
        if (rollbackTX && retryTXCounter
                == 0) {
            throw CosmeticException.createCosmeticExceptionWithTxRetryRollback();
        }
    }

    @RolesAllowed({"LabTechnician"})
    public void saveResults(CosmeticDTO cosmeticDTO) throws AppBaseException {
        if (null == cosmeticState) {
            throw CosmeticException.createExceptionWrongState(cosmeticState);
        }
        cosmeticState.setResults(cosmeticDTO.getResults());
        boolean rollbackTX;
        int retryTXCounter = txRetryLimit;
        do {
            try {
                cosmeticFacade.edit(cosmeticState);
                cosmeticState = null;
                rollbackTX = cosmeticManager.isLastTransactionRollback();
            } catch (CosmeticException ce) {
                throw ce;
            } catch (AppBaseException | EJBTransactionRolledbackException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy:"
                        + ex.getClass().getName());
                rollbackTX = true;
            }
        } while (rollbackTX && --retryTXCounter > 0);
        if (rollbackTX && retryTXCounter
                == 0) {
            throw CosmeticException.createCosmeticExceptionWithTxRetryRollback();
        }
    }

    @RolesAllowed({"Sales", "Assessor", "LabTechnician"})
    public CosmeticDTO downloadCosmeticForDetails(Long cosmeticId) throws AppBaseException {
        cosmeticState = cosmeticFacade.findById(cosmeticId);
        return CosmeticConverter.createCosmeticDTOFromEntity(cosmeticState);
    }

    @RolesAllowed({"Assessor"})
    public void updateCosmeticToxicology(CosmeticDTO cosmeticDTO, String toxicologyName) throws AppBaseException {
        cosmeticState = cosmeticFacade.findById(cosmeticDTO.getId());
        if (null == cosmeticState) {
            throw CosmeticException.createExceptionWrongState(cosmeticState);
        }
        ToxicologyDTO toxicologyDTO = cosmeticDTO.getToxicology();
        cosmeticState.setToxicology(ToxicologyConverter.createToxicologyEntityFromDTO(toxicologyDTO));
        boolean rollbackTX;
        int retryTXCounter = txRetryLimit;
        do {
            try {
                cosmeticManager.updateCosmeticToxicology(cosmeticState);
                rollbackTX = cosmeticManager.isLastTransactionRollback();
            } catch (CosmeticException ce) {
                throw ce;
            } catch (AppBaseException | EJBTransactionRolledbackException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy:"
                        + ex.getClass().getName());
                rollbackTX = true;
            }
        } while (rollbackTX && --retryTXCounter > 0);
        if (rollbackTX && retryTXCounter
                == 0) {
            throw CosmeticException.createCosmeticExceptionWithTxRetryRollback();
        }
    }

    public CosmeticDTO chooseCosmetic(CosmeticDTO cosmeticDTO) throws AppBaseException {
        cosmeticState = cosmeticFacade.findById(cosmeticDTO.getId());
        return CosmeticConverter.createCosmeticDTOFromEntityTest(cosmeticState);
    }

    public void setChoosenCosmetic(CosmeticDTO cosmeticDTO, String userName) throws AppBaseException {
        Long assessorId = accountManager.getUserId(userName);
        Assessor assessor = assessorFacade.findById(assessorId);
        cosmeticState.setAssessedBy(assessor);
        boolean rollbackTX;
        int retryTXCounter = txRetryLimit;
        do {
            try {
                cosmeticManager.setChoosenCosmetic(cosmeticState);
                rollbackTX = cosmeticManager.isLastTransactionRollback();
            } catch (CosmeticException ce) {
                throw ce;
            } catch (AppBaseException | EJBTransactionRolledbackException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy:"
                        + ex.getClass().getName());
                rollbackTX = true;
            }
        } while (rollbackTX && --retryTXCounter > 0);
        if (rollbackTX && retryTXCounter
                == 0) {
            throw CosmeticException.createCosmeticExceptionWithTxRetryRollback();
        }

    }

    public void setCosmeticNotChoosen(CosmeticDTO cosmeticDTO) throws AppBaseException {
        cosmeticState.setAssessedBy(null);
        boolean rollbackTX;
        int retryTXCounter = txRetryLimit;
        do {
            try {
                cosmeticManager.setNotChoosenCosmetic(cosmeticState);
                rollbackTX = cosmeticManager.isLastTransactionRollback();
            } catch (CosmeticException ce) {
                throw ce;
            } catch (AppBaseException | EJBTransactionRolledbackException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy:"
                        + ex.getClass().getName());
                rollbackTX = true;
            }
        } while (rollbackTX && --retryTXCounter > 0);
        if (rollbackTX && retryTXCounter
                == 0) {
            throw CosmeticException.createCosmeticExceptionWithTxRetryRollback();
        }

    }

}
