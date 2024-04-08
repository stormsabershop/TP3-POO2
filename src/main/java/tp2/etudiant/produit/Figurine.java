package tp2.etudiant.produit;
import tp2.application.AbstractProduit;
public class Figurine extends AbstractProduit {


    public Figurine(String nom, int prix) {
        super(nom,prix);
    }


    @Override
    public String decrit() {
        return toString();
    }

    @Override
    public String toString() {
        return "Figurine : Categorie= " + getNumeroCategorie() +", Nom= "+getNom()+ ", Prix= "+getPrix();
    }

    @Override
    public int getNumeroCategorie() {
        return 0;
    }

}