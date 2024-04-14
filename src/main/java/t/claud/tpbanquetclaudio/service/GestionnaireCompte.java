/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package t.claud.tpbanquetclaudio.service;

import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
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
        String s = "select e from CompteBancaire e";
        TypedQuery<CompteBancaire> query
                = em.createQuery(s, CompteBancaire.class);
        return query.getResultList();
    }

    public long nbComptes() {
        String req = "SELECT count(c) FROM CompteBancaire AS c";
        TypedQuery<Long> query = em.createQuery(req, Long.class);
        return query.getSingleResult();
    }

    public CompteBancaire getCompte(int idCompte) {
        String req = "SELECT c FROM CompteBancaire c WHERE c.id = :idCompte";
        TypedQuery<CompteBancaire> query = em.createQuery(req, CompteBancaire.class);
        query.setParameter("idCompte", idCompte);
        try {
            return query.getSingleResult();
        } catch (NoResultException ex) {
            return null; // Gérer le cas où aucun compte n'est trouvé avec cet identifiant
        }
    }

    @Transactional
    public CompteBancaire updateCompte(CompteBancaire compteBancaire) {
        return em.merge(compteBancaire);
    }

    @Transactional
    public void transfert(CompteBancaire source, CompteBancaire destination, int montant) {
        this.updateCompte(source);
        this.updateCompte(destination);
    }

    public CompteBancaire getCompteByName(String nom) {
        String req = "SELECT c FROM CompteBancaire c WHERE c.nom = :nom";
        TypedQuery<CompteBancaire> query = em.createQuery(req, CompteBancaire.class);
        query.setParameter("nom", nom);
        try {
            return query.getSingleResult();
        } catch (NoResultException ex) {
            return null; // Gérer le cas où aucun compte n'est trouvé avec cet identifiant
        }
    }
    
    @Transactional
    public void deleteCompte(CompteBancaire compte){
        em.remove(em.merge(compte));
    }
}
