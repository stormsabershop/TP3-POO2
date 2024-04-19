package tp2.etudiant.produit;

import tp2.application.AbstractProduit;

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
