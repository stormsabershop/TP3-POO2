package tp3.application;

import javafx.scene.control.ListCell;
import tp3.echange.Descriptible;
import tp3.etudiant.boite.Boite;

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
