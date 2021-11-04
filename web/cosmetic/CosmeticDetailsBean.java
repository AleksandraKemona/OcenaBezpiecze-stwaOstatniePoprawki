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

    public void setShowedCosmeticChoice(CosmeticDTO showedCosmetic){
        this.showedCosmeticChoice = showedCosmetic;
        init();
    }

    private String init(){
        showedCosmetic = cosmeticController.downloadCosmeticForDetails(showedCosmeticChoice);
        newToxicology = new ToxicologyDTO();
        appliedSubstrates = new String();
        appliedSubstrates = showedCosmetic.getComposition();
        describedCosmeticId = showedCosmetic.getId();
        return appliedSubstrates;
    }

    public CosmeticDTO getShowedCosmetic() {
        if (null != showedCosmetic) {

            return showedCosmetic;
        } else {
            return new CosmeticDTO(); // Dla unikniecia błędu formularza. Dane nie zostana zachowane.
        }
    }

    public CategoryDTO getAppliedCategory() {
        if (null != showedCosmetic.getCategory()) {
            appliedCategory = categoryController.getCategoryForCosmetic(showedCosmetic.getCategory());
            demandedAnalysis = appliedCategory.getDemandsAnalysis();
            List<AnalysisDTO> demandedAnalysiswithResults=new ArrayList<>();
            Map<Long, Integer> resultsMap = showedCosmetic.getResults();
            if(resultsMap==null){
                resultsMap=new HashMap<>();
                for (AnalysisDTO analysis : demandedAnalysis) {
                    Long analysisId=analysis.getAnalysisId();
                    resultsMap.put(analysisId, 0);
                }
            }
            for (AnalysisDTO analysis : demandedAnalysis) {
                Long analysisId = analysis.getAnalysisId();
                Integer singleResult = resultsMap.get(analysisId);
                if (singleResult==null) {
                    analysis.setResultInAnalysis(0);
                }else{
                    analysis.setResultInAnalysis(singleResult); 
                }
                analysis.isInRange();
                
                demandedAnalysiswithResults.add(analysis);
            }
            appliedCategory.setDemandsAnalysis(demandedAnalysiswithResults);
            return appliedCategory;
        } else {
            return new CategoryDTO(); // Dla unikniecia błędu formularza. Dane nie zostana zachowane.
        }
    }

    public void setDemandedAnalysis(CategoryDTO categoryDTO) {
        System.out.println("------set demanded analysis---------");
        System.out.println("category DTO "+ categoryDTO);
        List<AnalysisDTO> foundAnalysis = categoryController.findDemandedAnalysis(categoryDTO);
        System.out.println("found analysis "+foundAnalysis);
        this.demandedAnalysis = foundAnalysis;
    }
    
    public String editResult (AnalysisDTO analysis){
//        showedCosmetic=cosmetic;
        analysisForResults=analysis;
        
        return"editResults";
    }
    
    public String saveResult(){
        int newResult = analysisForResults.getResultInAnalysis();
        CosmeticDTO cosmeticWithResults= showedCosmetic;
        System.out.println("---------------wyniki cosmetic Details---------");
        System.out.println("new Result "+ newResult);
        System.out.println("showed Cosmetic "+showedCosmetic);
        Map <Long, Integer> resultsMap = generateMap(newResult);
        System.out.println("mapa poza ifem "+ resultsMap);
        cosmeticWithResults.setResults(resultsMap);
        cosmeticController.saveResults(cosmeticWithResults);
return "cosmeticDetails";
    }
    
    public String refreshDetails(CosmeticDTO cosmeticDTO){
        return "cosmeticDetails";
    }
    
    public Map <Long, Integer> generateMap(int newResult){
        CosmeticDTO cosmetic = showedCosmetic;
        System.out.println("metoda generate map showed cosmetic "+ showedCosmetic);
        Map <Long, Integer> map = new HashMap<>();
        Long analysisId=analysisForResults.getAnalysisId();
        if (cosmetic.getResults()==null) {
            System.out.println("Mapa 1 new "+ map);
            map.put(analysisId, newResult);
               System.out.println("Mapa 2 "+ map);
               System.out.println("mapa 2 value "+ map.get(analysisForResults));
        }else{
            map =cosmetic.getResults();
            System.out.println("Mapa 1 "+ map);
            map.put(analysisId, newResult);
               System.out.println("Mapa 2 "+ map);
               System.out.println("mapa 2 value "+ map.get(analysisForResults));
        }
     return map;
    }
    
    


    public String abort() {

        return "listCosmetics";
    }

    public String refresh(){
        init();
        return "";
    }

}
