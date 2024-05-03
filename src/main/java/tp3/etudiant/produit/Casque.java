package tp3.etudiant.produit;

public class Casque extends AbstractEcouteurs {


    public Casque(String nom, double prix) {
        super(nom, prix);
    }

    @Override
    public String decrit() {
        return toString();
    }

    @Override
    public String toString() {
        return "Casque: Categorie= " + getNumeroCategorie() + ", Nom= " + getNom() + ", Prix" + getPrix();
    }


}