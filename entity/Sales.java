
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
@Table(name = "Sales")
@DiscriminatorValue("Sales")
@NamedQueries({
//    @NamedQuery(name = "sales.findAll", query = "SELECT a FROM Sales a"),
    @NamedQuery(name = "Sales.findByAccount", query = "SELECT s FROM Sales s WHERE s.account = :account"),
    @NamedQuery(name = "Sales.findById", query = "SELECT s FROM Sales s WHERE s.salesId = :salesId")
})

public class Sales extends AbstractEntity implements Serializable{
    
    public Sales() {
    }
    
    @Override
    protected Object getBusinessKey() {
        return salesStamp;
    }

    @Id
    @Getter
    @Setter
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long salesId;

    @NotNull
    @Getter
    @Setter
    @Version
    @Column(name = "VERSION", nullable = false)
    private long version;

    @NotNull
    @Getter
    @Column(name = "LAST_MODIFIED", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModified;
    
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
    @Column(name = "SalesStamp", unique=true, nullable=false, length=120)
    private String salesStamp;
    
    @Getter
    @Setter
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "createdBy")
    private List<Cosmetic> cosmeticsList = new ArrayList<>();

    public Sales(Long salesId, String salesStamp) {
        this.salesId = salesId;
        this.salesStamp=salesStamp;
    }

   
    @Override
    public Long getId() {
        return salesId;
    }

   @Override
    public String toString() {
        return salesStamp;
    }

    
    
}
