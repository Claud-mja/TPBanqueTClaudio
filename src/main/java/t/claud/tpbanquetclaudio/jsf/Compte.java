/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package t.claud.tpbanquetclaudio.jsf;

import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import t.claud.tpbanquetclaudio.entity.CompteBancaire;
import t.claud.tpbanquetclaudio.jsf.util.Util;
import t.claud.tpbanquetclaudio.service.GestionnaireCompte;

/**
 *
 * @author PC
 */
@Named(value = "compte")
@RequestScoped
public class Compte {
    
    @Inject
    GestionnaireCompte gc;
    
    private String nomTitulaire;
    private int solde;
    
    /**
     * Creates a new instance of Compte
     */
    public Compte() {
    }

    public String getNomTitulaire() {
        return nomTitulaire;
    }

    public void setNomTitulaire(String nomTitulaire) {
        this.nomTitulaire = nomTitulaire;
    }

    public int getSolde() {
        return solde;
    }

    public void setSolde(int solde) {
        this.solde = solde;
    }
    
    public String inserer(){
        CompteBancaire c = gc.getCompteByName(nomTitulaire);
        
        if(c!=null){
            Util.messageErreur("Compte bancaire avec nom "+nomTitulaire+" existe dejà !", "Compte bancaire existant !", "form:nom");
            return null;
        }
        
        if(solde<=0){
            Util.messageErreur("Le solde doit imperativement être superieur à 1 !", "Solde bancaire incorect ! ", "form:solde");
            return null;
        }
        c = new CompteBancaire(nomTitulaire , solde);
        gc.creerCompte(c);
        
        Util.addFlashInfoMessage("La création du compte de "+c.getNom()+" effectué avec une Solde de "+c.getSolde());
        return "listeComptes?faces-redirect=true";
    }
    
}
