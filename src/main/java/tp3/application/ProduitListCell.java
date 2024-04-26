package tp3.application;

import javafx.scene.control.ListCell;
import tp3.echange.Descriptible;

public class ProduitListCell extends ListCell<AbstractProduit> {
    public void updateItem(AbstractProduit produit, boolean empty) {
        super.updateItem(produit, empty);
        if (produit != null && !empty) {
            if (produit instanceof Descriptible) {
                Descriptible descriptible = (Descriptible) produit;
                setText(descriptible.decrit());
            } else {
                setText(produit.toString());
            }

        } else {
            setItem(null);
            setGraphic(null);
            setText(null);
        }

    }
}
