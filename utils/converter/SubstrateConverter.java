package pl.lodz.p.it.spjava.e11.sa.utils.converter;

import java.util.ArrayList;
import java.util.List;
import pl.lodz.p.it.spjava.e11.sa.dto.SubstrateDTO;
import pl.lodz.p.it.spjava.e11.sa.entity.Substrate;

public class SubstrateConverter {

    public static Substrate createSubstrateEntityFromDTO(SubstrateDTO substrateDTO) {
        return new Substrate(substrateDTO.getSubstrateId(), substrateDTO.getSubstrateName(), substrateDTO.getSubstrateDescription());
    }

    public static SubstrateDTO createSubstrateDTOFromEntity(Substrate substrate) {
        return new SubstrateDTO(substrate.getSubstrateId(), substrate.getSubstrateName(), substrate.getSubstrateDescription());
    }

    public static List<SubstrateDTO> createListSubstrateDTOFromEntity(List<Substrate> listSubstrates) {
        List<SubstrateDTO> listSubstratesDTO = new ArrayList<>();
        for (Substrate substrate : listSubstrates) {
            SubstrateDTO substrateDTO = new SubstrateDTO(substrate.getSubstrateId(),
                    substrate.getSubstrateName(), substrate.getSubstrateDescription());
            listSubstratesDTO.add(substrateDTO);
        }
        return listSubstratesDTO;
    }
    
    public static List<Substrate> createListSubstrateEntityFromDTO(List<SubstrateDTO> listSubstratesDTO) {
        List<Substrate> listSubstrates = new ArrayList<>();
        for (SubstrateDTO substrateDTO : listSubstratesDTO) {
            Substrate substrate = new Substrate(substrateDTO.getSubstrateId(),
                    substrateDTO.getSubstrateName(), substrateDTO.getSubstrateDescription());
            listSubstrates.add(substrate);
        }
        return listSubstrates;
    }
}
