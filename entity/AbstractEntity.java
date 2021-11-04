package pl.lodz.p.it.spjava.e11.sa.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@MappedSuperclass
public abstract class AbstractEntity {

    protected static final long serialVersionUID = 1L;

    protected abstract Object getId();

    protected abstract Object getBusinessKey();

    @NotNull
    @Version
    @Getter
    @Column(name = "VERSION", nullable = false)
    private long version;
    

    @NotNull
    @Getter
    @Column(name = "LAST_MODIFIED", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModified;

   

    @PrePersist
    @PreUpdate
    public void updateLastModified() {
        this.lastModified = new Date();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[id=" + getId() + ", key=" + getBusinessKey() + ", version=" + version + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }

        if (this.getClass().isAssignableFrom(obj.getClass())) {
            return this.getBusinessKey().equals(((AbstractEntity) obj).getBusinessKey());
        } else {
            return false;
        }

    }

    @Override
    public int hashCode() {
        return getBusinessKey().hashCode();
    }
}
