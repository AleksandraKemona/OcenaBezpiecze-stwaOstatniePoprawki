package pl.lodz.p.it.spjava.e11.sa.web.substrate;

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
import pl.lodz.p.it.spjava.e11.sa.ejb.endpoint.SubstrateEndpoint;
import pl.lodz.p.it.spjava.e11.sa.exception.AppBaseException;
import pl.lodz.p.it.spjava.e11.sa.exception.SubstrateException;

@Named("substrateSession")
@SessionScoped
public class SubstrateController implements Serializable {

    private static final Logger LOG = Logger.getLogger(SubstrateController.class.getName());

    @EJB
    private SubstrateEndpoint substrateEndpoint;

    @Getter
    private SubstrateDTO editSubstrate;

    @Getter
    private SubstrateDTO createdSubstrate;

    @Getter
    private SubstrateDTO SubstrateAnalysis;

    public String createSubstrate(SubstrateDTO newSubstrateDTO) {
        try {
            createdSubstrate = newSubstrateDTO;
            substrateEndpoint.createSubstrate(createdSubstrate);
            createdSubstrate = null;
            return "listSubstrates";
        } catch (SubstrateException se) {
            if (SubstrateException.KEY_SUBSTRATE_NAME_EXISTS.equals(se.getMessage())) {
                ContextUtils.emitInternationalizedMessage("createNewSubstrateForm:name",
                        SubstrateException.KEY_SUBSTRATE_NAME_EXISTS);
            } else {
                Logger.getLogger(SubstrateController.class.getName()).log(Level.SEVERE,
                        "Zgłoszenie w metodzie akcji createSubstrate wyjątku: ", se);
            }
            return null;
        } catch (AppBaseException abe) {
            Logger.getLogger(SubstrateController.class.getName()).log(Level.SEVERE,
                    "Zgłoszenie w metodzie akcji createSubstrate wyjątku typu: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            }
            return null;
        }
    }

    public List<SubstrateDTO> listAllSubstrates() {
        try {
            return substrateEndpoint.listAllSubstrates();
        } catch (AppBaseException abe) {
            Logger.getLogger(SubstrateController.class.getName()).log(Level.SEVERE,
                    "Zgłoszenie w metodzie akcji listAllSubstrates wyjątku typu: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            }
            return null;
        }

    }

    public SubstrateDTO getSubstrateForEdition(SubstrateDTO substrateDTO) {
        try {
            editSubstrate = substrateEndpoint.getSubstrateForEdition(substrateDTO);
            return editSubstrate;
        } catch (SubstrateException se) {
            if (SubstrateException.KEY_SUBSTRATE_NOT_READ_FOR_EDITION.equals(se.getMessage())) {
                ContextUtils.emitInternationalizedMessage("editSubstrateForm:name",
                        SubstrateException.KEY_SUBSTRATE_NOT_READ_FOR_EDITION);
            } else {
                Logger.getLogger(SubstrateController.class.getName()).log(Level.SEVERE,
                        "Zgłoszenie w metodzie akcji getSubstrateForEdition wyjątku: ", se);
            }
            return null;
        } catch (AppBaseException abe) {
            Logger.getLogger(SubstrateController.class.getName()).log(Level.SEVERE,
                    "Zgłoszenie w metodzie akcji getSubstrateForEdition wyjątku typu: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            }
            return null;
        }
    }

    public SubstrateDTO getSubstrateForCosmetic(String substrateName) {
        try {
            SubstrateAnalysis = substrateEndpoint.getSubstrateForCosmetic(substrateName);
            return SubstrateAnalysis;
        } catch (SubstrateException se) {
            if (SubstrateException.KEY_SUBSTRATE_NOT_READ_FOR_COSMETIC.equals(se.getMessage())) {
                ContextUtils.emitInternationalizedMessage("cosmeticDetailsForm:SubstrtateNotReadForCosmetic",
                        SubstrateException.KEY_SUBSTRATE_NOT_READ_FOR_COSMETIC);
            } else {
                Logger.getLogger(SubstrateController.class.getName()).log(Level.SEVERE,
                        "Zgłoszenie w metodzie akcji editSubstrate wyjątku: ", se);
            }
            return null;
        } catch (AppBaseException abe) {
            Logger.getLogger(SubstrateController.class.getName()).log(Level.SEVERE,
                    "Zgłoszenie w metodzie akcji createSubstrate wyjątku typu: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            }
            return null;
        }
    }

    public String saveSubstrateAfterEdition(SubstrateDTO substrateDTO) {
        try {
            substrateEndpoint.saveSubstrateAfterEdition(substrateDTO);
            return "listSubstrates";
        } catch (SubstrateException se) {
            if (SubstrateException.KEY_SUBSTRATE_NAME_EXISTS.equals(se.getMessage())) {
                ContextUtils.emitInternationalizedMessage("editSubstrateForm:name",
                        SubstrateException.KEY_SUBSTRATE_NAME_EXISTS);
            } else if (SubstrateException.KEY_SUBSTRATE_OPTIMISTIC_LOCK.equals(se.getMessage())) {
                ContextUtils.emitInternationalizedMessage("editSubstrateForm:name", SubstrateException.KEY_SUBSTRATE_OPTIMISTIC_LOCK);
            } else {
                Logger.getLogger(SubstrateController.class.getName()).log(Level.SEVERE,
                        "Zgłoszenie w metodzie akcji editSubstrate wyjątku: ", se);
            }
            return null;
        } catch (AppBaseException abe) {
            Logger.getLogger(SubstrateController.class.getName()).log(Level.SEVERE,
                    "Zgłoszenie w metodzie akcji editSubstrate wyjątku typu: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            }
            return null;
        }
    }

    @PostConstruct
    private void init() {
        LOG.severe("Session started: " + ContextUtils.getSessionID());
    }

}
