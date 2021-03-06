/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ds.facade;

import com.ds.entity.System;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author adx_slevkov
 */
@Stateless
public class SystemFacade extends AbstractFacade<System> {

    @PersistenceContext(unitName = "com.ds_mim_war_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SystemFacade() {
        super(System.class);
    }
    
}
