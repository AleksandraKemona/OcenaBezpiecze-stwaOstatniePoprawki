/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.spjava.e11.sa.web.category;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import pl.lodz.p.it.spjava.e11.sa.dto.AnalysisDTO;
import pl.lodz.p.it.spjava.e11.sa.ejb.facade.CategoryFacade;
import pl.lodz.p.it.spjava.e11.sa.web.utils.ContextUtils;

import pl.lodz.p.it.spjava.e11.sa.dto.CategoryDTO;
import pl.lodz.p.it.spjava.e11.sa.dto.CosmeticDTO;
import pl.lodz.p.it.spjava.e11.sa.dto.SubstrateDTO;
import pl.lodz.p.it.spjava.e11.sa.ejb.endpoint.CategoryEndpoint;
import pl.lodz.p.it.spjava.e11.sa.ejb.facade.AnalysisFacade;
import pl.lodz.p.it.spjava.e11.sa.entity.Category;
import pl.lodz.p.it.spjava.e11.sa.exception.AppBaseException;
import pl.lodz.p.it.spjava.e11.sa.exception.CategoryException;
import pl.lodz.p.it.spjava.e11.sa.exception.SubstrateException;
import pl.lodz.p.it.spjava.e11.sa.utils.converter.CategoryConverter;
import pl.lodz.p.it.spjava.e11.sa.web.cosmetic.CosmeticController;
import pl.lodz.p.it.spjava.e11.sa.web.substrate.SubstrateController;

@Named("categorySession")
@SessionScoped
public class CategoryController implements Serializable {

    private static final Logger LOG = Logger.getLogger(CategoryController.class.getName());

    @EJB
    private AnalysisFacade analysisFacade;

    @EJB
    private CategoryEndpoint categoryEndpoint;

    @Getter
    private CategoryDTO editCategory;

    @Getter
    private CategoryDTO appliedCategory;

    @Getter
    private CategoryDTO createdCategory;

