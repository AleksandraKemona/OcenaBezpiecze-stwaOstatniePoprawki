package pl.lodz.p.it.spjava.e11.sa.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Map;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Cosmetics", indexes = @Index(name = "DBCONSTRAINT_UNIQUE_COSMETIC_NAME", columnList = "name", unique=true))
@NamedQueries({
    @NamedQuery(name = "Cosmetic.findAll", query = "SELECT c FROM Cosmetic c"),
    @NamedQuery(name = "Cosmetic.findByName", query = "SELECT c FROM Cosmetic c WHERE c.name = :name"),
    @NamedQuery(name = "Cosmetic.findById", query = "SELECT c FROM Cosmetic c WHERE c.cosmeticId = :cosmeticId")
})
@NoArgsConstructor
public class Cosmetic extends AbstractEntity implements Serializable {
    
    @Override
    protected Object getBusinessKey() {
        return name;
    }
    
   
    @Id
    @Column(name = "COSMETIC_ID", updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cosmeticId;

    @Getter
    @Setter
    @Digits(integer = 12, fraction = 0)
    @Column(name = "ORDER_NB")
    private BigInteger orderNb;

    @NotNull(message="{constraint.notnull}")
    @Getter
    @Setter
    @Size(min=3,max=32,message="{constraint.string.length.notinrange}")
    @Pattern(regexp="^[a-zA-Z,0-9- ]*$",message="{constraint.string.incorrectchar}")
    private String name;

    @Getter
    @Setter
    @NotNull(message="{constraint.notnull}")
    @JoinColumn(name = "CATEGORY_ID", nullable = true)
    @ManyToOne(optional = true)
    private Category categoryId;
    
    @Getter
    @Setter
    @NotNull(message="{constraint.notnull}")
    @Pattern(regexp="^[a-zA-Z, ]*$",message="{constraint.string.incorrectchar}")
    @Column(name = "COMPOSITION")
    private String composition;
    
    @Getter
    @Setter
    @Column(name = "RESULTS")
    private Map<Long, Integer> results;
    

    @Getter
    @Setter
    @JoinColumn(name = "CREATED_BY", nullable = true)
    @ManyToOne(optional = true, cascade = {CascadeType.MERGE})
    private Sales createdBy;

    @Getter
    @Setter
    @JoinColumn(name = "ASSESSED_BY", nullable = true)
    @ManyToOne(optional = true, cascade = {CascadeType.MERGE})
    private Assessor assessedBy;

    @Getter
    @Setter
    @JoinColumn(name = "TESTED_BY", nullable = true)
    @ManyToOne(optional = true, cascade = {CascadeType.MERGE})
    private LabTechnician testedBy;
    
    @Getter
    @Setter
    @OneToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "TOXICOLOGY_ID")
    private Toxicology toxicology;

    public Cosmetic(Long cosmeticId, BigInteger orderNb, String name, Category categoryId, String composition, 
                    Sales createdBy, Assessor assessedBy, LabTechnician testedBy, Toxicology toxicology, Map<Long, Integer> results) {
        this.cosmeticId = cosmeticId;
        this.orderNb = orderNb;
        this.name = name;
        this.categoryId = categoryId;
        this.composition = composition;
        this.createdBy = createdBy;
        this.assessedBy = assessedBy;
        this.testedBy = testedBy;
        this.toxicology = toxicology;
        this.results=results;

    }
    
    @Override
    public Long getId() {
        return cosmeticId;
    }

    @Override
    public String toString() {
        return name;
    }

}
