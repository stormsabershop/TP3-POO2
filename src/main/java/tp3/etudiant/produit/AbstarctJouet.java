package tp3.etudiant.produit;

import tp3.application.AbstractProduit;

public abstract class AbstarctJouet extends AbstractProduit {

    public AbstarctJouet(String nom, int prix) {
        super(nom, prix);
    }

    @Override
    public int getNumeroCategorie() {
        return 0;
    }

    @Override
    public abstract String decrit();


}