/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package t.claud.tpbanquetclaudio.jsf;

import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import t.claud.tpbanquetclaudio.entity.CompteBancaire;
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
    
    @Transactional
    public String transferer() throws Exception{
        CompteBancaire sourceCompte = gc.getCompte(idSource);
        CompteBancaire destCompte =  gc.getCompte(idDest);
        
        int newSourceSolde = sourceCompte.getSolde()-montant;
        int newDestSolde = destCompte.getSolde()+montant;
        if(newSourceSolde<0){
            throw new Exception("Solde source insufisant !");
        }
        
        sourceCompte.setSolde(newSourceSolde);
        destCompte.setSolde(newDestSolde);
        
        gc.updateCompte(sourceCompte);
        gc.updateCompte(destCompte);
        
        System.out.println("La transfert entre le client "+idSource+" et le client "+idDest+" avec une somme de "+montant+" effectuer ");
        return "listeComptes";
    }
    
}
