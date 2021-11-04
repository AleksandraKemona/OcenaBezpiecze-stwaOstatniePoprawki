package pl.lodz.p.it.spjava.e11.sa.exception;

import pl.lodz.p.it.spjava.e11.sa.entity.Account;
import pl.lodz.p.it.spjava.e11.sa.entity.Substrate;
import pl.lodz.p.it.spjava.e11.sa.entity.Toxicology;



/**
 *
 */
public class ToxicologyException extends AppBaseException {

    static final public String KEY_TOXICOLOGY_NAME_EXISTS = "error.substrates.db.constraint.uniq.substrateName";

    public ToxicologyException(String message) {
        super(message);
    }

    private ToxicologyException(String message, Throwable cause) {
        super(message, cause);
    }

    private Toxicology toxicology;

    public Toxicology getToxicology() {
        return toxicology;
    }

    static public ToxicologyException createToxicologyExceptionWithTxRetryRollback() {
        ToxicologyException se = new ToxicologyException(KEY_TX_RETRY_ROLLBACK);
        return se;
    }

    static public ToxicologyException createWithDbCheckConstraintKey(Toxicology toxicology, Throwable cause) {
        ToxicologyException se = new ToxicologyException(KEY_TOXICOLOGY_NAME_EXISTS, cause);
        se.toxicology = toxicology;
        return se;
    }
}
