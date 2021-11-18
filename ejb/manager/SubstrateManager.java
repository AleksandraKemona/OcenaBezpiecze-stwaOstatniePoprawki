package pl.lodz.p.it.spjava.e11.sa.ejb.manager;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import pl.lodz.p.it.spjava.e11.sa.ejb.facade.SubstrateFacade;
import pl.lodz.p.it.spjava.e11.sa.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.it.spjava.e11.sa.entity.Substrate;
import pl.lodz.p.it.spjava.e11.sa.exception.AppBaseException;
import pl.lodz.p.it.spjava.e11.sa.exception.SubstrateException;

@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors(LoggingInterceptor.class)
public class SubstrateManager extends AbstractManager {

    @EJB
    private SubstrateFacade substrateFacade;

    private Substrate editedSubstrate;

    private Substrate appliedSubstrate;

    private List<Substrate> substartesForVerification;

    public void createSubstrate(Substrate newSubstrate) throws AppBaseException {
        substrateFacade.create(newSubstrate);
    }

    public List<Substrate> downloadAllSubstrates() throws AppBaseException {
        return substrateFacade.findAll();
    }

    public Substrate downloadSubstrateForCosmetic(String substrateName) throws AppBaseException {
        appliedSubstrate = substrateFacade.findBySubstrateName(substrateName);
        if (null == appliedSubstrate) {
            throw SubstrateException.createExceptionWrongStateForCosmetic(appliedSubstrate);
        } else {
            substrateFacade.refresh(appliedSubstrate);
            return appliedSubstrate;
        }
    }

    public Substrate downloadSubstrateForEdition(Long id) throws AppBaseException {
        editedSubstrate = substrateFacade.find(id);
        if (null == editedSubstrate) {
            throw SubstrateException.createExceptionWrongState(editedSubstrate);
        } else {
            substrateFacade.refresh(editedSubstrate);
            return editedSubstrate;
        }
    }

    public void saveSubstrateAfterEdition(Substrate substrate) throws AppBaseException {
        substrateFacade.edit(substrate);
    }

}
