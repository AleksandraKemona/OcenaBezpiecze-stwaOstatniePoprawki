package pl.lodz.p.it.spjava.e11.sa.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@ToString
public class AccountDTO {

    @Getter
    @Setter

    private Long id;

    @Setter
    private String type;

    @Getter
    @Setter
    @NotNull(message = "{constraint.notnull}")
    @Size(min = 8, max = 32, message = "{constraint.string.length.notinrange}")
    @Pattern(regexp = "^[_a-zA-Z0-9-]*$", message = "{constraint.string.incorrectchar}")
    private String login;

    @Getter
    @Setter
    @NotNull(message = "{constraint.notnull}")
    @Size(min = 8, max = 32, message = "{constraint.string.length.notinrange}")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&_])[A-Za-z\\d@$!%*?&_]*$", message = "{constraint.string.incorrectcharPassword}")
    private String password;

    @Getter
    @Setter
    private String newPassword;

    @Getter
    @Setter
    @NotNull(message = "{constraint.notnull}")
    @Pattern(regexp = "^[ A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ0-9-]*$", message = "{constraint.string.incorrectchar.name}")
    @Size(min = 3, max = 32, message = "{constraint.string.length.notinrange.name}")
    private String name;

    @Getter
    @Setter
    @NotNull(message = "{constraint.notnull}")
    @Pattern(regexp = "^[ A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ0-9-]*$", message = "{constraint.string.incorrectchar.name}")
    @Size(min = 3, max = 32, message = "{constraint.string.length.notinrange.name}")
    private String surname;

    @Getter
    @Setter
    @NotNull(message = "{constraint.notnull}")
    @Size(min = 6, max = 64, message = "{constraint.string.length.notinrange}")
    @Pattern(regexp = "^[_a-zA-Z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$", message = "{constraint.string.incorrectemail}")
    private String email;

    @Getter
    @Setter
    @Size(min = 9, max = 9, message = "{constraint.string.length.notinrange.phone}")
    @Pattern(regexp = "^[0-9]+", message = "{constraint.string.incorrectchar.phone}")
    private String phone;

    @Getter
    @Setter
    private AdministratorDTO verifiedBy;

    @Getter
    @Setter
    private boolean active;

    @Getter
    @Setter
    @NotNull(message = "{constraint.notnull}")
    private String question;

    @Getter
    @Setter
    @NotNull(message = "{constraint.notnull}")
    private String answer;

    @Getter
    @Setter
    private String newAnswer;

    private boolean confirmed;

    public void setConfirmed(boolean isConfirmed) {
        if (verifiedBy != null) {
            this.confirmed = true;
        }
        {
            this.confirmed = false;
        }

    }

    public boolean isConfirmed() {
        if (verifiedBy != null) {
            return true;
        } else {
            return false;
        }
    }

    public String getType() {
        if (type == null) {
            return "Unverified";
        
        }else{
        return type;
        }
    }

    public AccountDTO(Long id, String type, String login, String password, String name, String surname, String email, String phone, AdministratorDTO verifiedBy, boolean active, String question, String answer) {
        this.id = id;
        this.type = type;
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.verifiedBy = verifiedBy;
        this.active = active;
        this.question = question;
        this.answer = answer;
    }

    public AccountDTO(String login, String name, String surname, String email, String phone) {
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
    }

    public AccountDTO(Long id) {
        this.id = id;
    }

}
