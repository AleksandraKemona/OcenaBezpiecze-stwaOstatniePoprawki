
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
public class AssessorDTO implements Serializable {
    
    @Getter
    @Setter
    private Long assessorId;

    @Getter
    @Setter
    private String assessorStamp;

    @Getter
    @Setter

    private List<CosmeticDTO> cosmeticsDTOList = new ArrayList<>();

    public AssessorDTO(Long assessorId, String assessorStamp) {
        this.assessorId = assessorId;
        this.assessorStamp = assessorStamp;
    
    }

    
    

}
