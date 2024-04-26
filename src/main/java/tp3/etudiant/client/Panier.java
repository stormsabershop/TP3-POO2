package tp3.etudiant.client;

import tp3.application.AbstractProduit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Panier {

    private List<AbstractProduit> produits;

    public Panier() {
        this.produits = new ArrayList<>()  ;
    }

    public void ajouteProduit(AbstractProduit produit) {
        produits.add(produit);
    }

    public List<AbstractProduit> getAchats() {
        return produits;
    }


    public void vide() {
        Iterator<AbstractProduit> iterator = produits.iterator();
        while (iterator.hasNext()){
            produits.remove(iterator.next());
        }
    }

    public boolean retireProduit(AbstractProduit produit){
        return produits.remove(produit);
    }
}
