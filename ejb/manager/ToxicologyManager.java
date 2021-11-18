package pl.lodz.p.it.spjava.e11.sa.ejb.manager;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import pl.lodz.p.it.spjava.e11.sa.dto.CosmeticDTO;
import pl.lodz.p.it.spjava.e11.sa.dto.SubstrateDTO;
import pl.lodz.p.it.spjava.e11.sa.ejb.endpoint.CosmeticEndpoint;
import pl.lodz.p.it.spjava.e11.sa.ejb.facade.CosmeticFacade;
import pl.lodz.p.it.spjava.e11.sa.ejb.facade.SubstrateFacade;
import pl.lodz.p.it.spjava.e11.sa.ejb.facade.ToxicologyFacade;
import pl.lodz.p.it.spjava.e11.sa.entity.Substrate;
import pl.lodz.p.it.spjava.e11.sa.entity.Toxicology;
import pl.lodz.p.it.spjava.e11.sa.exception.AppBaseException;
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
        toxicologyFacade.create(newToxicology);
        updatedCosmetic.setToxicology(ToxicologyConverter.createToxicologyDTOFromEntity(newToxicology));
        cosmeticEndpoint.updateCosmeticToxicology(updatedCosmetic, toxicologyName);
    }
}
