package tp3.etudiant.produit;

import tp3.application.AbstractProduit;

import java.io.Serializable;

public abstract class AbstractEcouteurs extends AbstractProduit {

    public AbstractEcouteurs(String nom, double prix) {
        super(nom, prix);
    }

    @Override
    public int getNumeroCategorie() {
        return 2;
    }

    @Override
    public abstract String decrit();


}
