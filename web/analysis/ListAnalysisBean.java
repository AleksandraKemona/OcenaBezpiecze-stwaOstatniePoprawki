package pl.lodz.p.it.spjava.e11.sa.web.analysis;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import pl.lodz.p.it.spjava.e11.sa.dto.AnalysisDTO;
import pl.lodz.p.it.spjava.e11.sa.exception.AppBaseException;

@ViewScoped
@Named
public class ListAnalysisBean implements Serializable {

    @Inject
    private AnalysisController analysisController;

    @Inject
    private EditAnalysisBean editAnalysisBean;

    @Getter
    private List<AnalysisDTO> analysisList;

    @PostConstruct
    public void init() {
        analysisList = analysisController.listAllAnalysis();
    }

    public String edit(AnalysisDTO analysisDTO) {
        editAnalysisBean.setEditedAnalysis(analysisDTO);
        return "editAnalysis";
    }

}
