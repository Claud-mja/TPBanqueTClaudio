/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package t.claud.tpbanquetclaudio.jsf;

import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import java.io.Serializable;
import java.util.List;
import t.claud.tpbanquetclaudio.entity.CompteBancaire;
import t.claud.tpbanquetclaudio.jsf.util.Util;
import t.claud.tpbanquetclaudio.service.GestionnaireCompte;

/**
 *
 * @author PC
 */
@Named(value = "listeComptes")
@ViewScoped
public class ListeComptes implements Serializable {

    private List<CompteBancaire> bancaires;

    @Inject
    GestionnaireCompte gc;

    /**
     * Creates a new instance of ListeComptes
     */
    public ListeComptes() {
    }

    public List<CompteBancaire> getAllComptes() {
        if (bancaires == null) {
            bancaires = gc.getAllComptes();
        }
        return bancaires;
    }
    
    public String supprimer(CompteBancaire cb){
        gc.deleteCompte(cb);
        Util.addFlashInfoMessage("Compte de " + cb.getNom() + " supprimé");
        return "listeComptes?faces-redirect=true";
    }

}
