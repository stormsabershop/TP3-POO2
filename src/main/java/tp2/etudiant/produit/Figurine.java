package tp2.etudiant.produit;
import tp2.application.AbstractProduit;
public class Figurine extends AbstractProduit {
    public Figurine(String nom) {
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
        return "Figurine{}";
    }
}