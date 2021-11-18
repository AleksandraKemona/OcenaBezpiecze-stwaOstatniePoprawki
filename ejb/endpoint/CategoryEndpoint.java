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
import pl.lodz.p.it.spjava.e11.sa.dto.AnalysisDTO;
import pl.lodz.p.it.spjava.e11.sa.dto.CategoryDTO;
import pl.lodz.p.it.spjava.e11.sa.dto.CosmeticDTO;
import pl.lodz.p.it.spjava.e11.sa.dto.SubstrateDTO;
import pl.lodz.p.it.spjava.e11.sa.ejb.facade.CategoryFacade;
import pl.lodz.p.it.spjava.e11.sa.ejb.facade.CosmeticFacade;
import pl.lodz.p.it.spjava.e11.sa.ejb.manager.CategoryManager;
import pl.lodz.p.it.spjava.e11.sa.ejb.manager.CosmeticManager;
import pl.lodz.p.it.spjava.e11.sa.entity.Analysis;
import pl.lodz.p.it.spjava.e11.sa.entity.Category;
import pl.lodz.p.it.spjava.e11.sa.entity.Cosmetic;
import pl.lodz.p.it.spjava.e11.sa.entity.Substrate;
import pl.lodz.p.it.spjava.e11.sa.exception.AppBaseException;
import pl.lodz.p.it.spjava.e11.sa.exception.CategoryException;
import pl.lodz.p.it.spjava.e11.sa.exception.SubstrateException;
import pl.lodz.p.it.spjava.e11.sa.utils.converter.AccountsConverter;
import pl.lodz.p.it.spjava.e11.sa.utils.converter.AnalysisConverter;
import pl.lodz.p.it.spjava.e11.sa.utils.converter.CategoryConverter;

@Stateful
@RolesAllowed({"Assessor"})
public class CategoryEndpoint implements Serializable {

    @EJB
    private CategoryManager categoryManager;

    @EJB
    private CosmeticManager cosmeticManager;

    @EJB
    private CategoryFacade categoryFacade;

    @EJB
    private CosmeticFacade cosmeticFacade;

    @Resource(name = "txRetryLimit")
    private int txRetryLimit;

    private Category categoryState;

    @TransactionAttribute(TransactionAttributeType.NEVER)
    public void createCategory(CategoryDTO newCategory, List<AnalysisDTO> selectedAnalysisList) throws AppBaseException {
        
        Category category = new Category();
        category.setCategoryName(newCategory.getCategoryName());
        boolean rollbackTX;
        int retryTXCounter = txRetryLimit;

        do {
            try {
                categoryManager.createCategory(category, selectedAnalysisList);
                rollbackTX = categoryManager.isLastTransactionRollback();
            }catch(CategoryException ce){
                throw ce;
            } catch (AppBaseException | EJBTransactionRolledbackException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy:"
                        + ex.getClass().getName());
                rollbackTX = true;
            }

        } while (rollbackTX && --retryTXCounter > 0);
        if (rollbackTX && retryTXCounter == 0) {
            throw CategoryException.createCategoryExceptionWithTxRetryRollback();
        }
    }

    @RolesAllowed({"Assessor", "Sales", "LabTechnician"})
    public List<CategoryDTO> listAllCategories() throws AppBaseException {
        return CategoryConverter.createListCategoriesDTOFromEntity(categoryManager.downloadAllCategories());
    }

    public CategoryDTO getCategoryForEdition(CategoryDTO category) throws AppBaseException {
        categoryState = categoryFacade.findByCategoryId(category.getCategoryId());
        return CategoryConverter.createCategoryDTOFromEntity(categoryState);

    }

    @RolesAllowed({"Assessor", "Sales", "LabTechnician"})
    public CategoryDTO getCategoryForCosmetic(CategoryDTO category) {
        return CategoryConverter.createCategoryDTOFromEntity(categoryManager.downloadCategoryForCosmetic(category.getCategoryId()));

    }

    @RolesAllowed({"Assessor", "Sales", "LabTechnician"})
    public List<AnalysisDTO> findDemandedAnalysis(CategoryDTO categoryDTO) throws AppBaseException {
        Category category = categoryFacade.findByCategoryId(categoryDTO.getCategoryId());
        List<Analysis> listAnalysis = category.getDemandsAnalysis();
        return AnalysisConverter.createListAnalysisDTOFromEntity(listAnalysis);
    }

    public void saveCategoryAfterEdition(CategoryDTO categoryDTO, List<AnalysisDTO> selectedAnalysisList) throws AppBaseException {
        if (null == categoryState) {
            throw CategoryException.createExceptionWrongState(categoryState);
        }
        categoryState.setDemandsAnalysis(AnalysisConverter.createListAnalysisEntityFromDTO(categoryDTO.getDemandsAnalysis()));
        categoryState.setCategoryName(categoryDTO.getCategoryName());

        boolean rollbackTX;
        int retryTXCounter = txRetryLimit;
        do {
            try {
                categoryManager.saveCategoryAfterEdition(categoryState, selectedAnalysisList);
                rollbackTX = categoryManager.isLastTransactionRollback();
            } catch (CategoryException se) {
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
            throw CategoryException.createCategoryExceptionWithTxRetryRollback();
        }
    }

    @TransactionAttribute(TransactionAttributeType.NEVER)
    @RolesAllowed({"Sales"})
    public void chooseCategory(CosmeticDTO cosmeticDTO, String categoryName) throws AppBaseException {
        Cosmetic cosmetic = new Cosmetic();
        cosmetic.setOrderNb(cosmeticDTO.getOrderNb());
        cosmetic.setName(cosmeticDTO.getName());
        cosmetic.setComposition(cosmeticDTO.getComposition());
        cosmetic.setAssessedBy(AccountsConverter.createAssessorEntityFromDTO(cosmeticDTO.getAssessedBy()));
        boolean rollbackTX;
        int retryTXCounter = txRetryLimit;
        do {
            try {
                cosmeticManager.chooseCategory(cosmetic, categoryName);
                rollbackTX = cosmeticManager.isLastTransactionRollback();
            } catch (EJBTransactionRolledbackException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy: "
                        + ex.getClass().getName()
                        + " z komunikatem: " + ex.getMessage());
                rollbackTX = true;
            }

        } while (rollbackTX && --retryTXCounter > 0);

        if (rollbackTX && retryTXCounter == 0) {
            throw new IllegalStateException("przekroczono.liczbę.prób.odwołanych.transakcji");
        }
    }

}
