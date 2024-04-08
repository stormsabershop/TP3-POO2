package tp2.etudiant.produit;
import tp2.application.AbstractProduit;

public class Casque extends AbstractProduit {


    public Casque(String nom,int prix) {
        super(nom,prix);
    }

    @Override
    public String decrit() {
        return toString();
    }

    @Override
    public String toString() {
        return "Casque: Categorie= " + getNumeroCategorie() +", Nom= "+getNom() +", Prix"+getPrix();
    }

    @Override
    public int getNumeroCategorie() {
        return 0;
    }


}