package pl.lodz.p.it.spjava.e11.sa.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "TOXICOLOGY")
@NamedQueries({
    @NamedQuery(name = "Toxicology.findAll", query = "SELECT t FROM Toxicology t"),
    @NamedQuery(name = "Toxicology.findById", query = "SELECT t FROM Toxicology t WHERE t.toxicologyId = :toxicologyId"),
    @NamedQuery(name = "Toxicology.findByToxicologyName", query = "SELECT t FROM Toxicology t WHERE t.toxicologyName = :toxicologyName")})
@NoArgsConstructor
@ToString

public class Toxicology extends AbstractEntity implements Serializable {

    @Override
    protected Object getBusinessKey() {
        return toxicologyName;
    }
    
    @Id
    @Getter
    @Setter
    @Column(name = "TOXICOLOGY_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long toxicologyId;


    @Basic(optional = false)
    @NotNull
    @Getter
    @Setter
    @Column(name = "NAME", unique = false, nullable = false)
    private String toxicologyName;

    @Getter
    @Setter
    @OneToOne(mappedBy = "toxicology", cascade = {CascadeType.MERGE})
    private Cosmetic cosmetic;

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "described_By",
            joinColumns = @JoinColumn(name = "toxicology_id"),
            inverseJoinColumns = @JoinColumn(name = "substrate_id"))
    private List<Substrate> describedBy = new ArrayList<>();

    public Toxicology(Long toxicologyId, List<Substrate> describedBy) {
        this.toxicologyId = toxicologyId;
        this.describedBy = describedBy;
    }

    public List<Substrate> getDescribedBy() {
        return describedBy;
    }

    public void setDescribedBy(List<Substrate> describedBy) {
        this.describedBy = describedBy;
    }

    public void addSubstrate(Substrate substrate) {
        this.describedBy.add(substrate);
        substrate.getUsedInToxicology().add(this);
    }

    public void removeSubstrate(Substrate substrate) {
        this.describedBy.remove(substrate);
        substrate.getUsedInToxicology().remove(this);
    }

    @Override
    public Long getId() {
        return toxicologyId;
    }
    

}
