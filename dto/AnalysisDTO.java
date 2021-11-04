package pl.lodz.p.it.spjava.e11.sa.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor


public class AnalysisDTO {
    
    @Getter
    @Setter
    private Long analysisId;
    
    @Getter
    @Setter
    @NotNull(message="{constraint.notnull")
    @Size(min=3,max=32,message="{constraint.string.length.notinrange}")
    private String name;
    
    @Getter
    @Setter
    @NotNull(message="{constraint.notnull")
    @Digits(integer = 100, fraction = 2)
    private int minimum;

    @Getter
    @Setter
    @NotNull(message="{constraint.notnull")
    @Digits(integer = 100, fraction = 2)
    private int maximum;
    
    private boolean inRange;

    @Getter
    @Setter
    @NotNull(message="{constraint.notnull")
    @Size(min=3,max=300,message="{constraint.string.length.notinrange}")
    private String standard;
    
    @Getter
    @Setter
    private int resultInAnalysis;
    
    @Getter
    private boolean selectedAnalysis;

    public boolean isSelected() {
        return selectedAnalysis;
    }
     public void setSelected(boolean selectedAnalysis) {
        this.selectedAnalysis = selectedAnalysis;
    }

    public AnalysisDTO(Long analysisId, String name, int minimum, int maximum, String standard) {
        this.analysisId = analysisId;
        this.name = name;
        this.minimum = minimum;
        this.maximum = maximum;
        this.standard = standard;
    }
    
    public void setInRange(boolean inRange) {
        int result = resultInAnalysis;
        int min = minimum;
        int max = maximum;
        if (result<min || result>max) {
            this.inRange=false;    
        }else{
            this.inRange=true;
        }
    }

    public boolean isInRange() {
        int result = resultInAnalysis;
        int min = minimum;
        int max = maximum;
        if (result<min || result>max) {
            inRange=false;    
        }else{
            inRange=true;
        }
        return inRange;
    }
    
    

    @Override
    public String toString() {
        return name;
    }
    
}
