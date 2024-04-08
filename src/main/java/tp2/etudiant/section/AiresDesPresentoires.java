package tp2.etudiant.section;

import tp2.application.AbstractProduit;
import tp2.etudiant.boite.Boite;

import java.util.Collection;

public class AiresDesPresentoires implements AireI {
    @Override
    public String decrit() {
        return "aire des presentoires";
    }

    @Override
    public Collection<AbstractProduit> retireProduits(Collection<AbstractProduit> items) {
        return null;
    }

    @Override
    public Collection<AbstractProduit> getAllProduits() {
        return null;
    }

    @Override
    public Collection<AbstractProduit> placerProduits(Boite produits) {
        return null;
    }

    @Override
    public boolean placerProduits(Collection<AbstractProduit> produits) {
        return false;
    }

    @Override
    public void viderAire() {

    }

    @Override
    public void gereSurplus(Collection<AbstractProduit> restant) {

    }
}
