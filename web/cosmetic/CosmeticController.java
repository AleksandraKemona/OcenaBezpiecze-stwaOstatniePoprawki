package pl.lodz.p.it.spjava.e11.sa.web.cosmetic;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import lombok.Getter;
import pl.lodz.p.it.spjava.e11.sa.dto.CosmeticDTO;
import pl.lodz.p.it.spjava.e11.sa.ejb.endpoint.CosmeticEndpoint;
import pl.lodz.p.it.spjava.e11.sa.ejb.facade.CategoryFacade;
import pl.lodz.p.it.spjava.e11.sa.web.utils.ContextUtils;
import java.util.logging.Level;
import javax.inject.Inject;
import pl.lodz.p.it.spjava.e11.sa.exception.AppBaseException;
import pl.lodz.p.it.spjava.e11.sa.exception.CosmeticException;
import pl.lodz.p.it.spjava.e11.sa.web.category.CategoryController;
import pl.lodz.p.it.spjava.e11.sa.web.substrate.SubstrateController;

@Named("cosmeticSession")
@SessionScoped
public class CosmeticController implements Serializable {

    private static final Logger LOG = Logger.getLogger(CosmeticController.class.getName());

    @Inject
    private CategoryController categoryController;

    @EJB
    private CategoryFacade categoryFacade;

    @EJB
    private CosmeticEndpoint cosmeticEndpoint;

    @Getter
    private CosmeticDTO editCosmetic;

    @Getter
    private CosmeticDTO createdCosmetic;

    @Getter
    private CosmeticDTO cosmeticForDeletion;

    @Getter
    private CosmeticDTO cosmeticForDetails;

    @Getter
    private CosmeticDTO choosenCosmetic;

    public String resetSession() {
        ContextUtils.invalidateSession();
        return "cancelAction";
    }

