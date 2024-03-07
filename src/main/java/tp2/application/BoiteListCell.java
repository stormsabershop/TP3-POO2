package tp2.application;

import javafx.scene.control.ListCell;
import tp2.echange.Descriptible;
import tp2.etudiant.boite.Boite;

public class BoiteListCell extends ListCell<Boite> {

    public void updateItem(Boite boite, boolean empty) {
        super.updateItem(boite, empty);
        if (boite != null && !empty) {
            if (boite instanceof Descriptible) {
                Descriptible descriptible = (Descriptible) boite;
                setText(descriptible.decrit());
            } else {
                setText(boite.toString());
            }

        } else {
            setItem(null);
            setGraphic(null);
            setText(null);
        }

    }
}
