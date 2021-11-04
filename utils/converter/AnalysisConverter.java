package pl.lodz.p.it.spjava.e11.sa.utils.converter;

import java.util.ArrayList;
import java.util.List;
import pl.lodz.p.it.spjava.e11.sa.dto.AnalysisDTO;
import pl.lodz.p.it.spjava.e11.sa.entity.Analysis;

public class AnalysisConverter {

    public static Analysis createAnalysisEntityFromDTO(AnalysisDTO analysisDTO) {
        return new Analysis(analysisDTO.getAnalysisId(), analysisDTO.getName(), analysisDTO.getMinimum(),
                analysisDTO.getMaximum(), analysisDTO.getStandard());
    }

    public static AnalysisDTO createAnalysisDTOFromEntity(Analysis analysis) {
        return new AnalysisDTO(analysis.getAnalysisId(), analysis.getName(), analysis.getMinimum(),
                analysis.getMaximum(), analysis.getStandard());
    }

    public static List<AnalysisDTO> createListAnalysisDTOFromEntity(List<Analysis> listAnalysis) {
        List<AnalysisDTO> listAnalysisDTO = new ArrayList<>();

        for (Analysis analysis : listAnalysis) {
            AnalysisDTO analysisDTO = new AnalysisDTO(analysis.getAnalysisId(), analysis.getName(), analysis.getMinimum(),
                    analysis.getMaximum(), analysis.getStandard());
            listAnalysisDTO.add(analysisDTO);
        }

        return listAnalysisDTO;
    }
    
    public static List<Analysis> createListAnalysisEntityFromDTO(List<AnalysisDTO> listAnalysisDTO) {
        List<Analysis> listAnalysis = new ArrayList<>();

        for (AnalysisDTO analysisDTO : listAnalysisDTO) {
            Analysis analysis = new Analysis(analysisDTO.getAnalysisId(), analysisDTO.getName(), analysisDTO.getMinimum(),
                    analysisDTO.getMaximum(), analysisDTO.getStandard());
            listAnalysis.add(analysis);
        }

        return listAnalysis;
    }

}
