package tp3.application;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import tp3.echange.UI;
import tp3.etudiant.Magasin;
import tp3.etudiant.client.Achat;
import tp3.echange.Descriptible;
import tp3.echange.Modele;
import tp3.etudiant.boite.Boite;
import tp3.etudiant.section.AireI;
import tp3.etudiant.section.Entrepot;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class MagasinController implements UI {


    public static final String NOM_CLIENT_DEFAUT = "Capitaine";
    private String clientActuel = NOM_CLIENT_DEFAUT;
    private final ProduitCreator produitCreator = new ProduitCreator();
    private Modele modele = new Magasin();
    private SectionController sectionController = null;


    @FXML
    private TreeView<Class> produitCreatorTreeView;

    @FXML
    private ListView<AbstractProduit> produitExistantCreationListView;

    @FXML
    private ListView<AbstractProduit> produitDisponibleListView;


    @FXML
    private Slider nombreCommandeSlider;

    @FXML
    private Label nombreCommandeLabel;

    @FXML
    private DatePicker dateCommandeDatePicker;

    @FXML
    private TreeView<Descriptible> rayonsSectionsTreeView;

    @FXML
    private TreeView<Descriptible> rayonsAchatTreeView;

    @FXML
    private TextArea chariteTextArea;

    @FXML
    private ListView<Boite> entrepotListView;

    @FXML
    private ListView<AbstractProduit> panierListView;

    @FXML
    private DatePicker dateAchatPicker;

    @FXML
    private TableColumn<Boite, String> infosColumn;

    @FXML
    private TableColumn<Boite, Integer> numberColumn;
    @FXML
    private TableColumn<Boite, List<AbstractProduit>> contenuColumn;

//    @FXML
//    private TableView<Boite> livraisonTableView;

    @FXML
    private Label rabaisLabel;

    @FXML
    private Slider rabaisSlider;


    @FXML
    private TextField nomClientTextField;


    @FXML
    private ComboBox<String> nomClientComboBox;

    @FXML
    private VBox produitGenerateurVBox;

    @FXML
    private TextArea aProposTextArea;

    @FXML
    void effacerCategorieSelectionnees(ActionEvent event) {
        List<AbstractProduit> listAEffacer = new ArrayList<>(produitExistantCreationListView.getSelectionModel().getSelectedItems());
        produitExistantCreationListView.getItems().removeAll(listAEffacer);
        produitExistantCreationListView.refresh();
        produitDisponibleListView.getItems().removeAll(listAEffacer);
        produitDisponibleListView.refresh();
    }

    @FXML
    void effacerToutesLesCategories(ActionEvent event) {
        produitExistantCreationListView.getItems().clear();
        produitExistantCreationListView.refresh();
        produitDisponibleListView.getItems().clear();
        produitDisponibleListView.refresh();
    }


    @FXML
    void commanderItems(ActionEvent event) throws CloneNotSupportedException {

        int nombreDemande = (int) nombreCommandeSlider.getValue();

        Collection<AbstractProduit> produitsACommander = produitDisponibleListView.getSelectionModel().getSelectedItems();
        Collection<Boite> commande = new ArrayList<>();

        int nombrePleine = nombreDemande / 10;
        int nombreRestant = nombreDemande % 10;

        if (produitsACommander.size() > 0) {

            prepareCommande(produitsACommander, nombrePleine, commande, nombreRestant);

            int nombreBoiteEnTrop = modele.recevoirCommande(commande);
            if (nombreBoiteEnTrop > 0) {
                alerte("Il y avait " + nombreBoiteEnTrop + " boîte(s) en trop");
            }
            metAJourEntrepot();
            metAJourSectionsTrees();
            metAJourCharite();

        } else {
            alerte("Il faut sélectionner au moins un produit");
        }

    }

    private void prepareCommande(Collection<AbstractProduit> produitsACommander, int nombrePleine, Collection<Boite> commande, int nombreRestant) throws CloneNotSupportedException {
        for (AbstractProduit produit : produitsACommander) {
            //boites pleines
            for (int i = 0; i < nombrePleine; i++) {
                commande.add(empacteProduits(produit, 10));
            }
            //restant
            if (nombreRestant > 0) {
                commande.add(empacteProduits(produit, nombreRestant));
            }

        }
    }

    private Boite empacteProduits(AbstractProduit produit, int nombre)
            throws CloneNotSupportedException {
        Boite retBoite = new Boite(1, -1);
        for (int j = 0; j < nombre; j++) {
            //AbstractProduit
            retBoite.ajouteProduit((AbstractProduit) produit.clone());
        }
        return retBoite;
    }

    private static void alerte(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void placerItemsDansSection(ActionEvent event) {
        ObservableList<Boite> selectedProduits = entrepotListView.getSelectionModel().getSelectedItems();
        Descriptible sectionVisee = rayonsSectionsTreeView.getSelectionModel().getSelectedItem().getValue();

        if (selectedProduits.size() == 0 || sectionVisee == null) {
            alerte("Vous devez choisir une section et au moins un produit");
        } else {
            modele.placerProduits((Collection<Boite>) selectedProduits, (AireI) sectionVisee);
            metAJourEntrepot();
            metAJourSectionsTrees();
            metAJourCharite();
        }
    }


    @FXML
    void mettreDansPanier(ActionEvent event) {

        ObservableList<TreeItem<Descriptible>> treeItemsVersPanier = rayonsAchatTreeView.getSelectionModel().getSelectedItems();
        Collection<AbstractProduit> itemsVersPanier = treeItemsVersPanier.stream()
                .flatMap(item -> item.getValue() instanceof AbstractProduit ?
                        List.of(item).stream() :
                        item.getChildren().stream())
                .map(i -> (AbstractProduit) i.getValue())
                .collect(Collectors.toList());

        ///on place les items sélectionnés dans le panier
        modele.mettreDansPanier(itemsVersPanier);

        //on retire les items sélectionnés de la section qui les contient
        treeItemsVersPanier.stream().forEach(item -> {
            if (item.getValue() instanceof AireI) {

                //tous les enfants si parent sélectionné
                removeFromSection(item, null);
            } else {  //c'est un produit

                //seul les enfants sélectionnés
                removeFromSection(item.getParent(), itemsVersPanier);
            }

        });

        metAJourSectionsTrees();
        metAJourPanier();
    }

    private static void removeFromSection(TreeItem<Descriptible> item, Collection<AbstractProduit> itemsVersPanier) {
        Collection<AbstractProduit> produits = item.getChildren().stream().map(noeud -> (AbstractProduit) noeud.getValue()).collect(Collectors.toList());
        AireI aireActuelle = (AireI) item.getValue();
        if (itemsVersPanier != null) {
            produits.retainAll(itemsVersPanier);

        }
        aireActuelle.retireProduits(produits);
    }

    @FXML
    void acheterPanier(ActionEvent event) {
        LocalDateTime dateTime = LocalDateTime.of(dateAchatPicker.getValue(), LocalTime.now());
        Achat achat = modele.acheterPanier(nomClientTextField.getText(), dateTime, rabaisSlider.getValue());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Achat confirmé");

        alert.setContentText(achat.imprimeFacture());
        alert.showAndWait();

        metAJourPanier();
//        metAJourLivraison();

    }


    @FXML
    void selectionneClient(ActionEvent event) {
        clientActuel = nomClientTextField.getText();
        if (!nomClientComboBox.getItems().contains(clientActuel)) {
            nomClientComboBox.getItems().add(clientActuel);
        }
        nomClientComboBox.getSelectionModel().select(nomClientComboBox.getItems().indexOf(clientActuel));

    }

    @FXML
    void selectionneClientAvecCombo(ActionEvent event) {
        clientActuel = nomClientComboBox.getSelectionModel().getSelectedItem();
        nomClientTextField.setText(clientActuel);
    }


    @FXML
    void retirerSelection(ActionEvent event) {
        modele.retirerDuPanier(panierListView.getSelectionModel().getSelectedItems());
        metAJourPanier();
        metAJourSectionsTrees();
        metAJourCharite();
    }

    @FXML
    void viderPanier(ActionEvent event) {
        modele.retirerDuPanier(panierListView.getItems());
        metAJourPanier();
        metAJourSectionsTrees();
        metAJourCharite();
    }


    @FXML
    public void initialize() {
        dateAchatPicker.setValue(LocalDate.now());

        // Gérer les sélections multiples
        produitExistantCreationListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        entrepotListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        produitDisponibleListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        rayonsAchatTreeView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        panierListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        rayonsSectionsTreeView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        prepareCreateurProduits();


        TreeItem<Descriptible> root = new TreeItem<>(new Descriptible() {
            @Override
            public String decrit() {
                return toString();
            }

            @Override
            public String toString() {
                return "Magasin";
            }
        });

        //connecte label et slider pour le nobre de commandes
        nombreCommandeLabel.textProperty().bind(
                Bindings.format("%.0f", nombreCommandeSlider.valueProperty())
        );


//        numberColumn.setCellValueFactory(
//                boite -> new ReadOnlyObjectWrapper<>(
//                        boite.getValue().getNumeroEmballage()));
//        contenuColumn.setCellValueFactory(
//                boite -> new ReadOnlyObjectWrapper<List<AbstractProduit>>(
//                        boite.getValue().getContenu()));
//        infosColumn.setCellValueFactory(
//                boiteItem -> {
//                    Boite boite = boiteItem.getValue();
//                    return new ReadOnlyObjectWrapper<String>(
//                            boite instanceof Descriptible ? boite.decrit() : boite.toString());
//                }
//        );

//        contenuColumn.setCellFactory(this::loadContenuCell);

        entrepotListView.setCellFactory(c -> new BoiteListCell());
        produitDisponibleListView.setCellFactory(c -> new ProduitListCell());
        panierListView.setCellFactory(c -> new ProduitListCell());

        rayonsSectionsTreeView.setCellFactory((a) -> {
            return new DescriptibleTreeCell();
        });
        rayonsAchatTreeView.setCellFactory((a) -> {
            return new DescriptibleTreeCell();
        });

        rayonsSectionsTreeView.setRoot(root);
        rayonsAchatTreeView.setRoot(root);

        //On synchronise les 2 listes de porduits à commander
        //produitExistantCreationListView.setItems(produitDisponibleListView.getItems());

        // Client de base
        nomClientComboBox.getItems().add(clientActuel);
        nomClientTextField.setText(clientActuel);

        //lier le rabais avec son slider
        rabaisLabel.textProperty().bind(rabaisSlider.valueProperty().asString("%.0f %%"));


        String aProposTexte = modele.init(this);
        aProposTextArea.setWrapText(true);
        aProposTextArea.setText(aProposTexte);

        metAJourSectionsTrees();
        metAJourEntrepot();
        metAJourCharite();

    }


    private void prepareCreateurProduits() {
        produitCreatorTreeView.setRoot(produitCreator.buildAllProduitTreeItem());
        produitCreatorTreeView.setCellFactory(p -> new ProduitClassTreeCell());
        produitCreatorTreeView.setShowRoot(true);

    }

    private TableCell<Boite, List<AbstractProduit>> loadContenuCell(TableColumn<Boite, List<AbstractProduit>> boiteListTableColumn) {
        return new TableContenuCell();
    }

    private void metAJourEntrepot() {

        entrepotListView.getItems().clear();
        Entrepot entrepot = modele.getEntrepot();
        if (entrepot != null) {
            Boite[][] boites = entrepot.getBoites2D();
            for (int i = 0; i < boites.length; i++) {
                entrepotListView.getItems().addAll(Arrays.asList(boites[i]));
            }
        }
    }

    private void metAJourSectionsTrees() {
//        TreeItem<Descriptible> root = rayonsSectionsTreeView.getRoot();
        TreeItem<Descriptible> root = new TreeItem<>(new Descriptible() {
            @Override
            public String decrit() {
                return toString();
            }

            @Override
            public String toString() {
                return "Magasin";
            }
        });
        root.setExpanded(true);
        rayonsSectionsTreeView.setRoot(root);
        rayonsAchatTreeView.setRoot(root);
//        initializer root avec un nouveau noeud pour enlever les cochonnerires
        Collection<AireI> sections = modele.getAllSections();
        if (sections != null) {
            for (AireI section : sections) {

                TreeItem<Descriptible> sectionItem = getSectionItemFor(section);
                sectionItem.getChildren().clear();
                sectionItem.setExpanded(true);
                Collection<AbstractProduit> produits = section.getAllProduits();
                if (produits != null) {
                    for (AbstractProduit produit : produits) {
                        TreeItem<Descriptible> produitItem = getProduitfor(sectionItem, produit);
                        //sectionItem.getChildren().add(produitItem);
                    }
                }
            }
        }
    }

    private void metAJourCharite() {
        chariteTextArea.clear();
        chariteTextArea.appendText(modele.getCharite().decrit());
    }

    private void metAJourPanier() {
        panierListView.getItems().clear();
        panierListView.getItems().addAll(modele.getContenuPanier());
    }

    private TreeItem<Descriptible> getProduitfor(TreeItem<Descriptible> sectionItem, AbstractProduit produit) {
        assert sectionItem != null;
        assert produit != null;

        TreeItem<Descriptible> root = rayonsSectionsTreeView.getRoot();
        TreeItem retItem =
                sectionItem.getChildren().stream().filter(item -> produit.equals(item.getValue()))
                        .findFirst().orElseGet(() -> {
                            TreeItem<Descriptible> item = new TreeItem<Descriptible>((Descriptible) produit);
                            sectionItem.getChildren().add(item);
                            return item;
                        });
        return retItem;
    }

    private TreeItem<Descriptible> getSectionItemFor(AireI section) {
        assert section != null;
        TreeItem<Descriptible> root = rayonsSectionsTreeView.getRoot();
        TreeItem retItem =
                root.getChildren().stream().filter(item -> section.equals(item.getValue()))
                        .findFirst().orElseGet(() -> {
                            TreeItem<Descriptible> item = new TreeItem<Descriptible>(section);
                            root.getChildren().add(item);
                            return item;
                        });
        return retItem;
    }


//    private void metAJourLivraison() {
//        this.livraisonTableView.getItems().clear();
//        Collection<Boite> livraisons = modele.getLivraisons();
//        this.livraisonTableView.getItems().addAll(livraisons);
//    }


    @FXML
    void creeNouveauProduit(ActionEvent event) {

        Class selectedClass = produitCreatorTreeView.getSelectionModel().getSelectedItem().getValue();
        if (!Modifier.isAbstract(selectedClass.getModifiers())) {
            AbstractProduit nouveauProduit = produitCreator.createNouveauProduit(selectedClass);
            produitExistantCreationListView.getItems().add(nouveauProduit);
            produitDisponibleListView.getItems().add(nouveauProduit);
            produitDisponibleListView.refresh();
            produitExistantCreationListView.refresh();
        } else {
            alerte("Vous devez choisir une catégorie de produit concrète");
        }

    }

    @FXML
    void reconstruitMagasin(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(this.panierListView.getScene().getWindow());
        if (file != null) {
            modele.reconstruit(file);

        }
        metAJourEntrepot();
        metAJourSectionsTrees();
        metAJourCharite();
    }

    @FXML
    void archiveMagasin(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(this.panierListView.getScene().getWindow());
        if (file != null) {
            modele.archive(file);
        }
    }

    @FXML
    void viderMagasin(ActionEvent event) {
        modele.viderMagasin();
        metAJourEntrepot();
        metAJourSectionsTrees();
        metAJourCharite();
//        metAJourLivraison();
    }

    @FXML
    void montreDetailsSectionSelectionnee(ActionEvent event) {
        TreeItem<Descriptible> selectedItem = rayonsSectionsTreeView.getSelectionModel().getSelectedItem();
        Descriptible selectedDescriptible = selectedItem.getValue();

        if (selectedDescriptible != null &&
                selectedDescriptible instanceof AireI) {
            AireI selectedAire = (AireI) selectedDescriptible;
            sectionController.afficheSection(selectedAire);
        } else {
            alerte("Vous devez sélectionner ue section");
        }

    }

    public void setSectionController(SectionController sectionController) {
        this.sectionController = sectionController;
    }


    @Override
    public List<AbstractProduit> getProduitsDisponibles() {
        return new ArrayList<>(produitExistantCreationListView.getItems());
    }

    @Override
    public void setProduitsDisponibles(List<AbstractProduit> produitsDisponibles) {
        produitExistantCreationListView.setItems(FXCollections.observableList(produitsDisponibles));
        produitDisponibleListView.setItems(FXCollections.observableList(produitsDisponibles));

        // on ne doit pas oublier les compteur statiques


    }

    public void stop() {
        modele.stop();
    }
}

