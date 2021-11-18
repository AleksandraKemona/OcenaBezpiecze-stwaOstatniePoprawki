package pl.lodz.p.it.spjava.e11.sa.dto;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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
    @NotNull(message = "{constraint.notnull}")
    @Pattern(regexp="^[a-zA-Z,0-9- ]*$",message="{constraint.string.incorrectchar}")
    @Size(min = 3, max = 32, message = "{constraint.string.length.notinrange}")
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
