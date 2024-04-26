package tp3.etudiant.produit;

public class Figurine extends AbstarctJouet {


    public Figurine(String nom, int prix) {
        super(nom, prix);
    }


    @Override
    public String decrit() {
        return toString();
    }

    @Override
    public String toString() {
        return "Figurine : Categorie= " + getNumeroCategorie() + ", Nom= " + getNom() + ", Prix= " + getPrix();
    }


}