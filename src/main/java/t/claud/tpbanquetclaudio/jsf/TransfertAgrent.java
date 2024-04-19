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
@Named(value = "transfert")
@RequestScoped
public class TransfertAgrent {
    
    @Inject
    GestionnaireCompte gc;
    
    private int idSource;
    private int idDest;
    private int montant;

    /**
     * Creates a new instance of TransfertAgrent
     */
    public TransfertAgrent() {
    }
    
    public int getIdSource() {
        return idSource;
    }
    
    public void setIdSource(int idSource) {
        this.idSource = idSource;
    }
    
    public int getIdDest() {
        return idDest;
    }
    
    public void setIdDest(int idDest) {
        this.idDest = idDest;
    }
    
    public int getMontant() {
        return montant;
    }
    
    public void setMontant(int montant) {
        this.montant = montant;
    }
    
    public String transferer() {
        CompteBancaire sourceCompte = gc.getCompte(idSource);
        CompteBancaire destCompte = gc.getCompte(idDest);
        boolean error = this.checkTransfert(sourceCompte, destCompte);
        if (error) {
            return null;
        }
        gc.transfert(sourceCompte, destCompte, montant);
        
        Util.addFlashInfoMessage("Transfert de " + montant + " effectué de " + sourceCompte.getNom() + " vers " + destCompte.getNom());
        return "listeComptes?faces-redirect=true";
    }
    
    private boolean checkTransfert(CompteBancaire source, CompteBancaire dest) {
        boolean erreur = false;
        if (source == null || dest == null) {
            // Message d'erreur associé au composant source ; form:source est l'id client
            // si l'id du formulaire est "form" et l'id du champ de saisie de l'id de la source est "source"
            // dans la page JSF qui lance le transfert.
            if (source == null) {
                Util.messageErreur("Aucun compte avec cet id !", "Aucun compte avec cet id !", "form:source");
            }
            
            if (dest == null) {
                Util.messageErreur("Aucun compte avec cet id !", "Aucun compte avec cet id !", "form:dest");
            }
            erreur = true;
        } else {
            if (source.getSolde() < montant) {
                Util.messageErreur("Solde insufisant pour le transfert !", "Solde source insuficant !", "form:source");
                erreur = true;
            }
        }
        return erreur;
    }
    
}
