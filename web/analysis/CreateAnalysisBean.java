package pl.lodz.p.it.spjava.e11.sa.web.analysis;

import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.spjava.e11.sa.dto.AnalysisDTO;
import pl.lodz.p.it.spjava.e11.sa.dto.CategoryDTO;

@SessionScoped
@Named
public class CreateAnalysisBean implements Serializable {

    @Inject
    private AnalysisController analysisController;

    @Setter
    private AnalysisDTO newAnalysis;

   

    @Getter
    @Setter
    private List<CategoryDTO> listCategoryDTO;

    public AnalysisDTO getNewAnalysis() {
        if (null != newAnalysis) {

            return newAnalysis;
        } else {
            return new AnalysisDTO();
        }
    }


    public String begin() {
        newAnalysis = new AnalysisDTO();
        return "createAnalysis";
    }

    public AnalysisDTO getAnalysisDTO() {
        return newAnalysis;
    }

    public String createAnalysis() {
        if (null == newAnalysis) {
            return "listAnalysis";
        }
       return analysisController.createAnalysis(newAnalysis);

    }

    public String abort() {
        return "listAnalysis";
    }

  
}
