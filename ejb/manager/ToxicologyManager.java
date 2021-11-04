/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.spjava.e11.sa.ejb.manager;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import pl.lodz.p.it.spjava.e11.sa.dto.CosmeticDTO;
import pl.lodz.p.it.spjava.e11.sa.dto.SubstrateDTO;
import pl.lodz.p.it.spjava.e11.sa.ejb.endpoint.CosmeticEndpoint;
import pl.lodz.p.it.spjava.e11.sa.ejb.facade.CategoryFacade;
import pl.lodz.p.it.spjava.e11.sa.ejb.facade.CosmeticFacade;
import pl.lodz.p.it.spjava.e11.sa.ejb.facade.SubstrateFacade;
import pl.lodz.p.it.spjava.e11.sa.ejb.facade.ToxicologyFacade;
import pl.lodz.p.it.spjava.e11.sa.entity.Category;
import pl.lodz.p.it.spjava.e11.sa.entity.Cosmetic;
import pl.lodz.p.it.spjava.e11.sa.entity.Substrate;
import pl.lodz.p.it.spjava.e11.sa.entity.Toxicology;
import pl.lodz.p.it.spjava.e11.sa.exception.AppBaseException;
import pl.lodz.p.it.spjava.e11.sa.utils.converter.CategoryConverter;
import pl.lodz.p.it.spjava.e11.sa.utils.converter.CosmeticConverter;
import pl.lodz.p.it.spjava.e11.sa.utils.converter.SubstrateConverter;
import pl.lodz.p.it.spjava.e11.sa.utils.converter.ToxicologyConverter;


@Stateful
public class ToxicologyManager extends AbstractManager{
    @EJB
    private ToxicologyFacade toxicologyFacade;
    @EJB
    private CosmeticFacade cosmeticFacade;
    
    @EJB
    private SubstrateFacade substrateFacade;
    
    @EJB
    private CosmeticEndpoint cosmeticEndpoint;
    
    private Toxicology editedToxicology;
    
    private CosmeticDTO updatedCosmetic;
    
    public void createToxicology(Toxicology newToxicology, List<SubstrateDTO> appliedSubstratesList, CosmeticDTO connectedCosmetic) throws AppBaseException{
        List<Substrate> appliedSubstratesEntity = SubstrateConverter.createListSubstrateEntityFromDTO(appliedSubstratesList);
        newToxicology.setCosmetic(CosmeticConverter.createCosmeticEntityFromDTO(connectedCosmetic));
        newToxicology.setDescribedBy(appliedSubstratesEntity);
        newToxicology.setToxicologyName("ToxicologyFor"+connectedCosmetic.getName());
        String toxicologyName=newToxicology.getToxicologyName();
        updatedCosmetic = connectedCosmetic;
//        updatedCosmetic.setToxicologyId(newToxicology);
//        updatedCosmetic.setName(connectedCosmetic.getName());
//        updatedCosmetic.setCategoryId(connectedCosmetic.getCategoryId());
//        updatedCosmetic.setComposition(connectedCosmetic.getComposition());
        
//        updatedCosmetic.setToxicologyId(newToxicology);
        System.out.println("----------------------------------------");
        System.out.println("Toxicology manager connectedCosmetic "+updatedCosmetic);
       
        
        
        toxicologyFacade.create(newToxicology);
//        cosmeticFacade.updateToxicology(updatedCosmetic, editedToxicology);
        
        updatedCosmetic.setToxicology(ToxicologyConverter.createToxicologyDTOFromEntity(newToxicology));
        System.out.println("----------------------------------------");
        System.out.println("updated cosmetic w ToxicologyManager "+ updatedCosmetic);
        
        cosmeticEndpoint.updateCosmeticToxicology(updatedCosmetic, toxicologyName);
    }
    
    
    
//    public List<Category> downloadAllCategories() {
//        return categoryFacade.findAll();
//    }
    

//    
//    public Toxicology downloadToxicologyForEdition(Long id) {
//        editedToxicology = toxicologyFacade.find(id);
////        if (null == edytowaneKonto)
////            throw ...;
//        toxicologyFacade.refresh(editedToxicology);
//        return editedToxicology;
//    }
//
////    public void zapiszKontoPoEdycji(String imie, String nazwisko, String email, int wiek)
//    public void saveToxicologyAfterEdition(Toxicology tmp) throws AppBaseException{
//        // Argument tmp jest tylko kontenerem z wartosciami.
//        // Uzywamy go aby nie przekazywac DTO do managera
//        // Do edytowanej encji przenosimy w ten sposob tylko to co moze podlegac zmianom!
//        editedToxicology.setToxycologicalDescription(tmp.getToxycologicalDescription());
//        
//        toxicologyFacade.edit(editedToxicology);
//    }
//    
//    public Toxicology downloadToxicologyForDetails(Long id) {
//        Toxicology downloadedToxicology = toxicologyFacade.find(id);
////        if (null == pobieraneKonto)
////            throw ...;
//        toxicologyFacade.refresh(downloadedToxicology);
//        return downloadedToxicology;
//    }
    
//    public void chooseSubstrate(final SubstrateDTO substrateDTO) throws AppBaseException{
//        Substrate substrate = substrateFacade.findBySubstrateId(substrateDTO.getSubstrateId());
//        
//        Toxicology toxicology = new Toxicology();
//        
////        cosmetic.setCategoryName(category.toString());//toDO poprawić związki relacyjne
//        toxicology.setDescribedBy(substrate.toString());
//     }
    
}
