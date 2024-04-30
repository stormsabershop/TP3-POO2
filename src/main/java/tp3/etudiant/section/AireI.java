package tp3.etudiant.section;

import tp3.application.AbstractProduit;
import tp3.echange.Descriptible;
import tp3.etudiant.boite.Boite;

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

    public boolean estPresent(AbstractProduit produit);


}
