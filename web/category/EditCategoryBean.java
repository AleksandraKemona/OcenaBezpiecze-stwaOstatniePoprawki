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
import pl.lodz.p.it.spjava.e11.sa.web.analysis.AnalysisController;

@SessionScoped
@Named
public class EditCategoryBean implements Serializable {

    @Inject
    private CategoryController categoryController;
    
    @Inject
    private AnalysisController analysisController;
    
    @EJB
    private AnalysisEndpoint analysisEndpoint;

    private CategoryDTO editedCategory;

    @Getter
    @Setter
    private List<AnalysisDTO> listAnalysisDTO;

    private List<AnalysisDTO> selectedAnalysisList;

    public CategoryDTO getEditedCategory() {
        if (null != editedCategory) {
            return editedCategory;
        } else {
            return new CategoryDTO();
        }
    }

    public void setEditedCategory(CategoryDTO editedCategory){
        listAnalysisDTO = analysisController.listAllAnalysis();
        this.editedCategory = categoryController.getCategoryForEdition(editedCategory);
    }

    public String saveCategory() {
        if (null == editedCategory) {
            return "main";
        }
        selectedAnalysisList = new ArrayList<>();
        for (AnalysisDTO analysisDTO : listAnalysisDTO) {
            if (analysisDTO.isSelected() == true) {
                selectedAnalysisList.add(analysisDTO);
                analysisDTO.setSelected(false);  
            }
        }
        return categoryController.saveCategoryAfterEdition(editedCategory, selectedAnalysisList);
    }

    public String abort() {
        return "listCategories";
    }

}
