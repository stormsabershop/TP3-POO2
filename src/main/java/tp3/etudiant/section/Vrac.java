package tp3.etudiant.section;

import tp3.application.AbstractProduit;
import tp3.etudiant.boite.Boite;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Vrac implements AireI, Lists{
    @Override
    public String decrit() {
        return "vrac ici";
    }


    @Override
    public Collection<AbstractProduit> retireProduits(Collection<AbstractProduit> items) {

        Collection<AbstractProduit> produitsARetirer = new HashSet<>();

        for (AbstractProduit item : items) {
            Set<AbstractProduit> produitsParType = contenu.get(item.getNom());

            if (produitsParType != null && produitsParType.contains(item)) {
                produitsParType.remove(item);
                produitsARetirer.add(item);

            }
        }

        return produitsARetirer;
    }

    @Override
    public Collection<AbstractProduit> getAllProduits() {
        Collection<AbstractProduit> tousProduits = new ArrayList<>();


        for (Set<AbstractProduit> produitsParType : contenu.values()) {

            tousProduits.addAll(produitsParType);
        }

        return tousProduits;
    }

    @Override
    public Collection<AbstractProduit> placerProduits(Boite produits) {

        Collection<AbstractProduit> produitsAajouter = new HashSet<>();


        for (AbstractProduit produit : produits.getContenu()) {

            Set<AbstractProduit> produitsParType = contenu.computeIfAbsent(produit.getNom(), k -> new HashSet<>());


            produitsParType.add(produit);
            produitsAajouter.add(produit);
        }

        return produitsAajouter;
    }

    @Override
    public boolean placerProduits(Collection<AbstractProduit> produits) {
        boolean produitsAjoutes = false;

        for (AbstractProduit produit : produits) {
            Set<AbstractProduit> produitsParType = contenu.computeIfAbsent(produit.getNom(), k -> new HashSet<>());

            produitsParType.add(produit);
            produitsAjoutes = true;
        }

        return produitsAjoutes;
    }

    @Override
    public void viderAire() {
        contenu.clear();

    }

    @Override
    public void gereSurplus(Collection<AbstractProduit> restant) {
        ((ChariteI) produitsListCharite).donneProduits(restant);
    }
}
