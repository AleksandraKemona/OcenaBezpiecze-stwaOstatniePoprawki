package pl.lodz.p.it.spjava.e11.sa.entity;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Administrators")

@DiscriminatorValue("ADMINISTRATOR")
@NamedQueries({
//    @NamedQuery(name = "administrator.findAll", query = "SELECT a FROM Administrator a"),
    @NamedQuery(name = "Administrator.findById", query = "SELECT a FROM Administrator a WHERE a.adminId = :adminId"),
    @NamedQuery(name = "Administrator.findByAccount", query = "SELECT a FROM Administrator a WHERE a.account = :account")
})
public class Administrator extends AbstractEntity implements Serializable {
    
    public Administrator() {
    }
    @Override
    protected Object getBusinessKey() {
        return adminStamp;
    }

    
    
    @Id
    @Getter
    @Setter
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long adminId;


    @NotNull
    @Getter
    @Setter
    @Size(max = 120)
    @Column(name = "AdminStamp", unique = true, nullable = false, length = 120)
    private String adminStamp;
    
    
    @Getter
    @Setter
    @NotNull
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "Account_ID", referencedColumnName = "Id")
    private Account account;
    
    @Getter
    @Setter
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "verifiedBy")
    private List<Account> accountsList = new ArrayList<>();

    

    public Administrator(String adminStamp) {
        this.adminStamp = adminStamp;
        
    }
    

  
     @Override
    public Long getId() {
        return adminId;
    }
}
