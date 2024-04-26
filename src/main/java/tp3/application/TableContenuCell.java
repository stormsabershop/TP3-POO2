package tp3.application;

import javafx.beans.binding.Bindings;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import tp3.etudiant.boite.Boite;

import java.util.List;

public class TableContenuCell extends TableCell<Boite, List<AbstractProduit>> {
    private ListView<AbstractProduit> root = new ListView<>();

    public TableContenuCell() {
        root.setCellFactory(l->new ProduitListCell());
    }

    public void updateItem(List<AbstractProduit> produits, boolean empty) {
        super.updateItem(produits, empty);
        if (produits != null && !empty) {
            setItem(produits);
            root.getItems().clear();
            root.getItems().addAll(produits);
            //root.setPrefSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
            //root.setMaxSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
            root.prefHeightProperty().bind(Bindings.size(root.getItems()).multiply(30));
            setGraphic(root);
        } else {
            setItem(null);
            setGraphic(null);
            setText(null);
        }

    }
}
