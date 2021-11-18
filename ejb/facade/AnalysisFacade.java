/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.spjava.e11.sa.ejb.facade;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import org.eclipse.persistence.exceptions.DatabaseException;
import pl.lodz.p.it.spjava.e11.sa.entity.Analysis;
import pl.lodz.p.it.spjava.e11.sa.entity.Category;
import pl.lodz.p.it.spjava.e11.sa.exception.AnalysisException;
import pl.lodz.p.it.spjava.e11.sa.exception.AppBaseException;

/**
 *
 * @author Ola
 */
@Stateless
public class AnalysisFacade extends AbstractFacade<Analysis> {

    @PersistenceContext(unitName = "SafetyAssessementPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AnalysisFacade() {
        super(Analysis.class);
    }
    
    public List<Analysis> findAnalysisByCategoryId(long categoryId) {
        List<Analysis> demandedAnalysisList= new ArrayList<>();
        TypedQuery<Analysis> tq = em.createNamedQuery("Analysis_Demands.findByCategoryId", Analysis.class);
        tq.setParameter("categoryId", categoryId);
        Analysis analysis = tq.getSingleResult();
        demandedAnalysisList.add(analysis);
        return demandedAnalysisList;
    }
    
    public Analysis findAnalysisByName(String name) {
        TypedQuery<Analysis> tq = em.createNamedQuery("Analysis.findByName", Analysis.class);
        tq.setParameter("name", name);
        Analysis analysis = tq.getSingleResult();
        em.refresh(analysis);
        return analysis;
    }
    
    public Analysis findById(Long analysisId) throws AppBaseException{
        TypedQuery<Analysis> tq = em.createNamedQuery("Analysis.findById", Analysis.class);
        tq.setParameter("analysisId", analysisId);
        Analysis analysis = tq.getSingleResult();
        em.refresh(analysis);
        return analysis;
    }
    
    @Override
    public void edit(Analysis entity) throws AppBaseException {
        try {
            super.edit(entity);
            em.flush();
        } catch (OptimisticLockException oe) {
            throw AnalysisException.createAnalysisExceptionWithOptimisticLockKey(entity, oe);
        } catch (PersistenceException|DatabaseException ex) {
            final Throwable cause = ex.getCause();
            final Throwable causeCause = ex.getCause().getCause();
            if (cause instanceof DatabaseException
                    && causeCause.getMessage().contains(DB_UNIQUE_CONSTRAINT_FOR_ANALYSIS_NAME)) {
                throw AnalysisException.createWithDbCheckConstraintKey(entity, cause);
            }
        }
    }
    
    @Override
    public void create(Analysis entity) throws AppBaseException {
        try {
            super.create(entity);
            em.flush();
        } catch (PersistenceException|DatabaseException ex) {
            final Throwable cause = ex.getCause();
            final Throwable causeCause = ex.getCause().getCause();
            
            if (cause instanceof DatabaseException
                    && causeCause.getMessage().contains(DB_UNIQUE_CONSTRAINT_FOR_ANALYSIS_NAME)) {
                throw AnalysisException.createWithDbCheckConstraintKey(entity, cause);
            }
        }
    }
    
    }
