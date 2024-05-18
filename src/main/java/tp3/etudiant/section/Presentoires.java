package tp3.etudiant.section;

import tp3.echange.Descriptible;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class Presentoires extends AiresDesPresentoires implements Descriptible, Serializable {

    public Presentoires() {
    }

    @Override
    public String decrit() {
        if (super.contenuPresentoires.isEmpty()) {
            return toString();
        } else return "Présentoires: \n";

    }


    @Override
    public String toString() {
        return "Presentoires:\n" +
                "(vide)";
    }
}
