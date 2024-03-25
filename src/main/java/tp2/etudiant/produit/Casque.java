package tp2.etudiant.produit;
import tp2.application.AbstractProduit;

public class Casque extends AbstractProduit {
    public Casque(String nom) {
        super(nom);
    }

    @Override
    public int getNumeroCategorie() {
        return 0;
    }

    @Override
    public String decrit() {
        return toString();
    }

    @Override
    public String toString() {
        return "Casque{}";
    }
}