package pl.lodz.p.it.spjava.e11.sa.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity

@Table(name = "ANALYSIS", indexes = @Index(name = "DBCONSTRAINT_UNIQUE_ANALYSIS_NAME", columnList = "name", unique=true))

@NamedQueries({
    @NamedQuery(name = "Analysis.findAll", query = "SELECT a FROM Analysis a"),
    @NamedQuery(name = "Analysis.findByName", query = "SELECT a FROM Analysis a WHERE a.name = :name"),
    @NamedQuery(name = "Analysis.findById", query = "SELECT a FROM Analysis a WHERE a.analysisId = :analysisId")})

@NoArgsConstructor

public class Analysis extends AbstractEntity implements Serializable {

    @Override
    protected Object getBusinessKey() {
        return name;
    }

    @Id
    @Getter
    @Setter
    @Column(name = "ANALYSIS_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    @Column(name = "MINIMUM", nullable = false)
    private int minimum;

    
    @Getter
    @Setter
    @NotNull(message="{constraint.notnull")
    @Digits(integer = 100, fraction = 2)
    @Column(name = "MAXIMUM", nullable = false)
    private int maximum;

    
    @Getter
    @Setter
    @NotNull(message="{constraint.notnull")
    @Size(min=3,max=300,message="{constraint.string.length.notinrange}")
    @Column(name = "STANDARD")
    private String standard;

    @ManyToMany(mappedBy = "demandsAnalysis")
    private List<Category> appliesToCategory = new ArrayList<>();

    public Analysis(Long analysisId, String name, int minimum, int maximum, String standard) {
        this.analysisId = analysisId;
        this.name = name;
        this.minimum = minimum;
        this.maximum = maximum;
        this.standard = standard;
    }


    public List<Category> getAppliesToCategory() {
        return appliesToCategory;
    }

    public void setAppliesToCategory(List<Category> appliesToCategory) {
        this.appliesToCategory = appliesToCategory;
    }

   @Override
    public Long getId() {
        return analysisId;
    }

    @Override
    public String toString() {
        return name;
    }
}
