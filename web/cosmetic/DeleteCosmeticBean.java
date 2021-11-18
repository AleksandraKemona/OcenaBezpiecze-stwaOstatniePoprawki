/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.spjava.e11.sa.web.cosmetic;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.spjava.e11.sa.dto.CosmeticDTO;
import pl.lodz.p.it.spjava.e11.sa.exception.AppBaseException;

@Named
@NoArgsConstructor
@SessionScoped
public class DeleteCosmeticBean implements Serializable {

    @Inject
    private CosmeticController cosmeticController;

    private CosmeticDTO cosmeticForDeletion;

    private CosmeticDTO deletedCosmeticChoice;

    public void setDeletedCosmeticChoice(CosmeticDTO deletedCosmetic){
        this.deletedCosmeticChoice = deletedCosmetic;
        init();
    }

    private void init(){
        cosmeticForDeletion = cosmeticController.getCosmeticForDeletion(deletedCosmeticChoice);
    }


    public CosmeticDTO getCosmeticForDeletion() {
        if (null != cosmeticForDeletion) {
            return cosmeticForDeletion;
        } else {
            return new CosmeticDTO();
        }
    }

    public String confirmDeleteCosmetic(CosmeticDTO cosmeticDTO){
        cosmeticDTO = this.deletedCosmeticChoice;
        return cosmeticController.confirmDeleteCosmetic(cosmeticDTO);
         
    }

    public String abort() {
        return "listCosmetics";
    }

    public String refresh(){
        init();
        return "";
    }
}

