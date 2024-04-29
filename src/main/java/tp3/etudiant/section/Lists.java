package tp3.etudiant.section;

import tp3.application.AbstractProduit;

import java.util.*;

public interface Lists {


//    Collection<AbstractProduit> produitsListVrac = new ArrayList<>();
List<AbstractProduit> produits = new ArrayList<>();

    Map<String, Set<AbstractProduit>> contenu = new HashMap<>();

    Collection<AbstractProduit> produitsListCharite = new ArrayList<>();
}
