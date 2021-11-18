
package pl.lodz.p.it.spjava.e11.sa.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "LABTECHNICIANS")
@DiscriminatorValue("LABTECHNCIAN")
@NamedQueries({
    @NamedQuery(name = "labTechnician.findAll", query = "SELECT a FROM LabTechnician a"),
})
@NoArgsConstructor

public class LabTechnician extends AbstractEntity implements Serializable{
    
 @Override
    protected Object getBusinessKey() {
        return labStamp;
    }
    
    @Id
    @Getter
    @Setter
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long labId;
    
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
    @Column(name = "LabStamp", unique=true, nullable=false, length=120)
    private String labStamp;
    
    @Getter
    @Setter
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "testedBy")
    private List<Cosmetic> cosmeticsList = new ArrayList<>();

    public LabTechnician(Long labId, String labStamp) {
        this.labId=labId;
        this.labStamp = labStamp;
    }

    @Override
    public String toString() {
        return labStamp;
    }
    
@Override
    public Long getId() {
        return labId;
    }
    
    
}
