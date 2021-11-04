package pl.lodz.p.it.spjava.e11.sa.web.toxicology;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import lombok.Getter;
import pl.lodz.p.it.spjava.e11.sa.web.utils.ContextUtils;
import pl.lodz.p.it.spjava.e11.sa.dto.SubstrateDTO;
import pl.lodz.p.it.spjava.e11.sa.dto.ToxicologyDTO;
import pl.lodz.p.it.spjava.e11.sa.ejb.endpoint.ToxicologyEndpoint;
import pl.lodz.p.it.spjava.e11.sa.exception.AppBaseException;
import pl.lodz.p.it.spjava.e11.sa.exception.SubstrateException;

@Named("toxicologySession")
@SessionScoped
public class ToxicologyController implements Serializable {

    private static final Logger LOG = Logger.getLogger(ToxicologyController.class.getName());

    @EJB
    private ToxicologyEndpoint toxicologyEndpoint;

    @Getter
    private ToxicologyDTO toxicologyDTO;

    @Getter
    private ToxicologyDTO createdToxicology;

    @Getter
    private List<SubstrateDTO> appliedSubstrates;
    
    @Getter
    private Long connectedCosmeticId;

    @Getter
    private SubstrateDTO SubstrateAnalysis;

    public String resetSession() {
        ContextUtils.invalidateSession();
        return "cancelAction";
    }

    public String createToxicology(ToxicologyDTO newToxicologyDTO, List<SubstrateDTO> appliedSubstratesList, Long describedCosmeticId) {

        try {
            createdToxicology = newToxicologyDTO;
            appliedSubstrates = appliedSubstratesList;
            connectedCosmeticId = describedCosmeticId;

            toxicologyEndpoint.createToxicology(createdToxicology, appliedSubstrates, connectedCosmeticId);
            
            appliedSubstrates = null;
            createdToxicology = null;
            connectedCosmeticId = null;

            return "listCosmetics";
        } catch (SubstrateException se) {
            System.out.println("SE Message" + se.getMessage());
            if (SubstrateException.KEY_SUBSTRATE_NAME_EXISTS.equals(se.getMessage())) {
                ContextUtils.emitInternationalizedMessage("createNewSubstrateForm:name",
                        SubstrateException.KEY_SUBSTRATE_NAME_EXISTS);

            } else {
                Logger.getLogger(ToxicologyController.class.getName()).log(Level.SEVERE,
                        "Zgłoszenie w metodzie akcji createSubstrate wyjątku: ", se);
            }
            return null;
        } catch (AppBaseException abe) {
            Logger.getLogger(ToxicologyController.class.getName()).log(Level.SEVERE,
                    "Zgłoszenie w metodzie akcji createSubstrate wyjątku typu: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            }
            return null;
        }
    }

//    public SubstrateDTO getSubstrateForEdition(SubstrateDTO substrateDTO) throws AppBaseException{
//        try {
//        editSubstrate = substrateEndpoint.getSubstrateForEdition(substrateDTO);
//        return editSubstrate;
//        } catch (SubstrateException se) {
//            if (SubstrateException.KEY_SUBSTRATE_NOT_READ_FOR_EDITION.equals(se.getMessage())) {
//                ContextUtils.emitInternationalizedMessage("editSubstrateForm:notReadForEdition",
//                        SubstrateException.KEY_SUBSTRATE_NOT_READ_FOR_EDITION);
//            } else {
//                Logger.getLogger(ToxicologyController.class.getName()).log(Level.SEVERE,
//                        "Zgłoszenie w metodzie akcji editSubstrate wyjątku: ", se);
//            }
//            return null;
//        } catch (AppBaseException abe) {
//            Logger.getLogger(ToxicologyController.class.getName()).log(Level.SEVERE,
//                    "Zgłoszenie w metodzie akcji createSubstrate wyjątku typu: ", abe.getClass());
//            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
//                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
//            }
//            return null;
//        }
//
//    }
    @PostConstruct
    private void init() {
        LOG.severe("Session started: " + ContextUtils.getSessionID());
    }

}
