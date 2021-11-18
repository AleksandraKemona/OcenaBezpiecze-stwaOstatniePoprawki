package pl.lodz.p.it.spjava.e11.sa.web.toxicology;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.spjava.e11.sa.dto.SubstrateDTO;
import pl.lodz.p.it.spjava.e11.sa.dto.ToxicologyDTO;
import pl.lodz.p.it.spjava.e11.sa.ejb.endpoint.SubstrateEndpoint;
import pl.lodz.p.it.spjava.e11.sa.ejb.facade.SubstrateFacade;
import pl.lodz.p.it.spjava.e11.sa.exception.AppBaseException;
import pl.lodz.p.it.spjava.e11.sa.utils.converter.SubstrateConverter;
import pl.lodz.p.it.spjava.e11.sa.web.cosmetic.CosmeticDetailsBean;
import pl.lodz.p.it.spjava.e11.sa.web.substrate.SubstrateController;

@SessionScoped
@Named
public class CreateToxicologyBean implements Serializable {

    @Inject
    private ToxicologyController toxicologyController;
    
    @Inject
    private SubstrateController substrateController;

    @EJB
    private SubstrateEndpoint substrateEndpoint;

    @Setter
    private ToxicologyDTO newToxicology;

    @EJB
    private SubstrateFacade substrateFacade;

    @Inject
    private CosmeticDetailsBean cosmeticDetailsBean;

    @Getter
    @Setter
    private String appliedSubstrates;

    @Getter
    @Setter
    private long describedCosmeticId;

    @Getter
    @Setter
    private List<SubstrateDTO> listSubstrateDTO;

    private List<SubstrateDTO> appliedSubstratesList;

    public ToxicologyDTO getNewToxicology() {
        if (null != newToxicology) {

            return newToxicology;
        } else {
            return new ToxicologyDTO();
        }
    }

    public void init(){
        newToxicology = new ToxicologyDTO();
        appliedSubstrates = new String();
        listSubstrateDTO = substrateController.listAllSubstrates();
    }

    public String createToxicology(String appliedSubstratesFromDetails, Long describedCosmeticId){

        appliedSubstrates = appliedSubstratesFromDetails;
        listSubstrateDTO = substrateController.listAllSubstrates();
        appliedSubstratesList = new ArrayList<>();
        String substrate = "";
        String coma = ",";

        for (int i = 0; i < appliedSubstrates.length(); i++) {
            if (appliedSubstrates.contains(coma)) {
                int cuttingPoint = appliedSubstrates.indexOf(coma);
                substrate = appliedSubstrates.substring(0, cuttingPoint);
                SubstrateDTO substrateDTO = substrateController.getSubstrateForCosmetic(substrate);
                appliedSubstrates = appliedSubstrates.substring(cuttingPoint + 2, appliedSubstrates.length());
                appliedSubstratesList.add(substrateDTO);
            }else{
                substrate = appliedSubstrates;
                SubstrateDTO substrateDTO = substrateController.getSubstrateForCosmetic(substrate);
                i=i+substrate.length();
                appliedSubstratesList.add(substrateDTO);
            }
        }
        return toxicologyController.createToxicology(newToxicology, appliedSubstratesList, describedCosmeticId);

    }
}