    public String createCosmetic(CosmeticDTO newCosmetic, String categoryName, String userName) {

        try {
            createdCosmetic = newCosmetic;
            cosmeticEndpoint.createCosmetic(createdCosmetic, categoryName, userName);
            createdCosmetic = null;
            return "listCosmetics";
        } catch (CosmeticException ce) {

            if (CosmeticException.KEY_COSMETIC_NAME_EXISTS.equals(ce.getMessage())) {
                ContextUtils.emitInternationalizedMessage("createNewCosmeticForm:name",
                        CosmeticException.KEY_COSMETIC_NAME_EXISTS);
            } else {
                Logger.getLogger(CosmeticController.class.getName()).log(Level.SEVERE,
                        "Zgłoszenie w metodzie akcji createSubstrate wyjątku: ", ce);
            }
            return null;
        } catch (AppBaseException abe) {
            Logger.getLogger(CosmeticController.class.getName()).log(Level.SEVERE,
                    "Zgłoszenie w metodzie akcji createSubstrate wyjątku typu: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            }
            return null;
        }
    }

    public String confirmCosmetic(CosmeticDTO newCosmetic) {
        try {
            cosmeticEndpoint.confirmCosmetic(newCosmetic);
            return "confirmCosmetic";
        } catch (CosmeticException ce) {
            if (CosmeticException.KEY_NAME_DOES_EXIST.equals(ce.getMessage())){
                ContextUtils.emitInternationalizedMessage("createNewCosmeticForm:name",
                        CosmeticException.KEY_NAME_DOES_EXIST);           
            }else{
                Logger.getLogger(CosmeticController.class.getName()).log(Level.SEVERE,
                        "Zgłoszenie w metodzie akcji createSubstrate wyjątku: ", ce);
            }
            return null;
        } catch (AppBaseException abe) {
            Logger.getLogger(CosmeticController.class.getName()).log(Level.SEVERE,
                    "Zgłoszenie w metodzie akcji createSubstrate wyjątku typu: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            }
            return null;
        }
    }

    public List<CosmeticDTO> listAllCosmetics() {
        try {
            return cosmeticEndpoint.listAllCosmetics();
        } catch (AppBaseException abe) {
            Logger.getLogger(CosmeticController.class.getName()).log(Level.SEVERE,
                    "Zgłoszenie w metodzie akcji createSubstrate wyjątku typu: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            }
            return null;
        }
    }

    public CosmeticDTO getCosmeticForDeletion() {
        return cosmeticForDeletion;
    }

    public CosmeticDTO getCosmeticForEdition(CosmeticDTO cosmetic) {
        try {
            editCosmetic = cosmeticEndpoint.getCosmeticForEdition(cosmetic);
            return editCosmetic;
        } catch (CosmeticException ce) {
            if (CosmeticException.KEY_COSMETIC_NOT_READ_FOR_EDITION.equals(ce.getMessage())) {
                ContextUtils.emitInternationalizedMessage("editCosmeticForm:name",
                        CosmeticException.KEY_COSMETIC_NOT_READ_FOR_EDITION);
            } else {
                Logger.getLogger(CosmeticController.class.getName()).log(Level.SEVERE,
                        "Zgłoszenie w metodzie akcji getCosmeticForEdition wyjątku: ", ce);
            }
            return null;
        } catch (AppBaseException abe) {
            Logger.getLogger(CosmeticController.class.getName()).log(Level.SEVERE,
                    "Zgłoszenie w metodzie akcji getCosmeticForEdition wyjątku typu: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            }
            return null;
        }

    }

    public String saveCosmeticAfterEdition(CosmeticDTO cosmeticDTO, String categoryName) {
        try {
            categoryName = editCosmetic.getCategory().getCategoryName();
            cosmeticEndpoint.saveCosmeticAfterEdition(cosmeticDTO, categoryName);
            return "listCosmetics";
        } catch (CosmeticException ce) {
            if (CosmeticException.KEY_COSMETIC_NAME_EXISTS.equals(ce.getMessage())) {
                ContextUtils.emitInternationalizedMessage("editCosmeticForm:name",
                        CosmeticException.KEY_COSMETIC_NAME_EXISTS);
            } else if (CosmeticException.KEY_COSMETIC_OPTIMISTIC_LOCK.equals(ce.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, CosmeticException.KEY_COSMETIC_OPTIMISTIC_LOCK);
            } else {
                Logger.getLogger(CosmeticController.class
                        .getName()).log(Level.SEVERE,
                                "Zgłoszenie w metodzie akcji editSubstrate wyjątku: ", ce);
            }
            return null;
        } catch (AppBaseException abe) {
            Logger.getLogger(SubstrateController.class
                    .getName()).log(Level.SEVERE,
                            "Zgłoszenie w metodzie akcji editSubstrate wyjątku typu: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            }
            return null;
        }
    }

    public String saveResults(CosmeticDTO cosmeticDTO) {
        try {
            cosmeticEndpoint.saveResults(cosmeticDTO);
            return "listCosmetics";
        } catch (CosmeticException ce) {
            if (CosmeticException.KEY_COSMETIC_OPTIMISTIC_LOCK.equals(ce.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, CosmeticException.KEY_COSMETIC_OPTIMISTIC_LOCK);
            } else {
                Logger.getLogger(CosmeticController.class
                        .getName()).log(Level.SEVERE,
                                "Zgłoszenie w metodzie akcji editSubstrate wyjątku: ", ce);
            }
            return null;
        } catch (AppBaseException abe) {
            Logger.getLogger(SubstrateController.class
                    .getName()).log(Level.SEVERE,
                            "Zgłoszenie w metodzie akcji editSubstrate wyjątku typu: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            }
            return null;
        }
    }

    public CosmeticDTO downloadCosmeticForDetails(CosmeticDTO detailedCosmetic) {
        try {
            cosmeticForDetails = cosmeticEndpoint.downloadCosmeticForDetails(detailedCosmetic.getId());
            return cosmeticForDetails;
        } catch (CosmeticException ce) {
            if (CosmeticException.KEY_COSMETIC_NOT_READ_FOR_EDITION.equals(ce.getMessage())) {
                ContextUtils.emitInternationalizedMessage("editCosmeticForm:name",
                        CosmeticException.KEY_COSMETIC_NOT_READ_FOR_EDITION);
            } else {
                Logger.getLogger(CosmeticController.class.getName()).log(Level.SEVERE,
                        "Zgłoszenie w metodzie akcji getCosmeticForEdition wyjątku: ", ce);
            }
            return null;
        } catch (AppBaseException abe) {
            Logger.getLogger(CosmeticController.class.getName()).log(Level.SEVERE,
                    "Zgłoszenie w metodzie akcji getCosmeticForEdition wyjątku typu: ", abe.getClass());
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

    public CosmeticDTO getCosmeticForDeletion(CosmeticDTO cosmeticDTO) {
        try {
            cosmeticForDeletion = cosmeticEndpoint.getCosmeticForDeletion(cosmeticDTO.getId());
            return cosmeticForDeletion;
        } catch (CosmeticException ce) {
            if (CosmeticException.KEY_COSMETIC_NOT_READ_FOR_EDITION.equals(ce.getMessage())) {
                ContextUtils.emitInternationalizedMessage("editCosmeticForm:name",
                        CosmeticException.KEY_COSMETIC_NOT_READ_FOR_EDITION);
            } else {
                Logger.getLogger(CosmeticController.class.getName()).log(Level.SEVERE,
                        "Zgłoszenie w metodzie akcji getCosmeticForEdition wyjątku: ", ce);
            }
            return null;
        } catch (AppBaseException abe) {
            Logger.getLogger(CosmeticController.class.getName()).log(Level.SEVERE,
                    "Zgłoszenie w metodzie akcji getCosmeticForEdition wyjątku typu: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            }
            return null;
        }

    }

    public String confirmDeleteCosmetic(CosmeticDTO cosmeticDTO) {
        try {
            cosmeticEndpoint.confirmDeleteCosmetic(cosmeticDTO);
            return "listCosmetics";
        } catch (CosmeticException ce) {
            if (CosmeticException.KEY_COSMETIC_ALREADY_CHANGED.equals(ce.getMessage())) {
                ContextUtils.emitInternationalizedMessage("deleteCosmeticForm:name", CosmeticException.KEY_COSMETIC_ALREADY_CHANGED);
            } else if (CosmeticException.KEY_NAME_DOES_NOT_EXIST.equals(ce.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, CosmeticException.KEY_NAME_DOES_NOT_EXIST);
            } else if (CosmeticException.KEY_COSMETIC_OPTIMISTIC_LOCK.equals(ce.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, CosmeticException.KEY_COSMETIC_OPTIMISTIC_LOCK);
            } else {
                Logger.getLogger(CosmeticController.class
                        .getName()).log(Level.SEVERE,
                                "Zgłoszenie w metodzie akcji confirmDeleteCosmetic wyjatku: ", ce);
            }
            return null;
        } catch (AppBaseException abe) {
            Logger.getLogger(CosmeticController.class
                    .getName()).log(Level.SEVERE,
                            "Zgłoszenie w metodzie akcji confirmDeleteCosmetic wyjatku typu: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            }
            return null;
        }
    }

    public String chooseCosmetic(CosmeticDTO cosmeticDTO, String userName) {
        try {
            choosenCosmetic = cosmeticEndpoint.chooseCosmetic(cosmeticDTO);
            return setChoosenCosmetic(choosenCosmetic, userName);
        } catch (CosmeticException ce) {
            if (CosmeticException.KEY_COSMETIC_OPTIMISTIC_LOCK.equals(ce.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, CosmeticException.KEY_COSMETIC_OPTIMISTIC_LOCK);
            }else if(CosmeticException.KEY_COSMETIC_CHOOSEN.equals(ce.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, CosmeticException.KEY_COSMETIC_CHOOSEN);
            } else {
                Logger.getLogger(CosmeticController.class
                        .getName()).log(Level.SEVERE,
                                "Zgłoszenie w metodzie akcji confirmDeleteCosmetic wyjatku: ", ce);
            }
            return null;
        } catch (AppBaseException abe) {
            Logger.getLogger(CosmeticController.class
                    .getName()).log(Level.SEVERE,
                            "Zgłoszenie w metodzie akcji confirmDeleteCosmetic wyjatku typu: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            }
            return null;
        }

    }

    public String resignFromCosmetic(CosmeticDTO cosmeticDTO) {
        try {
            choosenCosmetic = cosmeticEndpoint.chooseCosmetic(cosmeticDTO);
            return setCosmeticNotChoosen(choosenCosmetic);
        } catch (CosmeticException ce) {
            if (CosmeticException.KEY_COSMETIC_OPTIMISTIC_LOCK.equals(ce.getMessage())) {
                ContextUtils.emitInternationalizedMessage("deleteCosmeticForm:name", CosmeticException.KEY_COSMETIC_OPTIMISTIC_LOCK);
            } else {
                Logger.getLogger(CosmeticController.class
                        .getName()).log(Level.SEVERE,
                                "Zgłoszenie w metodzie akcji confirmDeleteCosmetic wyjatku: ", ce);
            }
            return null;
        } catch (AppBaseException abe) {
            Logger.getLogger(CosmeticController.class
                    .getName()).log(Level.SEVERE,
                            "Zgłoszenie w metodzie akcji confirmDeleteCosmetic wyjatku typu: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            }
            return null;
        }

    }

    public String setChoosenCosmetic(CosmeticDTO cosmeticDTO, String userName) {
        try {
            cosmeticEndpoint.setChoosenCosmetic(cosmeticDTO, userName);
            return "listCosmetics";
        } catch (CosmeticException ce) {
            if (CosmeticException.KEY_COSMETIC_NAME_EXISTS.equals(ce.getMessage())) {
                ContextUtils.emitInternationalizedMessage("listCosmetics:name",
                        CosmeticException.KEY_COSMETIC_NAME_EXISTS);
            } else if(CosmeticException.KEY_COSMETIC_CHOOSEN.equals(ce.getMessage())) {
                ContextUtils.emitInternationalizedMessage("listCosmeticsForm:assessedBy", CosmeticException.KEY_COSMETIC_CHOOSEN);
            }else if (CosmeticException.KEY_COSMETIC_OPTIMISTIC_LOCK.equals(ce.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, CosmeticException.KEY_COSMETIC_OPTIMISTIC_LOCK);
            } else {
                Logger.getLogger(CosmeticController.class
                        .getName()).log(Level.SEVERE,
                                "Zgłoszenie w metodzie akcji editSubstrate wyjątku: ", ce);
            }
            return null;
        } catch (AppBaseException abe) {
            Logger.getLogger(SubstrateController.class
                    .getName()).log(Level.SEVERE,
                            "Zgłoszenie w metodzie akcji editSubstrate wyjątku typu: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            }
            return null;
        }
    }

    public String setCosmeticNotChoosen(CosmeticDTO cosmeticDTO) {
        try {
            cosmeticEndpoint.setCosmeticNotChoosen(cosmeticDTO);
            return "listCosmetics";
        } catch (CosmeticException ce) {
            if (CosmeticException.KEY_COSMETIC_NAME_EXISTS.equals(ce.getMessage())) {
                ContextUtils.emitInternationalizedMessage("listCosmetics:name",
                        CosmeticException.KEY_COSMETIC_NAME_EXISTS);
            } else if (CosmeticException.KEY_COSMETIC_OPTIMISTIC_LOCK.equals(ce.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, CosmeticException.KEY_COSMETIC_OPTIMISTIC_LOCK);
            } else {
                Logger.getLogger(CosmeticController.class
                        .getName()).log(Level.SEVERE,
                                "Zgłoszenie w metodzie akcji editSubstrate wyjątku: ", ce);
            }
            return null;
        } catch (AppBaseException abe) {
            Logger.getLogger(SubstrateController.class
                    .getName()).log(Level.SEVERE,
                            "Zgłoszenie w metodzie akcji editSubstrate wyjątku typu: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            }
            return null;
        }
    }

}
