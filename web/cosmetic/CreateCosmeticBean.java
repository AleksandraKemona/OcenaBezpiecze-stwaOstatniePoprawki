package pl.lodz.p.it.spjava.e11.sa.web.cosmetic;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.spjava.e11.sa.dto.AssessorDTO;
import pl.lodz.p.it.spjava.e11.sa.dto.CategoryDTO;
import pl.lodz.p.it.spjava.e11.sa.dto.CosmeticDTO;
import pl.lodz.p.it.spjava.e11.sa.ejb.facade.CategoryFacade;
import pl.lodz.p.it.spjava.e11.sa.exception.CosmeticException;
import pl.lodz.p.it.spjava.e11.sa.web.category.CategoryController;

@SessionScoped
@Named
public class CreateCosmeticBean implements Serializable {

    @Inject
    private CosmeticController cosmeticController;

    @Inject
    private CategoryController categoryController;

    @EJB
    private CategoryFacade categoryFacade;
   
    @Setter
    private CosmeticDTO newCosmetic;

    @Setter
    private String categoryName;

    @Getter
    @Setter
    private List<CategoryDTO> listCategoryDTO;

    public CosmeticDTO getNewCosmetic() {
        if (null != newCosmetic) {
            return newCosmetic;
        } else {
            return new CosmeticDTO();
        }
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String begin() {
        newCosmetic = new CosmeticDTO();
        categoryName = new String();
        listCategoryDTO = categoryController.listAllCategories();
        return "createCosmetic";
    }


    public String confirmCosmetic() throws CosmeticException{
                String fromController ="";      
        if (null != newCosmetic && null != newCosmetic.getName()) {        
            newCosmetic.setAssessedBy(new AssessorDTO(-100l, " "));
            categoryController.chooseCategory(newCosmetic,categoryName);
            fromController =cosmeticController.confirmCosmetic(newCosmetic);   
        }
        return fromController;
    }
    
    public String returnConfirmCosmetic(){
        return "confirmCosmetic";
    }
    
    public String createCosmetic(String userName) {
        cosmeticController.createCosmetic(newCosmetic, categoryName, userName);
        return "listCosmetics";
    }

    public String abort() {
        return "listCosmetics";
    }  
}
