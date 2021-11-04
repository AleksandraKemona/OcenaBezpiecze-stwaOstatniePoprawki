package pl.lodz.p.it.spjava.e11.sa.web.analysis;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.spjava.e11.sa.dto.AnalysisDTO;

@SessionScoped
@Named
public class EditAnalysisBean implements Serializable {
    
    @Inject
    private AnalysisController analysisController;


    private AnalysisDTO editedAnalysis;

    public AnalysisDTO getEditedAnalysis() {
        if (null != editedAnalysis) {
            return editedAnalysis;
        } else {
            return new AnalysisDTO();
        }
    }

    public void setEditedAnalysis(AnalysisDTO editedAnalysis) {
        this.editedAnalysis = analysisController.getAnalysisForEdition(editedAnalysis);
    }
    
    public String saveAnalysis(){
        if (null == editedAnalysis) {
            return "main";
        }
        return analysisController.saveAnalysisAfterEdition(editedAnalysis);
    }
 
    public String abort() {
        return "listAnalysis";
    }
    
}
