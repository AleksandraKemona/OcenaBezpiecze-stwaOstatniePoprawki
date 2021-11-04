/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.spjava.e11.sa.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor

public class ToxicologyDTO {

    @Getter
    @Setter
    private Long toxicologyId;

    @Getter
    @Setter
    private List<SubstrateDTO> describedBy;

    public ToxicologyDTO(Long toxicologyId, List<SubstrateDTO> describedBy) {
        this.toxicologyId = toxicologyId;
        this.describedBy = describedBy;
    }

    @Override
        public String toString() {
        return  toxicologyId.toString();
    }

}
