/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package t.claud.tpbanquetclaudio.jsf;

import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import java.io.Serializable;
import t.claud.tpbanquetclaudio.entity.CompteBancaire;
import t.claud.tpbanquetclaudio.jsf.util.Util;
import t.claud.tpbanquetclaudio.service.GestionnaireCompte;

/**
 *
 * @author PC
 */
@Named(value = "compte")
@ViewScoped
public class Compte implements Serializable{
    
    @Inject
    GestionnaireCompte gc;
    
    private CompteBancaire compteBancaire;
    private int idCompte;
    
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

    public CompteBancaire getCompteBancaire() {
        return compteBancaire;
    }

    public void setCompteBancaire(CompteBancaire compteBancaire) {
        this.compteBancaire = compteBancaire;
    }


    public int getIdCompte() {
        return idCompte;
    }

    public void setIdCompte(int idCompte) {
        this.idCompte = idCompte;
    }
    
    public void loadCompte(){
        this.compteBancaire = gc.getCompte(idCompte);
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
    
    public String modifier(){
        
        if(compteBancaire.getSolde()<=0){
            Util.messageErreur("Le solde doit imperativement être superieur à 1 !", "Solde bancaire incorect ! ", "form:solde");
            return null;
        }
        
        gc.updateCompte(compteBancaire);
        Util.addFlashInfoMessage("Modification de nom compte en "+compteBancaire.getNom()+" effectué");
        return "listeComptes?faces-redirect=true";
    }
    
}
