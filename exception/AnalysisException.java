package pl.lodz.p.it.spjava.e11.sa.exception;

import javax.persistence.OptimisticLockException;
import pl.lodz.p.it.spjava.e11.sa.entity.Analysis;



/**
 *
 */
public class AnalysisException extends AppBaseException {

    static final public String KEY_ANALYSIS_NAME_EXISTS = "error.analysis.db.constraint.uniq.analysisName";
    static final public String KEY_ANALYSIS_OPTIMISTIC_LOCK = "error.analysis.optimisticlock";
    static final public String KEY_ANALYSIS_NOT_READ_FOR_EDITION = "error.analysis.analysisNotDownoladedForEdition";
    static final public String KEY_ANALYSIS_NOT_READ_FOR_CATEGORY = "error.analysis.analysisNotDownoladedForCosmetic";

    public AnalysisException(String message) {
        super(message);
    }

    private AnalysisException(String message, Throwable cause) {
        super(message, cause);
    }

    private Analysis analysis;

    public Analysis getAnalysis() {
        return analysis;
    }
    
    public static AnalysisException createExceptionWrongState(Analysis analysis) {
        AnalysisException ae = new AnalysisException(KEY_ANALYSIS_NOT_READ_FOR_EDITION);
        ae.analysis=analysis;
        return ae;
    }
    
    public static AnalysisException createExceptionWrongStateForCosmetic(Analysis analysis) {
        AnalysisException ae = new AnalysisException(KEY_ANALYSIS_NOT_READ_FOR_CATEGORY);
        ae.analysis=analysis;
        return ae;
    }

    
    
    static public AnalysisException createAnalysisExceptionWithOptimisticLockKey(Analysis analysis, OptimisticLockException oe) {
        AnalysisException ae = new AnalysisException(KEY_ANALYSIS_OPTIMISTIC_LOCK);
        ae.analysis=analysis;
        return ae;
    }

    static public AnalysisException createAnalysisExceptionWithTxRetryRollback() {
        AnalysisException ae = new AnalysisException(KEY_TX_RETRY_ROLLBACK);
        return ae;
    }

    static public AnalysisException createWithDbCheckConstraintKey(Analysis analysis, Throwable cause) {
        AnalysisException ae = new AnalysisException(KEY_ANALYSIS_NAME_EXISTS, cause);
        ae.analysis = analysis;
        return ae;
    }
}
