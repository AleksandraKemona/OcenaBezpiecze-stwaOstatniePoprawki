package pl.lodz.p.it.spjava.e11.sa.web.analysis;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import lombok.Getter;
import pl.lodz.p.it.spjava.e11.sa.dto.AnalysisDTO;
import pl.lodz.p.it.spjava.e11.sa.web.utils.ContextUtils;

import pl.lodz.p.it.spjava.e11.sa.dto.CategoryDTO;
import pl.lodz.p.it.spjava.e11.sa.ejb.endpoint.AnalysisEndpoint;
import pl.lodz.p.it.spjava.e11.sa.exception.AnalysisException;
import pl.lodz.p.it.spjava.e11.sa.exception.AppBaseException;

@Named("analysisSession")
@SessionScoped
public class AnalysisController implements Serializable {

    private static final Logger LOG = Logger.getLogger(AnalysisController.class.getName());

//    @EJB
//    private AnalysisFacade analysisFacade;
    @EJB
    private AnalysisEndpoint analysisEndpoint;

    @Getter
    private AnalysisDTO editAnalysis;

    @Getter
    private AnalysisDTO appliedAnalysis;

    @Getter
    private AnalysisDTO createdAnalysis;

    public String resetSession() {
        ContextUtils.invalidateSession();
        return "cancelAction";
    }

    public String createAnalysis(AnalysisDTO newAnalysis) {

        try {
            createdAnalysis = newAnalysis;
            analysisEndpoint.createAnalysis(createdAnalysis);
            createdAnalysis = null;
            return "listAnalysis";
        } catch (AnalysisException ae) {
            System.out.println("-----------------Controller wejście do Catch analysis exception-------");
            if (AnalysisException.KEY_ANALYSIS_NAME_EXISTS.equals(ae.getMessage())) {
                System.out.println("-------Controller analysis name exists---------");
                ContextUtils.emitInternationalizedMessage("createNewAnalysisForm:name",
                        AnalysisException.KEY_ANALYSIS_NAME_EXISTS);
            } else {
                Logger.getLogger(AnalysisController.class.getName()).log(Level.SEVERE,
                        "Zgłoszenie w metodzie akcji createAnalysis wyjątku: ", ae);
            }
            return null;
        } catch (AppBaseException abe) {
            Logger.getLogger(AnalysisController.class.getName()).log(Level.SEVERE,
                    "Zgłoszenie w metodzie akcji createAnalysis wyjątku typu: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            }
            return null;
        }
    }

    public List<AnalysisDTO> listAllAnalysis() {
        try {
            return analysisEndpoint.listAllAnalysis();
        } catch (AppBaseException abe) {
            Logger.getLogger(AnalysisController.class.getName()).log(Level.SEVERE,
                    "Zgłoszenie w metodzie akcji listAllAnalysis wyjątku typu: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            }
            return null;
        }
    }

    public AnalysisDTO getAnalysisForEdition(AnalysisDTO analysisDTO) {
        try {
            editAnalysis = analysisEndpoint.getAnalysisForEdition(analysisDTO);
            return editAnalysis;
        } catch (AnalysisException se) {
            if (AnalysisException.KEY_ANALYSIS_NOT_READ_FOR_EDITION.equals(se.getMessage())) {
                ContextUtils.emitInternationalizedMessage("editAnalysisForm:name",
                        AnalysisException.KEY_ANALYSIS_NOT_READ_FOR_EDITION);
            } else {
                Logger.getLogger(AnalysisController.class.getName()).log(Level.SEVERE,
                        "Zgłoszenie w metodzie akcji getAnalysisForEdition wyjątku: ", se);
            }
            return null;
        } catch (AppBaseException abe) {
            Logger.getLogger(AnalysisController.class.getName()).log(Level.SEVERE,
                    "Zgłoszenie w metodzie akcji getAnalysisForEdition wyjątku typu: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            }
            return null;
        }

    }

    public String saveAnalysisAfterEdition(AnalysisDTO analysisDTO) {
        try {
            analysisEndpoint.saveAnalysisAfterEdition(analysisDTO);
            return "listAnalysis";
        } catch (AnalysisException ae) {
            if (AnalysisException.KEY_ANALYSIS_NAME_EXISTS.equals(ae.getMessage())) {
                System.out.println("--------- Controller wejście do if name exists------------");
                ContextUtils.emitInternationalizedMessage("editAnalysisForm:name",
                        AnalysisException.KEY_ANALYSIS_NAME_EXISTS);
            } else if (AnalysisException.KEY_ANALYSIS_OPTIMISTIC_LOCK.equals(ae.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, AnalysisException.KEY_ANALYSIS_OPTIMISTIC_LOCK);
            } else {
                Logger.getLogger(AnalysisController.class.getName()).log(Level.SEVERE,
                        "Zgłoszenie w metodzie akcji editAnalysis wyjątku: ", ae);
            }
            return null;
        } catch (AppBaseException abe) {
            Logger.getLogger(AnalysisController.class.getName()).log(Level.SEVERE,
                    "Zgłoszenie w metodzie akcji editAnalysis wyjątku typu: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            }
            return null;
        }
    }

    public void chooseAnalysis(CategoryDTO categoryDTO) {
        analysisEndpoint.chooseAnalsysis(categoryDTO);
    }

    @PostConstruct
    private void init() {
        LOG.severe("Session started: " + ContextUtils.getSessionID());
    }

}
