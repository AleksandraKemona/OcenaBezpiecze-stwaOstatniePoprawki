package pl.lodz.p.it.spjava.e11.sa.exception;

import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import lombok.Getter;
import pl.lodz.p.it.spjava.e11.sa.entity.Cosmetic;



/**
 *
 */
public class CosmeticException extends AppBaseException {

    static final public String KEY_COSMETIC_NAME_EXISTS = "error.cosmetics.db.constraint.uniq.cosmeticName";
    static final public String KEY_COSMETIC_OPTIMISTIC_LOCK = "error.cosmetics.optimisticlock";
    static final public String KEY_COSMETIC_NOT_READ_FOR_EDITION = "error.cosmetic.cosmeticNotDownoladedForEdition";
    static final public String KEY_COSMETIC_NOT_FOUND = "error.cosmetic.cosmeticNotFound";
    static final public String KEY_COSMETIC_ALREADY_CHANGED = "error.cosmetic.cosmeticAlreadyChanged";
    static final public String KEY_NAME_DOES_NOT_EXIST = "error.cosmetic.nameDoseNotExist";
    static final public String KEY_NAME_DOES_EXIST = "error.cosmetic.nameDoesExist";
    static final public String KEY_COSMETIC_CHOOSEN = "error.cosmetic.alreadyChoosen";
    
    
    
    

    
    
    private Cosmetic cosmetic;
    
    @Getter
    private String name;
    
    @Getter
    private Long id;

    public CosmeticException(String message) {
        super(message);
    }

    private CosmeticException(String message, Throwable cause) {
        super(message, cause);
    }


    public Cosmetic getCosmetic() {
        return cosmetic;
    }

    public void setCosmetic(Cosmetic cosmetic) {
        this.cosmetic = cosmetic;
    }
    
    

    static public CosmeticException createCosmeticExceptionWithOptimisticLockKey(Cosmetic cosmetic, OptimisticLockException oe) {
        CosmeticException ce = new CosmeticException(KEY_COSMETIC_OPTIMISTIC_LOCK, oe);
        ce.setCosmetic(cosmetic);
        return ce;
    }
    
    static public CosmeticException createCosmeticDoesNotExistException(String name, NoResultException nre) {
        CosmeticException ce = new CosmeticException(KEY_NAME_DOES_NOT_EXIST, nre);
        ce.name = name;
        return ce;
    }
    static public CosmeticException createCosmeticDoesNotExistException(Long id, NoResultException nre) {
        CosmeticException ce = new CosmeticException(KEY_NAME_DOES_NOT_EXIST, nre);
        ce.id = id;
        return ce;
    }
    
    static public CosmeticException createCosmeticDoesExistException(String name) {
        CosmeticException ce = new CosmeticException(KEY_NAME_DOES_EXIST);
        ce.name = name;
        return ce;
    }
    
    public static CosmeticException createExceptionWrongState(Cosmetic cosmetic) {
        CosmeticException ae = new CosmeticException(KEY_COSMETIC_NOT_READ_FOR_EDITION);
        ae.cosmetic=cosmetic;
        return ae;
    }
    
    public static CosmeticException createCosmeticAlreadyChoosenException(Cosmetic cosmetic) {
        CosmeticException ae = new CosmeticException(KEY_COSMETIC_CHOOSEN);
        ae.cosmetic=cosmetic;
        return ae;
    }
    
    public static CosmeticException createExceptionWrongStateDeletion(Cosmetic cosmetic) {
        CosmeticException ae = new CosmeticException(KEY_COSMETIC_NOT_FOUND);
        ae.cosmetic=cosmetic;
        return ae;
    }

    static public CosmeticException createCosmeticExceptionWithTxRetryRollback() {
        CosmeticException ce = new CosmeticException(KEY_TX_RETRY_ROLLBACK);
        return ce;
    }

    static public CosmeticException createWithDbCheckConstraintKey(Cosmetic cosmetic, Throwable cause) {
        CosmeticException ce = new CosmeticException(KEY_COSMETIC_NAME_EXISTS, cause);
        ce.cosmetic = cosmetic;
        return ce;
    }
    
    
}
