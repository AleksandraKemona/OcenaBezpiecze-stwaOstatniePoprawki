package pl.lodz.p.it.spjava.e11.sa.ejb.manager;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import pl.lodz.p.it.spjava.e11.sa.ejb.facade.AnalysisFacade;
import pl.lodz.p.it.spjava.e11.sa.entity.Analysis;
import pl.lodz.p.it.spjava.e11.sa.exception.AppBaseException;


@Stateful
public class AnalysisManager extends AbstractManager{
    @EJB
    private AnalysisFacade analysisFacade;
    
    private Analysis editedAnalysis;
    
    private Analysis appliedAnalysis;
    
    public void createAnalysis(Analysis newAnalysis) throws AppBaseException {
        analysisFacade.create(newAnalysis);
    }
    
    public List<Analysis> downloadAllAnalysis() throws AppBaseException{
        return analysisFacade.findAll();
    }
    
    

    public void saveAnalysisAfterEdition(Analysis analysis) throws AppBaseException{
        analysisFacade.edit(analysis);
       
    }
    
    public Analysis downloadAnalysisForDetails(Long id) {
        Analysis downloadedAnalysis = analysisFacade.find(id);
//        if (null == pobieraneKonto)
//            throw ...;
        analysisFacade.refresh(downloadedAnalysis);
        return downloadedAnalysis;
    }
    
}
