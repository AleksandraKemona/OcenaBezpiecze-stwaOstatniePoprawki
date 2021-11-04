package pl.lodz.p.it.spjava.e11.sa.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Accounts")
//@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
@DiscriminatorValue("ACCOUNTS")
@NamedQueries({
    //    @NamedQuery(name = "administrator.findAll", query = "SELECT a FROM Administrator a"),
    @NamedQuery(name = "Account.findByLogin", query = "SELECT a FROM Account a WHERE a.login = :login"),
    @NamedQuery(name = "Account.findById", query = "SELECT a FROM Account a WHERE a.accountId = :accountId")
})

public class Account extends AbstractEntity implements Serializable {

    public Account() {
    }

    @Override
    protected Object getBusinessKey() {
        return login;
    }
    
    
    @Id
    @Getter
    @Setter
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long accountId;


    @Getter
    @Setter
    @Column(name = "type", updatable = true)
    private String type;

    @Getter
    @Setter
    @NotNull(message = "{constraint.notnull}")
    @Size(min = 6, max = 32, message = "{constraint.string.length.notinrange}")
    @Pattern(regexp = "^[_a-zA-Z0-9-]*$", message = "{constraint.string.incorrectchar}")
    @Column(name = "login", length = 32, nullable = false, unique = true, updatable = true)
    private String login;

    @Getter
    @Setter
    @NotNull(message = "{constraint.notnull}")
    @Size(min = 6, max = 32, message = "{constraint.string.length.notinrange}")
    @Column(name = "password", length = 256, nullable = false)
    private String password;

    @Getter
    @Setter
    @NotNull(message = "{constraint.notnull}")
    @Pattern(regexp = "^[ A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ0-9-]*$", message = "{constraint.string.incorrectchar.name}")
    @Size(min = 3, max = 32)
    @Column(name = "name", length = 32, nullable = false)
    private String name;

    @Getter
    @Setter
    @NotNull(message = "{constraint.notnull}")
    @Pattern(regexp = "^[ A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ0-9-]*$", message = "{constraint.string.incorrectchar.name}")
    @Size(min = 3, max = 32, message = "{constraint.string.length.notinrange.name}")
    @Column(name = "surname", length = 32, nullable = false)
    private String surname;

    @Getter
    @Setter
    @NotNull(message = "{constraint.notnull}")
    @Size(min = 6, max = 64, message = "{constraint.string.length.notinrange}")
    @Pattern(regexp = "^[_a-zA-Z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$", message = "{constraint.string.incorrectemail}")
    @Column(name = "email", length = 64, unique = true, nullable = false)
    private String email;


    @Getter
    @Setter
    @Size(min = 9, max = 9, message = "{constraint.string.length.notinrange.phone}")
    @Pattern(regexp = "^[0-9]+", message = "{constraint.string.incorrectchar.phone}")
    @Column(name = "phone", length = 12, unique = true, nullable = true)
    private String phone;

    @Getter
    @Setter
    @JoinColumn(name = "verifiedBy", nullable = true)
    @ManyToOne(optional = true, cascade = {CascadeType.MERGE})
    private Administrator verifiedBy;

    @Getter
    @Setter
    @Column(name = "isActive", nullable = false)
    private boolean isActive = false;

    @Getter
    @Setter
    @NotNull(message = "{constraint.notnull}")
    @Column(name = "question", length = 120)
    private String question;

    @Getter
    @Setter
    @NotNull(message = "{constraint.notnull}")
    @Column(name = "answer", length = 120)
    private String answer;

    @Getter
    @Setter
    @OneToOne(mappedBy = "account", cascade = {CascadeType.MERGE})
    private Administrator administrator;

    public Account(Long accountId, String login, String name, String surname, String email, String phone, String question, String answer) {
        this.accountId = accountId;
        this.login = login;

        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.question = question;
        this.answer = answer;
    }

    public Account(Long accountId) {
        this.accountId = accountId;
    }
    
    @Override
    public Long getId() {
        return accountId;
    }
}
