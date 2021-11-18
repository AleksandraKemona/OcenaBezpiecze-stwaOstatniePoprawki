package pl.lodz.p.it.spjava.e11.sa.ejb.manager;

import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import pl.lodz.p.it.spjava.e11.sa.dto.CosmeticDTO;
import pl.lodz.p.it.spjava.e11.sa.dto.SalesDTO;
import pl.lodz.p.it.spjava.e11.sa.ejb.facade.CategoryFacade;
import pl.lodz.p.it.spjava.e11.sa.ejb.facade.CosmeticFacade;
import pl.lodz.p.it.spjava.e11.sa.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.it.spjava.e11.sa.entity.Category;
import pl.lodz.p.it.spjava.e11.sa.entity.Cosmetic;
import pl.lodz.p.it.spjava.e11.sa.entity.Sales;
import pl.lodz.p.it.spjava.e11.sa.exception.AppBaseException;
import pl.lodz.p.it.spjava.e11.sa.exception.CosmeticException;
import pl.lodz.p.it.spjava.e11.sa.utils.converter.CosmeticConverter;

@Stateful
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors(LoggingInterceptor.class)

public class CosmeticManager extends AbstractManager {

    @EJB
    private CosmeticFacade cosmeticFacade;

    @EJB
    private CategoryFacade categoryFacade;

    private Cosmetic editedCosmetic;

    private Cosmetic deletedCosmetic;

    private Cosmetic updatedCosmetic;


    public String createCosmetic(Cosmetic newCosmetic, String categoryName) throws AppBaseException {
        Category category = categoryFacade.findByCategoryName(categoryName);
        newCosmetic.setCategoryId(category);
        categoryName = category.getCategoryName();
        cosmeticFacade.create(newCosmetic);
        return categoryName;
    }

    public List<Cosmetic> downloadAllCosmetics() throws AppBaseException {
        return cosmeticFacade.findAll();
    }

    public Cosmetic downloadCosmeticForDeletion(Long id) {
        deletedCosmetic = cosmeticFacade.find(id);
        cosmeticFacade.refresh(deletedCosmetic);
        return deletedCosmetic;
    }

    public void confirmDeleteCosmetic(Cosmetic cosmeticState) throws AppBaseException {

        if (null == cosmeticState) {
            throw CosmeticException.createExceptionWrongStateDeletion(deletedCosmetic);
        } else {
            cosmeticFacade.findById(cosmeticState.getId());
            cosmeticFacade.remove(cosmeticState);
        }
    }

    public Cosmetic downloadCosmeticForEdition(Long id) {
        editedCosmetic = cosmeticFacade.find(id);
        cosmeticFacade.refresh(editedCosmetic);
        return editedCosmetic;
    }

    public void saveCosmeticAfterEdition(Cosmetic tmp) throws AppBaseException {
        cosmeticFacade.edit(tmp);
    }

    public Cosmetic downloadCosmeticForDetails(Long id) {
        Cosmetic downloadedCosmetic = cosmeticFacade.find(id);
        cosmeticFacade.refresh(downloadedCosmetic);
        return downloadedCosmetic;
    }

    public void chooseCategory(Cosmetic cosmetic, String categoryName) throws AppBaseException {
        Category category = categoryFacade.findByCategoryName(categoryName);
        cosmetic.setCategoryId(category);
    }

    public void updateCosmeticToxicology(Cosmetic tmp) throws AppBaseException {
        updatedCosmetic = tmp;
        cosmeticFacade.edit(updatedCosmetic);

    }

    public void setChoosenCosmetic(Cosmetic cosmetic) throws AppBaseException {
        Cosmetic checkedCosmetic = cosmeticFacade.findById(cosmetic.getId());
        if (checkedCosmetic.getAssessedBy()==null) {
            cosmeticFacade.edit(cosmetic);
        }else{
            throw CosmeticException.createCosmeticAlreadyChoosenException(cosmetic);
        }
    }
    
    public void setNotChoosenCosmetic(Cosmetic cosmetic) throws AppBaseException {
            cosmeticFacade.edit(cosmetic);
    }

}
