package tp3.etudiant.produit;

import tp3.application.AbstractProduit;

import java.io.Serializable;

public abstract class AbstarctJouet extends AbstractProduit {

    public AbstarctJouet(String nom, double prix) {
        super(nom, prix);

    }

    @Override
    public int getNumeroCategorie() {
        return 0;
    }

    @Override
    public abstract String decrit();

}