    public String resetSession() {
        ContextUtils.invalidateSession();
        return "cancelAction";
    }

//    public List<AnalysisDTO> getDemandedAnalysis() {
//        
//        throw new UnsupportedOperationException("Not supported yet."); //ToDo change body of generated methods, choose Tools | Templates.
//    }
    public String createCategory(CategoryDTO newCategory, List<AnalysisDTO> selectedAnalysisList) {

        try {
            createdCategory = newCategory;
            categoryEndpoint.createCategory(newCategory, selectedAnalysisList);
            createdCategory = null;
            return "listCategories";
        } catch (CategoryException ce) {
            if (CategoryException.KEY_CATEGORY_NAME_EXISTS.equals(ce.getMessage())) {
                ContextUtils.emitInternationalizedMessage("createNewCategoryForm:name",
                        CategoryException.KEY_CATEGORY_NAME_EXISTS);
            } else {
                Logger.getLogger(CategoryController.class.getName()).log(Level.SEVERE,
                        "Zgłoszenie w metodzie akcji createSubstrate wyjątku: ", ce);
            }
            return null;
        } catch (AppBaseException abe) {
            Logger.getLogger(CategoryController.class.getName()).log(Level.SEVERE,
                    "Zgłoszenie w metodzie akcji createSubstrate wyjątku typu: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            }
            return null;
        }
    }

    public List<CategoryDTO> listAllCategories() {
        System.out.println("-----------------------Category Controller---------------");
        try {
            List<CategoryDTO> list = categoryEndpoint.listAllCategories();
            System.out.println("-----------------------Category Controller 2---------------");
            System.out.println("categoryEndpoint.listAllCategories() " + list);
            return categoryEndpoint.listAllCategories();
        } catch (AppBaseException abe) {
            System.out.println("-----------------------Category Controller 3---------------");
            Logger.getLogger(CosmeticController.class.getName()).log(Level.SEVERE,
                    "Zgłoszenie w metodzie akcji createSubstrate wyjątku typu: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            }
            return null;
        }
    }

    public CategoryDTO getCategoryForEdition(CategoryDTO category) {
        try {
            editCategory = categoryEndpoint.getCategoryForEdition(category);
            return editCategory;
        } catch (CategoryException ce) {
            if (CategoryException.KEY_CATEGORY_NOT_READ_FOR_EDITION.equals(ce.getMessage())) {
                ContextUtils.emitInternationalizedMessage("editCategoryForm:name",
                        CategoryException.KEY_CATEGORY_NOT_READ_FOR_EDITION);
            } else {
                Logger.getLogger(CategoryController.class.getName()).log(Level.SEVERE,
                        "Zgłoszenie w metodzie akcji getCategoryForEdition wyjątku: ", ce);
            }
            return null;
        } catch (AppBaseException abe) {
            Logger.getLogger(CategoryController.class.getName()).log(Level.SEVERE,
                    "Zgłoszenie w metodzie akcji getCategoryForEdition wyjątku typu: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            }
            return null;
        }

    }

    public CategoryDTO getCategoryForCosmetic(CategoryDTO category) {
        appliedCategory = categoryEndpoint.getCategoryForCosmetic(category);
        return appliedCategory;

    }

    public List<AnalysisDTO> findDemandedAnalysis(CategoryDTO categoryDTO) {
        try {
            System.out.println("------------Controller-------");
            System.out.println("category DTO "+ categoryDTO);
            System.out.println("wynik z endpointa "+ categoryEndpoint.findDemandedAnalysis(categoryDTO));
            return categoryEndpoint.findDemandedAnalysis(categoryDTO);
        } catch (CategoryException ce) {
            if (CategoryException.KEY_CATEGORY_NOT_READ_FOR_EDITION.equals(ce.getMessage())) {
                ContextUtils.emitInternationalizedMessage("editCategoryForm:name",
                        CategoryException.KEY_CATEGORY_NOT_READ_FOR_EDITION);
            } else {
                Logger.getLogger(CategoryController.class.getName()).log(Level.SEVERE,
                        "Zgłoszenie w metodzie akcji getCategoryForEdition wyjątku: ", ce);
            }
            return null;
        } catch (AppBaseException abe) {
            Logger.getLogger(CategoryController.class.getName()).log(Level.SEVERE,
                    "Zgłoszenie w metodzie akcji getCategoryForEdition wyjątku typu: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            }

        }
        return null;
    }

    public String saveCategoryAfterEdition(CategoryDTO categoryDTO, List<AnalysisDTO> selectedAnalysisList) {
        try {
            categoryEndpoint.saveCategoryAfterEdition(categoryDTO, selectedAnalysisList);
            return "listCategories";
        } catch (CategoryException ce) {
            if (CategoryException.KEY_CATEGORY_NAME_EXISTS.equals(ce.getMessage())) {
                ContextUtils.emitInternationalizedMessage("editCategoryForm:name",
                        CategoryException.KEY_CATEGORY_NAME_EXISTS);
            } else if (CategoryException.KEY_CATEGORY_OPTIMISTIC_LOCK.equals(ce.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, CategoryException.KEY_CATEGORY_OPTIMISTIC_LOCK);
            } else {
                Logger.getLogger(CategoryController.class.getName()).log(Level.SEVERE,
                        "Zgłoszenie w metodzie akcji editCategory wyjątku: ", ce);
            }
            return null;
        } catch (AppBaseException abe) {
            Logger.getLogger(CategoryController.class.getName()).log(Level.SEVERE,
                    "Zgłoszenie w metodzie akcji editCategory wyjątku typu: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            }
            return null;
        }
    }

    public void chooseCategory(CosmeticDTO newCosmetic, String categoryName) {
        try {
            System.out.println("----------------Category Controller-----------");
            System.out.println("new Cosmetic " + newCosmetic);
            System.out.println("categoryName " + categoryName);
            categoryEndpoint.chooseCategory(newCosmetic, categoryName);
        } catch (CategoryException ce) {
            if (CategoryException.KEY_CATEGORY_NOT_READ_FOR_EDITION.equals(ce.getMessage())) {
                ContextUtils.emitInternationalizedMessage("editCategoryForm:name",
                        CategoryException.KEY_CATEGORY_NOT_READ_FOR_EDITION);
            } else {
                Logger.getLogger(CategoryController.class.getName()).log(Level.SEVERE,
                        "Zgłoszenie w metodzie akcji getCategoryForEdition wyjątku: ", ce);
            }
        } catch (AppBaseException abe) {
            Logger.getLogger(CategoryController.class.getName()).log(Level.SEVERE,
                    "Zgłoszenie w metodzie akcji getCategoryForEdition wyjątku typu: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            }
        }
    }

    @PostConstruct
    private void init() {
        LOG.severe("Session started: " + ContextUtils.getSessionID());
    }

}
