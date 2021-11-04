package pl.lodz.p.it.spjava.e11.sa.exception;

import javax.persistence.OptimisticLockException;
import pl.lodz.p.it.spjava.e11.sa.entity.Substrate;



/**
 *
 */
public class SubstrateException extends AppBaseException {

    static final public String KEY_SUBSTRATE_NAME_EXISTS = "error.substrates.db.constraint.uniq.substrateName";
    static final public String KEY_SUBSTRATE_OPTIMISTIC_LOCK = "error.substrates.optimisticlock";
    static final public String KEY_SUBSTRATE_NOT_READ_FOR_EDITION = "error.substrates.substrateNotDownoladedForEdition";
    static final public String KEY_SUBSTRATE_NOT_READ_FOR_COSMETIC = "error.substrates.substrateNotDownoladedForCosmetic";


    public static SubstrateException createExceptionWrongState(Substrate substrate) {
        SubstrateException se = new SubstrateException(KEY_SUBSTRATE_NOT_READ_FOR_EDITION);
        se.substrate=substrate;
        return se;
    }
    
    public static SubstrateException createExceptionWrongStateForCosmetic(Substrate substrate) {
        SubstrateException se = new SubstrateException(KEY_SUBSTRATE_NOT_READ_FOR_COSMETIC);
        se.substrate=substrate;
        return se;
    }

    
    
    static public SubstrateException createSubstrateExceptionWithOptimisticLockKey(Substrate substrate, OptimisticLockException oe) {
        SubstrateException se = new SubstrateException(KEY_SUBSTRATE_OPTIMISTIC_LOCK, oe);
        se.substrate = substrate;
        return se;
    }
    
    
    


    public SubstrateException() {
    }

    
    public SubstrateException(String message) {
        super(message);
    }

    private SubstrateException(String message, Throwable cause) {
        super(message, cause);
    }

    private Substrate substrate;

    public Substrate getSubstrate() {
        return substrate;
    }

    static public SubstrateException createSubstrateExceptionWithTxRetryRollback() {
        SubstrateException se = new SubstrateException(KEY_TX_RETRY_ROLLBACK);
        return se;
    }

    static public SubstrateException createWithDbCheckConstraintKey(Substrate substrate, Throwable cause) {
        SubstrateException se = new SubstrateException(KEY_SUBSTRATE_NAME_EXISTS, cause);
        se.substrate = substrate;
        return se;
    }
}
