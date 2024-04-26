package tp3.etudiant.section;

import tp3.application.AbstractProduit;
import tp3.etudiant.boite.Boite;

import java.util.Collection;

public class Vrac implements AireI, Lists{

    @Override
    public String decrit() {
        return "vrac ici";
    }

    @Override
    public Collection<AbstractProduit> retireProduits(Collection<AbstractProduit> items) {
        produitsListVrac.removeAll(items);
        return produitsListVrac;
    }

    @Override
    public Collection<AbstractProduit> getAllProduits() {
        return produitsListVrac;
    }

    @Override
    public Collection<AbstractProduit> placerProduits(Boite produits) {
        produitsListVrac.addAll(produits.getContenu());
        return produitsListVrac;
    }

    @Override
    public boolean placerProduits(Collection<AbstractProduit> produits) {
        return produitsListVrac.addAll(produits);
    }

    @Override
    public void viderAire() {
        produitsListVrac.clear();

    }

    @Override
    public void gereSurplus(Collection<AbstractProduit> restant) {
        ((ChariteI) produitsListCharite).donneProduits(restant);
    }
}
