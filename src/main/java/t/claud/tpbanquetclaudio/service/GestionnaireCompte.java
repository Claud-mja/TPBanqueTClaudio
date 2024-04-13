/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package t.claud.tpbanquetclaudio.service;

import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;
import t.claud.tpbanquetclaudio.entity.CompteBancaire;

@DataSourceDefinition(
        className = "com.mysql.cj.jdbc.MysqlDataSource",
        name = "java:app/jdbc/banque",
        serverName = "localhost",
        portNumber = 3360,
        user = "root", 
        password = "root", 
        databaseName = "banque",
        properties = {
            "useSSL=false",
            "allowPublicKeyRetrieval=true",
            "driverClass=com.mysql.cj.jdbc.Driver"
        }
)

/**
 * Façade pour gérer les comptes.
 *
 * @author PC
 */
@ApplicationScoped
public class GestionnaireCompte {

    @PersistenceContext(unitName = "banquePU")
    private EntityManager em;

    /**
     * Methode pour faire l'insertion d'une compte
     *
     * @param c
     */
    @Transactional
    public void creerCompte(CompteBancaire c) {
        em.persist(c);
    }

    public List<CompteBancaire> getAllComptes() {
        //Query query = em.createNamedQuery("Comptebancaire.findAll");
        String s = "select e from Compte as e";
        TypedQuery<CompteBancaire> query
                = em.createQuery(s, CompteBancaire.class);
        return query.getResultList();
    }
    
    
    public long nbComptes(){
        String req = "SELECT count(c) FROM CompteBancaire AS c";
        TypedQuery<Long> query = em.createQuery(req , Long.class);
        return query.getSingleResult();
    }

}
