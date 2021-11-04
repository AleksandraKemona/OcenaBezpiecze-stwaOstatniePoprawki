
package pl.lodz.p.it.spjava.e11.sa.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor
@ToString

public class AdministratorDTO implements Serializable {

    @Getter
    @Setter
    private Long adminId;
    
    @Getter
    @Setter
    private String adminStamp;
    
    @Getter
    @Setter
    @NotNull
    private AccountDTO accountId;
    
    @Getter
    @Setter

    private List<AccountDTO> accountsDTOList = new ArrayList<>();

    public AdministratorDTO(Long adminId, String adminStamp) {
        this.adminId = adminId;
        this.adminStamp = adminStamp;
    
    }
    
    
    
  
    
    
    

    
    
    
    

}
