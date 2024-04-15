package tp2.etudiant.section;

import tp2.application.AbstractProduit;

import java.util.ArrayList;
import java.util.Collection;

public interface Lists {

    Collection<AbstractProduit> produitsList = new ArrayList<>();
    Collection<AbstractProduit> produitsListVrac = new ArrayList<>();
    Collection<AbstractProduit> produitsListCharite = new ArrayList<>();
}
