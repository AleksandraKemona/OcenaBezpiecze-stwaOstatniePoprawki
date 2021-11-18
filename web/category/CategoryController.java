package pl.lodz.p.it.spjava.e11.sa.web.category;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import lombok.Getter;
import pl.lodz.p.it.spjava.e11.sa.dto.AnalysisDTO;
import pl.lodz.p.it.spjava.e11.sa.web.utils.ContextUtils;

import pl.lodz.p.it.spjava.e11.sa.dto.CategoryDTO;
import pl.lodz.p.it.spjava.e11.sa.dto.CosmeticDTO;
import pl.lodz.p.it.spjava.e11.sa.ejb.endpoint.CategoryEndpoint;
import pl.lodz.p.it.spjava.e11.sa.ejb.facade.AnalysisFacade;
import pl.lodz.p.it.spjava.e11.sa.exception.AppBaseException;
import pl.lodz.p.it.spjava.e11.sa.exception.CategoryException;
import pl.lodz.p.it.spjava.e11.sa.web.cosmetic.CosmeticController;

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

    public String createCategory(CategoryDTO newCategory, List<AnalysisDTO> selectedAnalysisList) {
    try {
            createdCategory = newCategory;
            categoryEndpoint.createCategory(newCategory, selectedAnalysisList);
            createdCategory = null;
            return "listCategories";
        } catch (CategoryException ce) {
            if (CategoryException.KEY_CATEGORY_NAME_EXISTS.equals(ce.getMessage())) {
                ContextUtils.emitInternationalizedMessage("createCategoryForm:name",
                        CategoryException.KEY_CATEGORY_NAME_EXISTS);
            }else if(CategoryException.KEY_CATEGORY_ANALYSIS_DEMANDED.equals(ce.getMessage())){
                ContextUtils.emitInternationalizedMessage("createCategoryForm:demandedAnalysis",
                        CategoryException.KEY_CATEGORY_ANALYSIS_DEMANDED);              
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
        try {
            return categoryEndpoint.listAllCategories();
        } catch (AppBaseException abe) {
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

    public String chooseCategory(CosmeticDTO newCosmetic, String categoryName) {
        try {
            categoryEndpoint.chooseCategory(newCosmetic, categoryName);
            return "confirmCosmetic";
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
        return "confirmCosmetic";
    }

    @PostConstruct
    private void init() {
        LOG.severe("Session started: " + ContextUtils.getSessionID());
    }

}
