package pl.lodz.p.it.spjava.e11.sa.dto;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.it.spjava.e11.sa.dto.AnalysisDTO;

@NoArgsConstructor

public class CategoryDTO {

    @Getter
    @Setter
    private Long categoryId;

    @Getter
    @Setter
    private String categoryName;
    
    
    @Setter
    private String demandsAnalysisString;


    @Setter
    private List<AnalysisDTO> demandsAnalysis;
    
    @Setter
    private List<AnalysisDTO> newDemandsAnalysis;

    public CategoryDTO(Long categoryId, String categoryName, List<AnalysisDTO> demandsAnalysis) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.demandsAnalysis = demandsAnalysis;

    }

    public CategoryDTO(Long categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;

    }

    public List<AnalysisDTO> getDemandsAnalysis() {
        return demandsAnalysis;
    }

    public String getDemandsAnalysisString() {
        String demandsAnalysisToCut = demandsAnalysis.toString();
        demandsAnalysisString = demandsAnalysisToCut.substring(1, demandsAnalysisToCut.length() - 1);
        return demandsAnalysisString;
    }

    @Override
    public String toString() {
        return categoryName;
    }

}
