package tp2.etudiant.section;

import tp2.application.AbstractProduit;

import java.util.Collection;

public interface ChariteI {
    void donneProduits(Collection<AbstractProduit> produits);
    public void donneProduit(AbstractProduit produit);
}
