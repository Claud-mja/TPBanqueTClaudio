/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package t.claud.tpbanquetclaudio.jsf;

import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.persistence.OptimisticLockException;
import java.io.Serializable;
import t.claud.tpbanquetclaudio.entity.CompteBancaire;
import t.claud.tpbanquetclaudio.jsf.util.Util;
import t.claud.tpbanquetclaudio.service.GestionnaireCompte;

/**
 *
 * @author PC
 */
@Named(value = "mouvement")
@ViewScoped
public class RetraitVerseCompte implements Serializable {

    private int idCompte;
    private int montant;
    private CompteBancaire compte;
    private String typeMouv;

    @Inject
    GestionnaireCompte gc;

    /**
     * Creates a new instance of RetraitVerseCompte
     */
    public RetraitVerseCompte() {
    }

    public int getIdCompte() {
        return idCompte;
    }

    public void setIdCompte(int idCompte) {
        this.idCompte = idCompte;
    }

    public void loadCompte() {
        compte = gc.getCompte(idCompte);
    }

    public CompteBancaire getCompte() {
        return compte;
    }

    public void setCompte(CompteBancaire compte) {
        this.compte = compte;
    }

    public String getTypeMouv() {
        return typeMouv;
    }

    public void setTypeMouv(String typeMouv) {
        this.typeMouv = typeMouv;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public String mouvementer() {
        boolean isError = this.checkTransaction();
        if (isError) {
            return null;
        }

        try {
            switch (typeMouv) {
                case "Retrait" ->
                    gc.retrait(compte, montant);
                case "Versement" ->
                    gc.versement(compte, montant);
                default -> {
                    Util.messageErreur("Type mouvement non definit !", "Type mouvement non definit !", "form:mouv");
                    return null;
                }
            }
            Util.addFlashInfoMessage(typeMouv + " de " + montant + " effectué du compte de " + this.compte.getNom());
            return "listeComptes?faces-redirect=true";
        } catch (OptimisticLockException ex) {
            Util.messageErreur("Le compte de " + compte.getNom()
                    + " a été modifié ou supprimé par un autre utilisateur !");
            return null;
        }
    }

    public boolean checkTransaction() {
        boolean error = false;
        if (typeMouv == null) {
            Util.messageErreur("Type mouvement non definit !", "Type mouvement non definit !", "form:mouv");
            error = true;
        } else if (montant <= 0) {
            Util.messageErreur("Montant de la transaction doit être superieur à 0 !", "Montant incorect !", "form:montant");
            error = true;
        } else if (this.compte.getSolde() < montant) {
            if (typeMouv.equals("Retrait")) {
                Util.messageErreur("Montant à retirer insuffisant !", "Solde insuffisant !", "form:montant");
                error = true;
            }
        }
        return error;
    }

}
