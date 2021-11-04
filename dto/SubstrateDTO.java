package pl.lodz.p.it.spjava.e11.sa.dto;

import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SubstrateDTO {

    @Getter
    @Setter
    private Long substrateId;

    @Getter
    @Setter
    @NotNull(message="{constraint.notnull")
    @Size(min=3,max=32,message="{constraint.string.length.notinrange}")
    private String substrateName;

    @Getter
    @Setter
    @NotNull(message="{constraint.notnull")
    @Size(min=3,max=10000,message="{constraint.string.length.notinrange}")
    private String substrateDescription;

}
