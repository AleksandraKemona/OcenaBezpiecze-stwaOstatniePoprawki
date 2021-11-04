/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.spjava.e11.sa.utils.converter;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import pl.lodz.p.it.spjava.e11.sa.dto.CategoryDTO;
import pl.lodz.p.it.spjava.e11.sa.ejb.facade.CategoryFacade;
import pl.lodz.p.it.spjava.e11.sa.entity.Category;

/**
 *
 * @author Ola
 */
public class CategoryConverter {
    
    @EJB
    private CategoryFacade categoryFacade;
    
    public static Category createCategoryEntityFromDTO(CategoryDTO categoryDTO) {
        return new Category(categoryDTO.getCategoryId(),
        categoryDTO.getCategoryName(), AnalysisConverter.createListAnalysisEntityFromDTO(categoryDTO.getDemandsAnalysis()));
    }
    
    public static Category createCategoryEntityFromDTOForCosmetic(CategoryDTO categoryDTO) {
        System.out.println("Category converter" + categoryDTO);
        
        return new Category(categoryDTO.getCategoryId(),
        categoryDTO.getCategoryName());
    }

    public static CategoryDTO createCategoryDTOFromEntity(Category category) {
        return new CategoryDTO(category.getCategoryId(),
        category.getCategoryName(), AnalysisConverter.createListAnalysisDTOFromEntity(category.getDemandsAnalysis()));
    }
    
    public static CategoryDTO createCategoryDTOFromEntityForCosmetic(Category category) {
        return new CategoryDTO(category.getCategoryId(),
        category.getCategoryName());
    }
    
//    List<Cosmetic> listCosmetics = cosmeticFacade.findAll();
    public static List<CategoryDTO> createListCategoriesDTOFromEntity(List<Category> listCategories) {
        List<CategoryDTO> listCategoriesDTO = new ArrayList<>();

        for (Category category : listCategories) {
            CategoryDTO categoryDTO = new CategoryDTO(category.getCategoryId(),
        category.getCategoryName(),AnalysisConverter.createListAnalysisDTOFromEntity(category.getDemandsAnalysis()));
            listCategoriesDTO.add(categoryDTO);
        }

        return listCategoriesDTO;
    }
    
    public static List<Category> createListCategoriesEntityFromDTO(List<CategoryDTO> listCategoriesDTO) {
        List<Category> listCategories = new ArrayList<>();

        for (CategoryDTO categoryDTO : listCategoriesDTO) {
            Category category = new Category(categoryDTO.getCategoryId(),
        categoryDTO.getCategoryName(),AnalysisConverter.createListAnalysisEntityFromDTO(categoryDTO.getDemandsAnalysis()));
            listCategoriesDTO.add(categoryDTO);
        }

        return listCategories;
    }
  
}
