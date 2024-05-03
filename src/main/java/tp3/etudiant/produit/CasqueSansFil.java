package tp3.etudiant.produit;

public class CasqueSansFil extends Casque {
    public CasqueSansFil(String nom, double prix) {
        super(nom, prix);
    }

    @Override
    public String decrit() {
        return toString();
    }

    @Override
    public String toString() {
        return "Casque sans fil : Categorie= " + getNumeroCategorie() + ", Nom= " + getNom() + ", Prix" + getPrix();
    }
}
