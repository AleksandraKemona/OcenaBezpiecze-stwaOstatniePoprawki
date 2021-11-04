package pl.lodz.p.it.spjava.e11.sa.web.substrate;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import pl.lodz.p.it.spjava.e11.sa.dto.SubstrateDTO;

@ViewScoped
@Named
public class ListSubstratesBean implements Serializable {

    @Inject
    private SubstrateController substrateController;

    @Inject
    private EditSubstrateBean editSubstrateBean;
    
    @Getter
    private List<SubstrateDTO> substrates = null;

    @PostConstruct
    public void init(){
        substrates = substrateController.listAllSubstrates();
    }

    public String edit(SubstrateDTO substrateDTO){
        editSubstrateBean.setEditedSubstrate(substrateDTO);
        return "editSubstrate";
    }

}
