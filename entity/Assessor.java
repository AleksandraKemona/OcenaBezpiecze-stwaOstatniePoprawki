
package pl.lodz.p.it.spjava.e11.sa.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Assessors")
@DiscriminatorValue("Assessor")
@NamedQueries({
    @NamedQuery(name = "assessor.findAll", query = "SELECT a FROM Assessor a"),
    @NamedQuery(name = "Assessor.findByAccount", query = "SELECT a FROM Assessor a WHERE a.account = :account"),
    @NamedQuery(name = "Assessor.findById", query = "SELECT a FROM Assessor a WHERE a.assessorId = :assessorId")
})

public class Assessor extends AbstractEntity implements Serializable{
    
    @Override
    protected Object getBusinessKey() {
        return assessorStamp;
    }

    public Assessor() {
    }

    @Id
    @Getter
    @Setter
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long assessorId;

    
    @Getter
    @Setter
    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Account_ID", referencedColumnName = "Id")
    private Account account;

    @NotNull
    @Getter
    @Setter
    @Size(max=120)
    @Column(name = "AssessorStamp", unique=true, nullable=false, length=120)
    private String assessorStamp;
    
    @Getter
    @Setter
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "assessedBy")
    private List<Cosmetic> cosmeticsList = new ArrayList<>();

    public Assessor(Long assessorId, String assessorStamp) {
        this.assessorId=assessorId;
         this.assessorStamp = assessorStamp;
    }


    @Override
    public Long getId() {
        return assessorId;
    }
    
    @Override
    public String toString() {
        return assessorStamp;
    }

}