package pl.lodz.p.it.spjava.e11.sa.web.cosmetic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.spjava.e11.sa.dto.AnalysisDTO;
import pl.lodz.p.it.spjava.e11.sa.dto.CategoryDTO;
import pl.lodz.p.it.spjava.e11.sa.dto.CosmeticDTO;
import pl.lodz.p.it.spjava.e11.sa.dto.ToxicologyDTO;
import pl.lodz.p.it.spjava.e11.sa.exception.AppBaseException;
import pl.lodz.p.it.spjava.e11.sa.web.analysis.AnalysisController;
import pl.lodz.p.it.spjava.e11.sa.web.category.CategoryController;

@SessionScoped
@Named
public class CosmeticDetailsBean implements Serializable {

    @Inject
    private CosmeticController cosmeticController;

    @Inject
    private CategoryController categoryController;

    @Inject
    private ListCosmeticsBean listCosmeticsBean;

    @Inject
    private AnalysisController analysisController;

    @Getter
    private List<AnalysisDTO> demandedAnalysis;

    private CosmeticDTO showedCosmetic;

    private CategoryDTO appliedCategory;

    @Getter
    @Setter
    private AnalysisDTO analysisForResults;

    private CosmeticDTO showedCosmeticChoice;

    @Getter
    @Setter
    private ToxicologyDTO newToxicology;

    @Getter
    @Setter
    private String appliedSubstrates;

    @Getter
    @Setter
    private Long describedCosmeticId;

    public String setShowedCosmeticChoice(CosmeticDTO showedCosmetic) {
        this.showedCosmeticChoice = showedCosmetic;
        return init();
    }

    private String init() {
        showedCosmetic = cosmeticController.downloadCosmeticForDetails(showedCosmeticChoice);
        newToxicology = new ToxicologyDTO();
        appliedSubstrates = new String();
        appliedSubstrates = showedCosmetic.getComposition();
        describedCosmeticId = showedCosmetic.getId();
        return "cosmeticDetails";
    }

    public CosmeticDTO getShowedCosmetic() {
        if (null != showedCosmetic) {

            return showedCosmetic;
        } else {
            return new CosmeticDTO();
        }
    }

    public CategoryDTO getAppliedCategory() {
        if (null != showedCosmetic.getCategory()) {
            appliedCategory = categoryController.getCategoryForCosmetic(showedCosmetic.getCategory());
            demandedAnalysis = appliedCategory.getDemandsAnalysis();
            List<AnalysisDTO> demandedAnalysiswithResults = new ArrayList<>();
            Map<Long, Integer> resultsMap = showedCosmetic.getResults();
            if (resultsMap == null) {
                resultsMap = new HashMap<>();
                for (AnalysisDTO analysis : demandedAnalysis) {
                    Long analysisId = analysis.getAnalysisId();
                    resultsMap.put(analysisId, 0);
                }
            }
            for (AnalysisDTO analysis : demandedAnalysis) {
                Long analysisId = analysis.getAnalysisId();
                Integer singleResult = resultsMap.get(analysisId);
                if (singleResult == null) {
                    analysis.setResultInAnalysis(0);
                } else {
                    analysis.setResultInAnalysis(singleResult);
                }
                analysis.isInRange();

                demandedAnalysiswithResults.add(analysis);
            }
            appliedCategory.setDemandsAnalysis(demandedAnalysiswithResults);
            return appliedCategory;
        } else {
            return new CategoryDTO();
        }
    }

    public void setDemandedAnalysis(CategoryDTO categoryDTO) {
        List<AnalysisDTO> foundAnalysis = categoryController.findDemandedAnalysis(categoryDTO);
        this.demandedAnalysis = foundAnalysis;
    }

    public String editResult(AnalysisDTO analysis) {
        analysisForResults = analysis;

        return "editResults";
    }

    public String saveResult() {
        int newResult = analysisForResults.getResultInAnalysis();
        CosmeticDTO cosmeticWithResults = showedCosmetic;
        Map<Long, Integer> resultsMap = generateMap(newResult);
        cosmeticWithResults.setResults(resultsMap);
        cosmeticController.saveResults(cosmeticWithResults);
        return "cosmeticDetails";
    }

    public String refreshDetails(CosmeticDTO cosmeticDTO) {
        return "cosmeticDetails";
    }

    public Map<Long, Integer> generateMap(int newResult) {
        CosmeticDTO cosmetic = showedCosmetic;
        Map<Long, Integer> map = new HashMap<>();
        Long analysisId = analysisForResults.getAnalysisId();
        if (cosmetic.getResults() == null) {
            map.put(analysisId, newResult);
        } else {
            map = cosmetic.getResults();
            map.put(analysisId, newResult);
        }
        return map;
    }

    public String abort() {

        return "listCosmetics";
    }

    public String refresh() {
        init();
        return "";
    }

}
