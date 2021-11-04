package pl.lodz.p.it.spjava.e11.sa.web.substrate;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Setter;
import pl.lodz.p.it.spjava.e11.sa.dto.SubstrateDTO;

@SessionScoped
@Named
public class CreateSubstrateBean implements Serializable {

    @Inject
    private SubstrateController substrateController;

    @Setter
    private SubstrateDTO newSubstrate;
    
    public SubstrateDTO getNewSubstrate() {
        if (null != newSubstrate) {

            return newSubstrate;
        } else {
            return new SubstrateDTO();
        }
    }
    
    public String begin() {
        newSubstrate = new SubstrateDTO();
        return "createSubstrate";
    }
    
    public String createSubstrate(){
        if (null == newSubstrate.getSubstrateName()) {
            return null;
        }
       return substrateController.createSubstrate(newSubstrate);
    }
    
    public String abort() {
        return "listSubstrates";
    }

}
