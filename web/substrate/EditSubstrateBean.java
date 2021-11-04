package pl.lodz.p.it.spjava.e11.sa.web.substrate;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.spjava.e11.sa.dto.SubstrateDTO;

@SessionScoped
@Named
public class EditSubstrateBean implements Serializable {
    
    @Inject
    private SubstrateController substrateController;

    private SubstrateDTO editedSubstrate;

    public SubstrateDTO getEditedSubstrate() {
        if (null != editedSubstrate) {
            return editedSubstrate;
        } else {
            return new SubstrateDTO();
        }
    }

    public void setEditedSubstrate(SubstrateDTO editedSubstrateDTO){    
        this.editedSubstrate = substrateController.getSubstrateForEdition(editedSubstrateDTO);
    }
    
    public String saveSubstrate(){
        if (null == editedSubstrate) {
            return "main";
        }
        return substrateController.saveSubstrateAfterEdition(editedSubstrate);   
    }

    public String abort() {
        return "listSubstrates";
    }
    
}
