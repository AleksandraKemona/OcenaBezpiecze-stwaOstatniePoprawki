/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.spjava.e11.sa.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity

@Table(name = "SUBSTRATES", indexes = @Index(name = "sn_unique", columnList = "SUBSTRATE_NAME"))

@NamedQueries({
    @NamedQuery(name = "Substrate.findAll", query = "SELECT s FROM Substrate s"),
    @NamedQuery(name = "Substrate.findBySubstrateId", query = "SELECT s FROM Substrate s WHERE s.substrateId = :substrateId"),
    @NamedQuery(name = "Substrate.findBySubstrateName", query = "SELECT s FROM Substrate s WHERE s.substrateName = :substrateName")})

@NoArgsConstructor

public class Substrate extends AbstractEntity implements Serializable {

    @Override
    protected Object getBusinessKey() {
        return substrateName;
    }

    @Id
    @Column(name = "SUBSTRATE_ID", nullable = false)
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long substrateId;
    
    @Getter
    @Setter
    
    @NotNull(message="{constraint.notnull")
    @Size(min=3,max=32,message="{constraint.string.length.notinrange}")
    @Column(name = "SUBSTRATE_NAME", unique = true, nullable = false)
    private String substrateName;

    @Getter
    @Setter
    @NotNull(message="{constraint.notnull")
    @Size(min=3,max=10000,message="{constraint.string.length.notinrange}")
    @Column(name = "SUBSTRATE_DESCRIPTION", unique = false, nullable = false)
    private String substrateDescription;

    @ManyToMany(mappedBy = "describedBy")
//    @JoinTable(
//            name = "describedBy",
//            joinColumns = {
//                @JoinColumn(name = "SUBSTRATE_ID", nullable = false)},
//            inverseJoinColumns = {
//                @JoinColumn(name = "TOXYCOLOGY_ID", nullable = false)}
//    )
    private List<Toxicology> usedInToxicology = new ArrayList<>();

    public Substrate(Long substrateId, String substrateName, String substrateDescription) {
        this.substrateId = substrateId;
//        this.version = version;
//        this.lastModified = lastModified;
        this.substrateName = substrateName;
        this.substrateDescription = substrateDescription;
    }

    public List<Toxicology> getUsedInToxicology() {
        return usedInToxicology;
    }

    public void setUsedInToxicology(List<Toxicology> usedInToxicology) {
        this.usedInToxicology = usedInToxicology;
    }
 

    @Override
    public String toString() {
        return substrateName;
    }
    
    @Override
    public Long getId() {
        return substrateId;
    }

}
