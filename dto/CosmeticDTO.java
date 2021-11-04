package pl.lodz.p.it.spjava.e11.sa.dto;

import java.math.BigInteger;
import java.util.Map;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.lodz.p.it.spjava.e11.sa.entity.Toxicology;

@NoArgsConstructor
@ToString
public class CosmeticDTO {

    @Getter
    @Setter
    private Long id;

    @NotNull(message = "{constraint.notnull}")
    @Digits(integer = 12, fraction = 0)
    @Getter
    @Setter
    private BigInteger orderNb;

    @Getter
    @Setter
    @NotNull(message = "{constraint.notnull}")
    @Pattern(regexp="^[a-zA-Z,0-9- ]*$",message="{constraint.string.incorrectchar}")
    @Size(min = 3, max = 32, message = "{constraint.string.length.notinrange}")
    private String name;

    @Getter
    @Setter
    @NotNull(message = "{constraint.notnull}")
    private CategoryDTO category;

    @Getter
    @Setter
    @NotNull(message = "{constraint.notnull}")
    @Pattern(regexp="^[a-zA-Z, ]*$",message="{constraint.string.incorrectchar}")
    private String composition;
    
    @Getter
    @Setter
    private Map<Long, Integer> results;

    @Getter
    @Setter
    private SalesDTO createdBy;

    @Getter
    @Setter
    private AssessorDTO assessedBy;

    @Getter
    @Setter
    private LabDTO testedBy;

    @Getter
    @Setter
    private ToxicologyDTO toxicology;

    private boolean appliedToxicology;

    private boolean assessed;

    public void setAssessed(boolean isAssessed) {
        Long assessorid = assessedBy.getAssessorId();
        if (assessorid == null) {
            this.assessed = false;
        } else {
            this.assessed = true;
        }

    }

    public boolean isAssessed() {
        Long assessorid = assessedBy.getAssessorId();
        if (assessorid == -100l) {
            assessed = false;
            return assessed;
        } else {
            assessed = true;
            return assessed;
        }
    }

    public CosmeticDTO(Long id, BigInteger orderNb, String name, CategoryDTO category, String composition, SalesDTO createdBy, AssessorDTO assessedBy, LabDTO testedBy, ToxicologyDTO toxicologyId, Map<Long, Integer> results) {
        this.id = id;
        this.orderNb = orderNb;
        this.name = name;
        this.category = category;
        this.composition = composition;
        this.createdBy = createdBy;
        this.assessedBy = assessedBy;
        this.testedBy = testedBy;
        this.toxicology = toxicologyId;
        this.results=results;
    }

    public CosmeticDTO(Long id, BigInteger orderNb, String name, CategoryDTO category, String composition, SalesDTO createdBy, AssessorDTO assessedBy, boolean assessed) {
        this.id = id;
        this.orderNb = orderNb;
        this.name = name;
        this.category = category;
        this.composition = composition;
        this.createdBy = createdBy;
        this.assessedBy = assessedBy;
        this.assessed = assessed;
    }

    public void setHasToxicology(boolean hasToxicology) {
        if (toxicology.getToxicologyId() == -100l) {
            this.appliedToxicology = false;
        }
        {
            this.appliedToxicology = true;
        }
    }

    public boolean getHasToxicology() {
        if (toxicology.getToxicologyId() == -100l) {
            return false;
        } else {
            return true;
        }
    }

    public CosmeticDTO(Long id, BigInteger orderNb, String name, CategoryDTO category, String composition, SalesDTO createdBy, AssessorDTO assessedBy) {
        this.id = id;
        this.orderNb = orderNb;
        this.name = name;
        this.category = category;
        this.composition = composition;
        this.createdBy = createdBy;
        this.assessedBy = assessedBy;
    }

    public CosmeticDTO(BigInteger orderNb, String name, String composition) {
        this.orderNb = orderNb;
        this.name = name;
        this.composition = composition;
    }
    
    

}
