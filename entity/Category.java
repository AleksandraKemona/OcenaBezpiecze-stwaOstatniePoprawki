package pl.lodz.p.it.spjava.e11.sa.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.io.*;
import javax.persistence.Index;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity

@Table(name = "CATEGORIES", indexes = @Index(name = "DBCONSTRAINT_UNIQUE_CATEGORY_NAME", columnList = "categoryName", unique=true))
@NamedQueries({
    @NamedQuery(name = "Category.findAll", query = "SELECT c FROM Category c"),
    @NamedQuery(name = "Category.findByCategoryId", query = "SELECT c FROM Category c WHERE c.categoryId = :categoryId"),
    @NamedQuery(name = "Category.findByCategoryName", query = "SELECT c FROM Category c WHERE c.categoryName = :categoryName")})

@NoArgsConstructor
@ToString

public class Category extends AbstractEntity implements Serializable {

    @Override
    protected Object getBusinessKey() {
        return categoryName;
    }

    @Id
    @Column(name = "CATEGORY_ID", nullable = false)
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long categoryId;

    @Getter
    @Setter
    @NotNull(message = "{constraint.notnull}")
    @Pattern(regexp = "^[a-zA-Z,0-9- ]*$", message = "{constraint.string.incorrectchar}")
    @Size(min = 3, max = 32, message = "{constraint.string.length.notinrange}")

    private String categoryName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categoryId")
    private List<Cosmetic> cosmeticsList = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "analysis_demands",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "analysis_id"))
    private List<Analysis> demandsAnalysis = new ArrayList<>();

    public Category(Long categoryId, String categoryName, List<Analysis> demandsAnalysis) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.demandsAnalysis = demandsAnalysis;

    }

    public Category(Long categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public List<Cosmetic> getCosmeticsList() {
        return cosmeticsList;
    }

    public void setCosmeticsList(List<Cosmetic> cosmeticsList) {
        this.cosmeticsList = cosmeticsList;
    }

    public List<Analysis> getDemandsAnalysis() {
        return demandsAnalysis;
    }

    public void setDemandsAnalysis(List<Analysis> demandsAnalysis) {
        this.demandsAnalysis = demandsAnalysis;
    }

    public void addAnalysis(Analysis analysis) {
        this.demandsAnalysis.add(analysis);
        analysis.getAppliesToCategory().add(this);
    }

    public void removeAnalysis(Analysis analysis) {
        this.demandsAnalysis.remove(analysis);
        analysis.getAppliesToCategory().remove(this);
    }

    @Override
    public Long getId() {
        return categoryId;
    }
}
