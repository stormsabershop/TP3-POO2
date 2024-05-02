package tp3.etudiant.client;

import tp3.application.AbstractProduit;
import tp3.etudiant.section.AireI;

import java.util.*;

public class Panier {

    private List<AbstractProduit> produits; // essaye d'utiliser la map et supprimer la lsist
    private Map<AbstractProduit, AireI> panierMap;

    public Panier() {
        this.produits = new ArrayList<>();
        this.panierMap = new HashMap<>();
    }

    public void ajouteProduit(AbstractProduit produit, AireI aireDuProduit) {
        assert aireDuProduit !=null : "Aire d'origine st null";
        panierMap.putIfAbsent(produit, aireDuProduit);

        produits.add(produit);
    }

    public List<AbstractProduit> getAchats() {
        return produits;
    }


    public void vide() {
        produits.clear();

    }

    public boolean retireProduit(AbstractProduit produit) {
        panierMap.remove(produit);
        return produits.remove(produit);
    }

    public Map<AbstractProduit, AireI> getPanierMap() {
        return panierMap;
    }
}
