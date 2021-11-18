/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.spjava.e11.sa.ejb.endpoint;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import pl.lodz.p.it.spjava.e11.sa.dto.AnalysisDTO;
import pl.lodz.p.it.spjava.e11.sa.dto.CategoryDTO;
import pl.lodz.p.it.spjava.e11.sa.ejb.facade.AnalysisFacade;
import pl.lodz.p.it.spjava.e11.sa.ejb.manager.AnalysisManager;
import pl.lodz.p.it.spjava.e11.sa.ejb.manager.CategoryManager;
import pl.lodz.p.it.spjava.e11.sa.entity.Analysis;
import pl.lodz.p.it.spjava.e11.sa.exception.AnalysisException;
import pl.lodz.p.it.spjava.e11.sa.exception.AppBaseException;
import pl.lodz.p.it.spjava.e11.sa.utils.converter.AnalysisConverter;

@Stateful
@RolesAllowed({"Assessor", "LabTechnician"})
public class AnalysisEndpoint implements Serializable {

    @EJB
    private AnalysisManager analysisManager;

    @EJB
    private CategoryManager categoryManager;

    @EJB
    private AnalysisFacade analysisFacade;

    @Resource(name = "txRetryLimit")
    private int txRetryLimit;

    private Analysis analysisState;

    @TransactionAttribute(TransactionAttributeType.NEVER)
    public void createAnalysis(AnalysisDTO newAnalysis) throws AppBaseException {
        Analysis analysis = new Analysis();
        analysis.setName(newAnalysis.getName());
        analysis.setMinimum(newAnalysis.getMinimum());
        analysis.setMaximum(newAnalysis.getMaximum());
        analysis.setStandard(newAnalysis.getStandard());

        boolean rollbackTX;
        int retryTXCounter = txRetryLimit;

        do {
            try {
                analysisManager.createAnalysis(analysis);
                rollbackTX = analysisManager.isLastTransactionRollback();
            } catch (AnalysisException ae) {
                throw ae;
            } catch (AppBaseException | EJBTransactionRolledbackException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy:"
                        + ex.getClass().getName());
                rollbackTX = true;
            }

        } while (rollbackTX && --retryTXCounter > 0);
        if (rollbackTX && retryTXCounter == 0) {
            throw AnalysisException.createAnalysisExceptionWithTxRetryRollback();
        }
    }

    public List<AnalysisDTO> listAllAnalysis() throws AppBaseException {
        return AnalysisConverter.createListAnalysisDTOFromEntity(analysisManager.downloadAllAnalysis());
    }

    public AnalysisDTO getAnalysisForEdition(AnalysisDTO analysisDTO) throws AppBaseException {
        analysisState = analysisFacade.findById(analysisDTO.getAnalysisId());
        return AnalysisConverter.createAnalysisDTOFromEntity(analysisState);
    }

    public void saveAnalysisAfterEdition(AnalysisDTO analysisDTO) throws AppBaseException {

        if (null == analysisState) {
            throw AnalysisException.createExceptionWrongState(analysisState);
        }
        analysisState.setName(analysisDTO.getName());
        analysisState.setMinimum(analysisDTO.getMinimum());
        analysisState.setMaximum(analysisDTO.getMaximum());
        analysisState.setStandard(analysisDTO.getStandard());
        boolean rollbackTX;
        int retryTXCounter = txRetryLimit;
        do {
            try {
                analysisManager.saveAnalysisAfterEdition(analysisState);
                rollbackTX = analysisManager.isLastTransactionRollback();
            } catch (AnalysisException ae) {
                throw ae;
            } catch (AppBaseException | EJBTransactionRolledbackException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy:"
                        + ex.getClass().getName());
                rollbackTX = true;
            }
        } while (rollbackTX && --retryTXCounter > 0);
        if (rollbackTX && retryTXCounter
                == 0) {
            throw AnalysisException.createAnalysisExceptionWithTxRetryRollback();
        }
    }

    public AnalysisDTO downloadAnalysisForDetails(AnalysisDTO detailedAnalysis) {
        return AnalysisConverter.createAnalysisDTOFromEntity(analysisManager.downloadAnalysisForDetails(detailedAnalysis.getAnalysisId()));
    }

    @TransactionAttribute(TransactionAttributeType.NEVER)
    public void chooseAnalsysis(final CategoryDTO categoryDTO) {
        boolean rollbackTX;
        int retryTXCounter = txRetryLimit;
        do {
            try {
                categoryManager.chooseAnalysis(categoryDTO);
                rollbackTX = categoryManager.isLastTransactionRollback();
            } catch (EJBTransactionRolledbackException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy: "
                        + ex.getClass().getName()
                        + " z komunikatem: " + ex.getMessage());
                rollbackTX = true;
            }
        } while (rollbackTX && --retryTXCounter > 0);
        if (rollbackTX && retryTXCounter == 0) {
            throw new IllegalStateException("przekroczono.liczbę.prób.odwołanych.transakcji");
        }
    }

}
