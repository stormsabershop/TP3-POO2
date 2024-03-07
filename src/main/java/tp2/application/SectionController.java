package tp2.application;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import tp2.echange.Descriptible;
import tp2.etudiant.section.AireI;

import java.util.List;
import java.util.Map;

public class SectionController {

    @FXML
    private TableView<Map.Entry<String, List<Descriptible>>> sectionTableView;

    @FXML
    private Label sectionTitleLabel;
    public static final int NOMBRE_COLONNES = 25;
    private AireI section;

    public void afficheSection(AireI section) {

        this.section = section;

        afficheSection();
    }

    private void afficheSection() {
       // Object contenu = this.section.getContent();
        Map<String, List<Descriptible>> map = null;
        sectionTitleLabel.setText(this.section.decrit());
        sectionTitleLabel.getScene().getWindow();

        // on uniformise les données.
//        if (contenu instanceof Boite[][]) {
//            Boite[][] boites = (Boite[][]) contenu;
//            map = new HashMap<>();
//            for (int i = 0; i < boites.length; i++) {
//                //todo rendre
//                map.put("rangée-" + i, new ArrayList<>(Arrays.asList(boites[i])));
//            }
//        } else if (contenu instanceof Map) {
//            map = (Map<String, List<Descriptible>>) contenu;
//            //msd pour n'avoir que des list et non des sets
//            map = map.entrySet().stream().collect(Collectors.toMap(
//                    e -> e.getKey(),
//                    e -> new ArrayList<>(e.getValue())));
//        }

        // On change les données
        sectionTableView.getItems().clear();
        sectionTableView.getItems().addAll(map.entrySet());
        sectionTableView.refresh();
    }

//    private String findProduitsName(Boite[] boites) {
//        String retVal = "";
//        for (int i = 0; i < boites.length && retVal.length() == 0; i++) {
//            if (boites[i] != null) {
//                List<AbstractProduit> produitsActuel = boites[i].getContenu();
//                if (produitsActuel.size() > 0) {
//                    AbstractProduit produit = produitsActuel.get(0);
//                    retVal = produit.getNom() + "-" + produit.getNumProduit();
//                }
//
//            }
//        }
//        return retVal;
//    }


    @FXML
    void metAJourButton(ActionEvent event) {
        afficheSection();
    }

    @FXML
    void initialize() {
        sectionTableView.getColumns().clear();
        //on crée les lignes du tableau
        TableColumn<Map.Entry<String, List<Descriptible>>, String> colonneNom = new TableColumn<>();
        colonneNom.setText("Nom");
        colonneNom.setCellValueFactory((c) -> {
            return new ReadOnlyObjectWrapper<>(c.getValue().getKey());
        });
        sectionTableView.getColumns().add(colonneNom);

        for (int i = 0; i < NOMBRE_COLONNES; i++) {
            TableColumn<Map.Entry<String, List<Descriptible>>, String> colonneActuelle = new TableColumn<>();
            colonneActuelle.setText("" + i);
            int tempI = i;
            colonneActuelle.setCellValueFactory((c) -> {

                String value = c.getValue().getValue() != null &&
                        c.getValue().getValue().size() > tempI &&
                        c.getValue().getValue().get(tempI) != null ?
                        c.getValue().getValue().get(tempI).decrit() :
                        "";
                return new ReadOnlyObjectWrapper<>(value);
            });
            sectionTableView.getColumns().add(colonneActuelle);
        }
    }


}
