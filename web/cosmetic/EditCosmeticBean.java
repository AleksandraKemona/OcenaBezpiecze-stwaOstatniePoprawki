package pl.lodz.p.it.spjava.e11.sa.web.cosmetic;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.it.spjava.e11.sa.dto.CategoryDTO;
import pl.lodz.p.it.spjava.e11.sa.dto.CosmeticDTO;
import pl.lodz.p.it.spjava.e11.sa.web.category.CategoryController;

@Named(value = "editCosmeticBean")
@NoArgsConstructor
@SessionScoped
public class EditCosmeticBean implements Serializable {

    @Inject
    private CosmeticController cosmeticController;

    @Inject
    private CategoryController categoryController;

    @Getter
    @Setter
    private List<CategoryDTO> listCategoryDTO;

    @PostConstruct
    private void init() {
        editedCosmetic = cosmeticController.getEditCosmetic();
        listCategoryDTO = categoryController.listAllCategories();
    }

    private CosmeticDTO editedCosmetic;

    public void setEditedCosmetic(CosmeticDTO editedCosmeticDTO) {
        
        this.editedCosmetic = cosmeticController.getCosmeticForEdition(editedCosmeticDTO);
    }
    
    public CosmeticDTO getEditedCosmetic() {
        if (null != editedCosmetic) {
        return editedCosmetic;
        } else {
            return new CosmeticDTO(); 
        }
    }

    public String saveCosmetic(){
        if (null == editedCosmetic) {
            return "main";
        }
        String categoryName = editedCosmetic.getCategory().getCategoryName();
        return cosmeticController.saveCosmeticAfterEdition(editedCosmetic, categoryName);
    }

    public String abort() {
        return "listCosmetics";
    }
}
