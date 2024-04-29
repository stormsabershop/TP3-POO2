package tp3.etudiant.produit;

import tp3.application.AbstractProduit;

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
