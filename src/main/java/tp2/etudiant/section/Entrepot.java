package tp2.etudiant.section;


import tp2.etudiant.boite.Boite;

import java.util.ArrayList;
import java.util.List;

public class Entrepot {
    public static final int NOMBRE_SECTION = 5;
    public static final int NOMBRE_TABLETTE = 5;
    public static final int NOMBRE_CATEGORIES = 3;
    private String sectionId = "entrepot";
    private Boite[][][] entreposage;//[rangee][section][tablette]->[categorie de produit][type de produit][boite de produit]

    public Entrepot() {
        entreposage = new Boite[NOMBRE_CATEGORIES]
                [NOMBRE_SECTION]
                [NOMBRE_TABLETTE];
    }

    public boolean entreposeBoite(Boite boite) {

        int categorie = boite.getNumeroCategorie();
        int numeroProduit = boite.getNumeroProduit();

        // Vérifier si la catégorie est valide
        if (categorie < 0 || categorie >= entreposage.length) {
            return false;
        }

        Boite[][] section = entreposage[categorie];

        // Parcourir toutes les sections de la catégorie pour trouver une section appropriée pour stocker la boîte
        for (int sectionIndex = 0; sectionIndex < section.length; sectionIndex++) {
            Boite[] tablettes = section[sectionIndex];
            // Vérifier si la section peut accueillir le produit
            boolean sectionFull = true;
            for (Boite b : tablettes) {
                if (b == null) {
                    sectionFull = false;
                    break;
                }
            }
            if (!sectionFull) {
                // Trouver la première tablette libre dans la section
                for (int tabletteIndex = 0; tabletteIndex < tablettes.length; tabletteIndex++) {
                    if (tablettes[tabletteIndex] == null) {
                        // Placer la boîte sur la tablette libre
                        tablettes[tabletteIndex] = boite;
                        return true; // Boîte placée avec succès
                    }
                }
            }
        }
        // Aucune section n'a pu accueillir la boîte
        return false;
    }

    public void retireBoite(Boite boite) {
        // Parcourir toutes les catégories
        for (int categorie = 0; categorie < entreposage.length; categorie++) {
            Boite[][] section = entreposage[categorie];
            // Parcourir toutes les sections de la catégorie
            for (int sectionIndex = 0; sectionIndex < section.length; sectionIndex++) {
                Boite[] tablettes = section[sectionIndex];
                // Parcourir toutes les tablettes de la section
                for (int tabletteIndex = 0; tabletteIndex < tablettes.length; tabletteIndex++) {
                    Boite currentBoite = tablettes[tabletteIndex];
                    // Si la boîte correspond à celle que nous voulons retirer
                    if (currentBoite != null && currentBoite.equals(boite)) {
                        // Retirer la boîte
                        tablettes[tabletteIndex] = null;
                        // Faire descendre les boîtes qui étaient au-dessus
                        descendreBoites(categorie, sectionIndex, tabletteIndex);
                        return; // Sortir de la méthode une fois que la boîte est retirée
                    }
                }
            }
        }

    }
    private void descendreBoites(int categorie, int sectionIndex, int tabletteIndex) { // modifier
        // Faire descendre les boîtes qui étaient au-dessus
        for (int i = sectionIndex; i < entreposage[categorie].length; i++) {
            // Si nous sommes à la dernière section, il n'y a pas de boîtes au-dessus à faire descendre
            if (i == entreposage[categorie].length - 1) {
                break;
            }
            Boite[] currentSection = entreposage[categorie][i];
            Boite[] nextSection = entreposage[categorie][i + 1];
            // La tablette actuelle prend la valeur de la tablette de la section suivante
            currentSection[tabletteIndex] = nextSection[tabletteIndex];
            // La tablette de la section suivante devient nulle
            nextSection[tabletteIndex] = null;
        }
    }

    // passage 3d vers 2d les 2 preière dimension sont fusionnées
    public Boite[][] getBoites2D() {

        List<Boite[]> boites2D = new ArrayList<>();
        for (Boite[][] section : entreposage) {
            for (Boite[] tablettes : section) {
                // Vérifier si la section contient au moins une boîte
                boolean sectionHasBoite = false;
                for (Boite boite : tablettes) {
                    if (boite != null) {
                        sectionHasBoite = true;
                        break;
                    }
                }
                // Si la section contient au moins une boîte, ajouter les tablettes à la représentation 2D
                if (sectionHasBoite) {
                    boites2D.add(tablettes);
                }
            }
        }
        return boites2D.toArray(new Boite[0][]);
    }

    public Boite[] getBoites1D() {

        List<Boite> boites1D = new ArrayList<>();
        for (Boite[][] section : entreposage) {
            for (Boite[] tablettes : section) {
                for (Boite boite : tablettes) {
                    if (boite != null) {
                        boites1D.add(boite);
                    }
                }
            }
        }
        return boites1D.toArray(new Boite[0]);
    }

    public Boite[][][] getBoites3D() {

        return entreposage;
    }


}

