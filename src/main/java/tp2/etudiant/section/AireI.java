package tp2.etudiant.section;

import tp2.application.AbstractProduit;
import tp2.echange.Descriptible;
import tp2.etudiant.boite.Boite;

import java.io.Serializable;
import java.util.Collection;

public interface AireI extends Descriptible, Serializable {
    Collection<AbstractProduit> retireProduits(Collection<AbstractProduit> items);

    Collection<AbstractProduit> getAllProduits();

    //PLace tous les produits possibles. Retourne ceux qui n'ont pas pu être placés.
    Collection<AbstractProduit> placerProduits(Boite produits);
    boolean placerProduits(Collection<AbstractProduit> produits);


    void viderAire();

    void gereSurplus(Collection<AbstractProduit> restant);


}
