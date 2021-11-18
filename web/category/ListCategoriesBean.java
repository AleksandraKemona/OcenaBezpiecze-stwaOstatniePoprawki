package pl.lodz.p.it.spjava.e11.sa.web.category;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import pl.lodz.p.it.spjava.e11.sa.dto.CategoryDTO;
import pl.lodz.p.it.spjava.e11.sa.exception.AppBaseException;

@ViewScoped
@Named
public class ListCategoriesBean implements Serializable {

    @Inject
    private CategoryController categoryController;

    @Inject
    private EditCategoryBean editCategoryBean;
    
    @Getter
    private List<CategoryDTO> categories;

    @PostConstruct
    public void init(){
        categories = categoryController.listAllCategories();
    }

    public String edit(CategoryDTO category){
        editCategoryBean.setEditedCategory(category);
       return "editCategory";
    }
}
