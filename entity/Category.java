package pl.lodz.p.it.spjava.e11.sa.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.io.*;
import java.util.Objects;

@Entity
@Table(name = "CATEGORIES")
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

    @Basic(optional = false)
    @NotNull
    @Getter
    @Setter
    @Column(name = "CATEGORY_NAME", unique = true, nullable = false)
    private String categoryName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categoryId")
    private List<Cosmetic> cosmeticsList = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "analysis_demands",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "analysis_id"))
    private List<Analysis> demandsAnalysis= new ArrayList<>();

    public Category(Long categoryId, String categoryName, List<Analysis> demandsAnalysis) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.demandsAnalysis = demandsAnalysis;

    }
    
    public Category(Long categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    } //ToDo sprawdić czy to jest w ogóle potrzebne

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
