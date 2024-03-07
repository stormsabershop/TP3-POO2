package tp2.application;

import javafx.scene.control.TreeCell;

import java.lang.reflect.Modifier;

public class ProduitClassTreeCell extends TreeCell<Class> {
    public void updateItem(Class produitClass, boolean empty) {
        super.updateItem(produitClass, empty);
        if (produitClass != null && !empty) {
            setText(produitClass.getSimpleName());
            if(Modifier.isAbstract(produitClass.getModifiers())){
                setStyle("-fx-text-fill: lightgray");
            }else{
                setStyle("-fx-text-fill: darkblue");
            }
        } else {
            setItem(null);
//            setGraphic(null);
            setText(null);
        }
    }
}
