package pl.lodz.p.it.spjava.e11.sa.utils.converter;

import java.util.ArrayList;
import java.util.List;
import pl.lodz.p.it.spjava.e11.sa.dto.AssessorDTO;
import pl.lodz.p.it.spjava.e11.sa.dto.CosmeticDTO;
import pl.lodz.p.it.spjava.e11.sa.entity.Assessor;
import pl.lodz.p.it.spjava.e11.sa.entity.Cosmetic;

public class CosmeticConverter {

    public static Cosmetic createCosmeticEntityFromDTO(CosmeticDTO cosmeticDTO) {

        return new Cosmetic(cosmeticDTO.getId(), cosmeticDTO.getOrderNb(),
                cosmeticDTO.getName(), CategoryConverter.createCategoryEntityFromDTOForCosmetic(cosmeticDTO.getCategory()),
                cosmeticDTO.getComposition(),
                AccountsConverter.createSalesEntityFromDTO(cosmeticDTO.getCreatedBy()),
                AccountsConverter.createAssessorEntityFromDTO(cosmeticDTO.getAssessedBy()),
                AccountsConverter.createLabEntityFromDTO(cosmeticDTO.getTestedBy()),
                ToxicologyConverter.createToxicologyEntityFromDTO(cosmeticDTO.getToxicology()),
                cosmeticDTO.getResults());
    }

    public static CosmeticDTO createCosmeticDTOFromEntity(Cosmetic cosmetic) {
        return new CosmeticDTO(cosmetic.getId(), cosmetic.getOrderNb(),
                cosmetic.getName(), CategoryConverter.createCategoryDTOFromEntityForCosmetic(cosmetic.getCategoryId()),
                cosmetic.getComposition(),
                AccountsConverter.createSalesDTOFromEntity(cosmetic.getCreatedBy()),
                AccountsConverter.createAssessorDTOFromEntity(cosmetic.getAssessedBy()),
                AccountsConverter.createLabDTOFromEntity(cosmetic.getTestedBy()),
                ToxicologyConverter.createToxicologyDTOFromEntity(cosmetic.getToxicology()),
                cosmetic.getResults());
    }

    public static CosmeticDTO createCosmeticDTOFromEntityTest(Cosmetic cosmetic) {
        return new CosmeticDTO(cosmetic.getId(), cosmetic.getOrderNb(),
                cosmetic.getName(), CategoryConverter.createCategoryDTOFromEntityForCosmetic(cosmetic.getCategoryId()),
                cosmetic.getComposition(),
                AccountsConverter.createSalesDTOFromEntity(cosmetic.getCreatedBy()),
                AccountsConverter.createAssessorDTOFromEntity(cosmetic.getAssessedBy())
        );
    }

//    public static CosmeticDTO createCosmeticDTOFromEntityTest(Cosmetic cosmetic) {
//        return new CosmeticDTO(cosmetic.getId(), cosmetic.getOrderNb(),
//                cosmetic.getName(), CategoryConverter.createCategoryDTOFromEntityForCosmetic(cosmetic.getCategoryId()),
//                cosmetic.getComposition());
//    }
//    List<Cosmetic> listCosmetics = cosmeticFacade.findAll();
    public static List<CosmeticDTO> createListCosmeticDTOFromEntity(List<Cosmetic> listCosmetics) {
        List<CosmeticDTO> listCosmeticsDTO = new ArrayList<>();

        for (Cosmetic cosmetic : listCosmetics) {
            CosmeticDTO cosmeticDTO = new CosmeticDTO(cosmetic.getId(), cosmetic.getOrderNb(),
                    cosmetic.getName(), CategoryConverter.createCategoryDTOFromEntityForCosmetic(cosmetic.getCategoryId()),
                    cosmetic.getComposition(),
                    AccountsConverter.createSalesDTOFromEntity(cosmetic.getCreatedBy()),
                    AccountsConverter.createAssessorDTOFromEntity(cosmetic.getAssessedBy()),
                    AccountsConverter.createLabDTOFromEntity(cosmetic.getTestedBy()),
                    ToxicologyConverter.createToxicologyDTOFromEntity(cosmetic.getToxicology()),
                    cosmetic.getResults());
            listCosmeticsDTO.add(cosmeticDTO);
        }

        return listCosmeticsDTO;
    }

    public static List<CosmeticDTO> createListCosmeticDTOFromEntityTest(List<Cosmetic> listCosmetics) {
        List<CosmeticDTO> listCosmeticsDTO = new ArrayList<>();
        for (Cosmetic cosmetic : listCosmetics) {
            AssessorDTO test = AccountsConverter.createAssessorDTOFromEntity(cosmetic.getAssessedBy());

            CosmeticDTO cosmeticDTO = new CosmeticDTO(cosmetic.getId(), cosmetic.getOrderNb(),
                    cosmetic.getName(), CategoryConverter.createCategoryDTOFromEntityForCosmetic(cosmetic.getCategoryId()),
                    cosmetic.getComposition(),
                    AccountsConverter.createSalesDTOFromEntity(cosmetic.getCreatedBy()),
                    AccountsConverter.createAssessorDTOFromEntity(cosmetic.getAssessedBy()));

            cosmeticDTO.setAssessedBy(test);
            listCosmeticsDTO.add(cosmeticDTO);
        }

        return listCosmeticsDTO;
    }

}
