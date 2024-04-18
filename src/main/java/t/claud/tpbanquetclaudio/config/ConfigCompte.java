/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package t.claud.tpbanquetclaudio.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContext;
import jakarta.transaction.Transactional;
import t.claud.tpbanquetclaudio.entity.CompteBancaire;
import t.claud.tpbanquetclaudio.service.GestionnaireCompte;

/**
 * Configuration Bean CDI
 *
 * @author PC
 */
@ApplicationScoped
public class ConfigCompte {

    @Inject
    GestionnaireCompte gc;

    @Transactional
    public void init(
            @Observes
            @Initialized(ApplicationScoped.class) ServletContext context) { 
        long nbCompteDB = gc.nbComptes();   
        if (nbCompteDB <= 0) {
            gc.creerCompte(new CompteBancaire("John Lennon", 150000));
            gc.creerCompte(new CompteBancaire("Paul McCartney", 950000));
            gc.creerCompte(new CompteBancaire("Ringo Starr", 20000));
            gc.creerCompte(new CompteBancaire("Georges Harrisson", 100000));
        }
    }

}
