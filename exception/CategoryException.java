package pl.lodz.p.it.spjava.e11.sa.exception;

import javax.persistence.OptimisticLockException;
import pl.lodz.p.it.spjava.e11.sa.entity.Account;
import pl.lodz.p.it.spjava.e11.sa.entity.Category;
import pl.lodz.p.it.spjava.e11.sa.entity.Substrate;
import pl.lodz.p.it.spjava.e11.sa.entity.Toxicology;



/**
 *
 */
public class CategoryException extends AppBaseException {

static final public String KEY_CATEGORY_NAME_EXISTS = "error.categories.db.constraint.uniq.substrateName";
    static final public String KEY_CATEGORY_OPTIMISTIC_LOCK = "error.categories.optimisticlock";
    static final public String KEY_CATEGORY_NOT_READ_FOR_EDITION = "error.categories.categoryNotDownoladedForEdition";
    static final public String KEY_CATEGORY_NOT_READ_FOR_COSMETIC = "error.categories.categoryNotDownoladedForCosmetic";
    static final public String KEY_CATEGORY_ANALYSIS_DEMANDED = "error.categories.analysisDemanded";
    

    public CategoryException(String message) {
        super(message);
    }

    private CategoryException(String message, Throwable cause) {
        super(message, cause);
    }

    private Category category;

    public Category getCategory() {
        return category;
    }
    
    public static CategoryException createExceptionWrongState(Category category) {
        CategoryException ce = new CategoryException(KEY_CATEGORY_NOT_READ_FOR_EDITION);
        ce.category=category;
        return ce;
    }
    
    public static CategoryException createExceptionWrongStateForCosmetic(Category category) {
        CategoryException ce = new CategoryException(KEY_CATEGORY_NOT_READ_FOR_COSMETIC);
        ce.category=category;
        return ce;
    }
    
    public static CategoryException createExceptionDemandsAnalysis(Category category) {
        CategoryException ce = new CategoryException(KEY_CATEGORY_ANALYSIS_DEMANDED);
        ce.category=category;
        return ce;
    }

    
    
    static public CategoryException createCategoryExceptionWithOptimisticLockKey(Category category, OptimisticLockException oe) {
        CategoryException ce = new CategoryException(KEY_CATEGORY_OPTIMISTIC_LOCK);
        ce.category=category;
        return ce;
    }

    static public CategoryException createCategoryExceptionWithTxRetryRollback() {
        CategoryException ce = new CategoryException(KEY_TX_RETRY_ROLLBACK);
        return ce;
    }

    static public CategoryException createWithDbCheckConstraintKey(Category category, Throwable cause) {
        CategoryException ce = new CategoryException(KEY_CATEGORY_NAME_EXISTS, cause);
        ce.category = category;
        return ce;
    }
}
