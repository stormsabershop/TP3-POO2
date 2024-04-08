package tp2.etudiant.section;

import tp2.application.AbstractProduit;
import tp2.etudiant.boite.Boite;

import java.util.ArrayList;
import java.util.Collection;

public class Presentoires implements AireI {
    Collection<AbstractProduit> produits = new ArrayList<>();

    public Presentoires() {
        this.produits = produits;
    }
    @Override
    public String decrit() {
        return "salut comment ca va";
    }

    @Override
    public Collection<AbstractProduit> retireProduits(Collection<AbstractProduit> items) {
        produits.removeAll(items);
        return produits;
    }

    @Override
    public Collection<AbstractProduit> getAllProduits() {
        return produits;
    }

    @Override
    public Collection<AbstractProduit> placerProduits(Boite produitsPourAjouter) {
        produits.addAll(produitsPourAjouter.getContenu());
        return produits;
    }

    @Override
    public boolean placerProduits(Collection<AbstractProduit> produitsPourAjouter) {
        return produits.addAll(produitsPourAjouter);
    }

    @Override
    public void viderAire() {

    }

    @Override
    public void gereSurplus(Collection<AbstractProduit> restant) {

    }

    @Override
    public String toString() {
        return "Presentoires:\n{" +
                "produits=" + produits +
                '}';
    }
}
