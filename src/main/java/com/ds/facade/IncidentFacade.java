/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ds.facade;

import com.ds.entity.Incident;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author adx_slevkov
 */
@Stateless
public class IncidentFacade extends AbstractFacade<Incident> {

    @PersistenceContext(unitName = "com.ds_mim_war_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IncidentFacade() {
        super(Incident.class);
    }

    public List findByDate(Date date) {
        return em.createQuery("SELECT i FROM Incident i WHERE i.incDate = :date")
                .setParameter("date", date)
                .getResultList();
    }

    public List findByDatePeriod(Date startDate, Date endDate) {
        return em.createQuery("SELECT i FROM Incident i WHERE i.incDate >= :startDate AND i.incDate <= :endDate")
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }

    public List findByIncNumber(String incident) {
        return em.createQuery("SELECT i FROM Incident i WHERE i.incNumber LIKE CONCAT('%',:incident,'%')")
                .setParameter("incident", incident)
                .getResultList();
    }

    public List findByMainNote(String mainNote) {
        return em.createQuery("SELECT i FROM Incident i WHERE i.incTitle LIKE CONCAT('%',:mainNote,'%')")
                .setParameter("mainNote", mainNote)
                .getResultList();
    }

    public List findByResolveNote(String resolveNote) {
        return em.createQuery("SELECT i FROM Incident i WHERE i.incDescription LIKE CONCAT('%',:resolveNote,'%')")
                .setParameter("resolveNote", resolveNote)
                .getResultList();
    }

}
