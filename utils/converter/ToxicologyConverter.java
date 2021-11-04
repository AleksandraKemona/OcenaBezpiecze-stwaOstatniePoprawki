/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.spjava.e11.sa.utils.converter;

import java.util.ArrayList;
import java.util.List;
import pl.lodz.p.it.spjava.e11.sa.dto.CategoryDTO;
import pl.lodz.p.it.spjava.e11.sa.dto.SubstrateDTO;
import pl.lodz.p.it.spjava.e11.sa.dto.ToxicologyDTO;
import pl.lodz.p.it.spjava.e11.sa.entity.Category;
import pl.lodz.p.it.spjava.e11.sa.entity.Toxicology;

/**
 *
 * @author Ola
 */
public class ToxicologyConverter {

    public static Toxicology createToxicologyEntityFromDTO(ToxicologyDTO toxicologyDTO) {
        return new Toxicology(toxicologyDTO.getToxicologyId(), SubstrateConverter.createListSubstrateEntityFromDTO(toxicologyDTO.getDescribedBy()));
    }

    public static ToxicologyDTO createToxicologyDTOFromEntity(Toxicology toxicology) {
        ToxicologyDTO toxicologyDTO = new ToxicologyDTO();
        SubstrateDTO substrateDTO = new SubstrateDTO();
        List<SubstrateDTO> list= new ArrayList<>();
        list.add(substrateDTO);
        if (toxicology==null) {
            toxicologyDTO.setToxicologyId(-100l);
            toxicologyDTO.setDescribedBy(list);   
        }
        else{
            toxicologyDTO.setToxicologyId(toxicology.getToxicologyId());
            toxicologyDTO.setDescribedBy(SubstrateConverter.createListSubstrateDTOFromEntity(toxicology.getDescribedBy()));
        }
        return toxicologyDTO;
    }
    
  
    

    public static List<ToxicologyDTO> createListToxicologiesDTOFromEntity(List<Toxicology> listToxicologies) {
        List<ToxicologyDTO> listToxicologiesDTO = new ArrayList<>();

        for (Toxicology toxicology : listToxicologies) {
            ToxicologyDTO toxicologyDTO = new ToxicologyDTO(toxicology.getToxicologyId(), SubstrateConverter.createListSubstrateDTOFromEntity(toxicology.getDescribedBy()));
            listToxicologiesDTO.add(toxicologyDTO);
        }

        return listToxicologiesDTO;
    }

}
