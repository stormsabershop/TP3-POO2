package tp2.etudiant.produit;

import tp2.application.AbstractProduit;

public abstract class AbstractEcouteurs extends AbstractProduit {
    public AbstractEcouteurs(String nom, int prix) {
        super(nom, prix);
    }

    @Override
    public int getNumeroCategorie() {
        return 2;
    }

    @Override
    public abstract String decrit();

}
