package tp3.etudiant.section;

import tp3.echange.Descriptible;

public class Presentoires extends AiresDesPresentoires implements Descriptible {

    public Presentoires() {
    }

    @Override
    public String decrit() {
        if (super.produitsList.isEmpty()) {
            return toString();
        } else return "Présentoires: \n";

    }

    @Override
    public String toString() {
        return "Presentoires:\n" +
                "(vide)";
    }
}
