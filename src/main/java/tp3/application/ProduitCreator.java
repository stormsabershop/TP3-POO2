package tp3.application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tp3.etudiant.fichiers.Historique;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

public class ProduitCreator {

    private static final String DEFAULT_VALUE = "1";
    private static int lastNumber = 0;

    private static int nomCompteur = 0;
    private Historique historique = new Historique();

    public ProduitCreator() {
    }

    private Class getPrimitive(Class baseType) {
        return Array.get(Array.newInstance(baseType, 1), 0).getClass();
    }

    public AbstractProduit createNouveauProduit(Class classeRequise) {
        AbstractProduit retProduit;
        Class<AbstractProduit> abstractProduitClass = AbstractProduit.class;

        Constructor constructor = null;

        constructor = classeRequise.getDeclaredConstructors()[0];// on tient pour acquis un seul constructeur
        Parameter[] parameters = constructor.getParameters();

//        if (parameters[0].isNamePresent()) {
//            System.out.println(parameters[0].getName());
//        }

        Object[] parametersValues = convertStringsToValues(
                parameters,
                demandeValeursUtilisateur(parameters, classeRequise.getSimpleName()));
        historique.ajouterEvenement("Ouverture de l’application");

        try {
            retProduit = (AbstractProduit) constructor.newInstance(parametersValues);
            historique.ajouterEvenement("Ouverture de l’application");

        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }


        return retProduit;
    }

    private Object[] convertStringsToValues(Parameter[] parameters, String[] valeursTexte) {
        Object[] retValues = new Object[parameters.length];
        try {
            for (int i = 0; i < parameters.length; i++) {
                Class classeActuelle = parameters[i].getType();
                if (classeActuelle != String.class) {//conversion en emballeur
                    Method method = getPrimitive(classeActuelle).getDeclaredMethod("valueOf", String.class);
                    retValues[i] = method.invoke(null, valeursTexte[i]);
                } else {
                    retValues[i] = valeursTexte[i];
                }
            }
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return retValues;
    }

    public TreeItem<Class> buildAllProduitTreeItem() {
        TreeItem<Class> root = new TreeItem<>(AbstractProduit.class);
        Map<Class, Set<Class>> classMap = new HashMap<>();

        Set<Class> allProduitClasses = findAllClassesUsingClassLoader("tp3.etudiant.produit");
        buildClassHierarchyMap(allProduitClasses, classMap);

        ajouteTreeNodeRec(classMap, AbstractProduit.class, root);

        return root;
    }

    private void ajouteTreeNodeRec(Map<Class, Set<Class>> classMap, Class actualClass, TreeItem<Class> actualNode) {
        Set<Class> actualSet = classMap.get(actualClass);
        for (Class classe : actualSet) {
            TreeItem<Class> newNode = new TreeItem<>(classe);
            actualNode.getChildren().add(newNode);
            ajouteTreeNodeRec(classMap, classe, newNode);
        }
    }

    private void buildClassHierarchyMap(Set<Class> allProduitClasses, Map<Class, Set<Class>> classMap) {
        for (Class classe : allProduitClasses) {
            classMap.putIfAbsent(classe, new HashSet<Class>());
            Class superclass = classe.getSuperclass();

            while (!superclass.equals(Object.class)
            ) {
                classMap.putIfAbsent(superclass, new HashSet<>());
                classMap.get(superclass).add(classe);
                classe = superclass;
                superclass = superclass.getSuperclass();
            }
        }
    }

    private Set<Class> findAllClassesUsingClassLoader(String packageName) {
        InputStream stream = ClassLoader.getSystemClassLoader()
                .getResourceAsStream(packageName.replaceAll("[.]", "/"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        return reader.lines()
                .filter(line -> line.endsWith(".class"))
                .map(line -> getClass(line, packageName))
                .filter(this::isAbstractProduit)
                .collect(Collectors.toSet());
    }


    private Class getClass(String className, String packageName) {
        try {
            return Class.forName(packageName + "."
                    + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            // handle the exception
        }
        return null;
    }

//    public AbstractProduit construitProduit(Class produitClass) {
//        Constructor constructor = produitClass.getDeclaredConstructors()[0];
//        Class[] parametersTypes = constructor.getParameterTypes();
//
//        Parameter[] parameters = constructor.getParameters();
//        //parameters.s
//        for (Parameter parameter : parameters) {
//            parameter.getName();
//            parameter.getType();
//        }
//
//        return null;
//    }

    private boolean isAbstractProduit(Class classe) {
        boolean retVal = false;
        Class classeActuelle = classe;
        while (classeActuelle != null &&
                !classeActuelle.equals(Object.class)) {
            if (classeActuelle.equals(AbstractProduit.class)) {
                retVal = true;
            }
            classeActuelle = classeActuelle.getSuperclass();
        }
        return retVal;
    }

    String[] demandeValeursUtilisateur(Parameter[] caracteristiques, String nom) {
        String[] retVal = new String[caracteristiques.length];
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        VBox root = new VBox();
        //List<HBox> caracteristiqueHBoxes = ajouteBoites(caracteristiques);
        createExpendablePane(caracteristiques, root, nom);

        alert.getDialogPane().setExpandableContent(root);
        alert.setContentText("Saisir les valeurs suivantes");

        Optional<ButtonType> reponse = alert.showAndWait();
        if (reponse.isPresent()) {
            if (reponse.get().equals(ButtonType.OK)) {
                int i = 0;
                for (Node node : root.getChildren()) {
                    HBox currentHBox = (HBox) node;
                    //String caracName = ((Label) currentHBox.getChildren().get(0)).getText();
                    String val = ((TextField) currentHBox.getChildren().get(1)).getText();
                    retVal[i++] = val;
                }
            }
        }

        return retVal;
    }

    static void createExpendablePane(Parameter[] caracteristiques, VBox root, String nom) {
        for (Parameter caracteristique : caracteristiques) {
            HBox hbox = new HBox();
            Label label = new Label(caracteristique.getName().replaceAll(
                    String.format("%s|%s|%s",
                            "(?<=[A-Z])(?=[A-Z][a-z])",
                            "(?<=[^A-Z])(?=[A-Z])",
                            "(?<=[A-Za-z])(?=[^A-Za-z])"
                    ),
                    " "
            ) + "  ");
            Label typeLabel = new Label("  (" + caracteristique.getType().getName() + ")");
            TextField textField = new TextField();
            if (caracteristique.getName().equals("nom")) {
                textField.setText(nom + nomCompteur++);

            } else {
                textField.setText(DEFAULT_VALUE);

            }
            hbox.getChildren().addAll(label, textField, typeLabel);
            hbox.setAlignment(Pos.BASELINE_CENTER);
            hbox.setPadding(new Insets(20));
            root.getChildren().add(hbox);
        }
    }

}