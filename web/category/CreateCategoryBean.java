package pl.lodz.p.it.spjava.e11.sa.web.category;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.spjava.e11.sa.dto.AnalysisDTO;
import pl.lodz.p.it.spjava.e11.sa.dto.CategoryDTO;
import pl.lodz.p.it.spjava.e11.sa.ejb.endpoint.AnalysisEndpoint;
import pl.lodz.p.it.spjava.e11.sa.exception.AppBaseException;

@SessionScoped
@Named
public class CreateCategoryBean implements Serializable {

    @Inject
    private CategoryController categoryController;
    
    @EJB
    private AnalysisEndpoint analysisEndpoint;
    
    @Setter
    private CategoryDTO newCategory;
    
    @Getter
    @Setter
    private List<AnalysisDTO> listAnalysisDTO;
    
    private List<AnalysisDTO> selectedAnalysisList;
    
    @Setter
    private String demandedAnalysis;
    

    public CategoryDTO getNewCategory() {
        if (null != newCategory) {

            return newCategory;
        } else {
            return new CategoryDTO();
        }
    }

   public String getDemandedAnalysis() {
        return demandedAnalysis;
    }

    public String begin() throws AppBaseException{
        newCategory = new CategoryDTO();
        demandedAnalysis = new String();
        System.out.println("demanded Bean" + demandedAnalysis);
        listAnalysisDTO = analysisEndpoint.listAllAnalysis();
       

        return "createCategory";
    }

    public CategoryDTO getCategoryDTO() {
        return newCategory;
    }


    public String createCategory() {
        
        if (null != newCategory && null != newCategory.getCategoryName()) {

            selectedAnalysisList = new  ArrayList<>();
            
            for (AnalysisDTO analysisDTO : listAnalysisDTO) {
                if (analysisDTO.isSelected() == true) {
                    selectedAnalysisList.add(analysisDTO);                  
                    analysisDTO.setSelected(false); 
                }               
            } 
            
            categoryController.createCategory(newCategory, selectedAnalysisList);
            return "listCategories";
        } else {
            
            return "listCategories";
        }
    }
    
    public String abort() {
        return "listCategories";
    }
    
    
    //    public String categoryChoiceAction() {
//        newCosmetic.setCategoryDTO(categoryFacade.findByCategoryId(categoryChoiceController.getSelectedCategoryDTO().getCategoryId()));
//        categoryEndpoint.chooseCategory(newCosmetic);
//        return "confirmCosmetic";
//    }
}
