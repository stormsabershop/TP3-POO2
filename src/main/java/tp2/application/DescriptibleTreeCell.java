package tp2.application;

import javafx.scene.control.TreeCell;
import tp2.echange.Descriptible;

public class DescriptibleTreeCell extends TreeCell<Descriptible> {


    public void updateItem(Descriptible descriptible, boolean empty) {
        super.updateItem(descriptible, empty);
        if (descriptible != null && !empty) {
            setText(descriptible.decrit());
        } else {
            setItem(null);
            setGraphic(null);
            setText(null);
        }

    }
}
