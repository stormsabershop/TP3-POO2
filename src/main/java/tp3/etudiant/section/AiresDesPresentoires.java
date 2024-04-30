package tp3.etudiant.section;

import tp3.application.AbstractProduit;
import tp3.etudiant.boite.Boite;

import java.util.*;

public class AiresDesPresentoires implements AireI, Lists {
    Map<String, List<AbstractProduit>> contenuPresentoires;

    public AiresDesPresentoires() {
        this.contenuPresentoires = new HashMap<>();
    }

    @Override
    public String decrit() {
        return "aire des presentoires";
    }

    @Override
    public Collection<AbstractProduit> retireProduits(Collection<AbstractProduit> items) {
        Collection<AbstractProduit> produitsARetirer = new HashSet<>();


        for (AbstractProduit item : items) {

            List<AbstractProduit> produitsParType = contenuPresentoires.get(item.getNom());


            if (produitsParType != null && produitsParType.contains(item)) {
                produitsParType.remove(item);
                produitsARetirer.add(item);

                if (produitsParType.isEmpty()) {
                    contenuPresentoires.remove(item.getNom());
                }
            }
        }

        return produitsARetirer;
    }

    @Override
    public Collection<AbstractProduit> getAllProduits() {
        Collection<AbstractProduit> tousProduits = new ArrayList<>();


        for (List<AbstractProduit> produitsParType : contenuPresentoires.values()) {

            tousProduits.addAll(produitsParType);
        }

        return tousProduits;
    }

    @Override
    public Collection<AbstractProduit> placerProduits(Boite produits) {
        Collection<AbstractProduit> produitsAajouter = new HashSet<>();


        for (AbstractProduit produit : produits.getContenu()) {
            List<AbstractProduit> produitsParType = contenuPresentoires.computeIfAbsent(produit.getNom(), k -> new ArrayList<>());


            produitsParType.add(produit);
            produitsAajouter.add(produit);
        }

        return produitsAajouter;
    }

    @Override
    public boolean placerProduits(Collection<AbstractProduit> produits) {
        boolean produitsPlaces = false;

        for (AbstractProduit produit : produits) {
            List<AbstractProduit> produitsParType = contenuPresentoires.computeIfAbsent(produit.getNom(), k -> new ArrayList<>());

            produitsParType.add(produit);
            produitsPlaces = true;
        }

        return produitsPlaces;
    }

    @Override
    public void viderAire() {
        contenuPresentoires.clear();

    }

    @Override
    public void gereSurplus(Collection<AbstractProduit> restant) {
        ((ChariteI) produitsListCharite).donneProduits(restant);
    }

    @Override
    public boolean estPresent(AbstractProduit produit) {
        boolean estla;
        if (contenuPresentoires.containsKey(produit.getNom())) {
            estla = true;
        } else {
            estla = false;
        }
        return estla;
    }
}
