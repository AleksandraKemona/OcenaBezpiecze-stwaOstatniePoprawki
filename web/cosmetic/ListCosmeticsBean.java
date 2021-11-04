package pl.lodz.p.it.spjava.e11.sa.web.cosmetic;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import pl.lodz.p.it.spjava.e11.sa.dto.CosmeticDTO;

@ViewScoped
@Named
public class ListCosmeticsBean implements Serializable {

    @Inject
    private CosmeticController cosmeticController;

    @Inject
    private CosmeticDetailsBean cosmeticDetailsBean;

    @Inject
    private EditCosmeticBean editCosmeticBean;

    @Inject
    private DeleteCosmeticBean deleteCosmeticBean;

    @Getter
    private List<CosmeticDTO> cosmetics = null;

    @PostConstruct
    public void init() {
        cosmetics = cosmeticController.listAllCosmetics();
    }

    public String edit(CosmeticDTO cosmetic) {
        editCosmeticBean.setEditedCosmetic(cosmetic);
        return "editCosmetic";
    }

    public String showDetails(CosmeticDTO cosmeticDTO){
        System.out.println("cosmetic DTO w list "+cosmeticDTO);
       
        cosmeticDetailsBean.setDemandedAnalysis(cosmeticDTO.getCategory());
        
        cosmeticDetailsBean.setShowedCosmeticChoice(cosmeticDTO);
        
        return "cosmeticDetails";
    }

    public String showDetailsForDeletion(CosmeticDTO cosmeticDTO){
        deleteCosmeticBean.setDeletedCosmeticChoice(cosmeticDTO);
        return "deleteCosmetic";
    }

    public String chooseCosmetic(CosmeticDTO cosmeticDTO, String userName){
        return cosmeticController.chooseCosmetic(cosmeticDTO, userName);
    }

    public String resignFromCosmetic(CosmeticDTO cosmeticDTO){
        return cosmeticController.resignFromCosmetic(cosmeticDTO);
    }

}
