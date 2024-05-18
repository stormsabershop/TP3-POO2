package tp3.etudiant.section;


import tp3.etudiant.boite.Boite;

import java.io.Serializable;
import java.util.Arrays;

public class Entrepot implements Serializable {
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
        boolean retval = false;
        for (int i = 0; i < entreposage.length && retval == false; i++) {
            if (rangerLibre(i)) {
                entreposage[i][0][0] = boite;
                retval = true;
            } else {
                if (boite.getNumeroCategorie() == categorieRange(i)) {
                    for (int j = 0; j < entreposage[i].length && retval == false; j++) {
                        if (boite.getNumeroProduit() == produitSection(i, j)) {
                            if (!verifSectionPlein(i, j)) {
                                for (int k = entreposage[i][j].length - 2; k >= 0 && retval == false; k--) {
                                    entreposage[i][j][k + 1] = entreposage[i][j][k];
                                    entreposage[i][j][k] = null;
                                }
                                entreposage[i][j][0] = boite;
                                retval = true;
                            } else {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return retval;
    }

    public boolean rangerLibre(int indexRangée) {

        boolean estLibre = true;
        for (int i = 0; i < entreposage[indexRangée].length; i++) {
            for (int j = 0; j < entreposage[indexRangée][i].length; j++) {
                if (entreposage[indexRangée][i][j] != null) {
                    estLibre = false;
                }
            }

        }
        return estLibre;

    }

    public int categorieRange(int index) {
        int indexTrouve = -1;
        for (int i = 0; i < entreposage[index].length; i++) {
            for (int j = 0; j < entreposage[index][i].length; j++) {
                if (entreposage[index][i][j] != null) {
                    indexTrouve = entreposage[index][i][j].getNumeroCategorie();
                }
            }
        }
        return indexTrouve;
    }

    public int produitSection(int indexRange, int indexSection) {
        int indexTrouv = -1;
        for (int j = 0; j < entreposage[indexRange][indexSection].length; j++) {
            if (entreposage[indexRange][indexSection][j] != null) {
                indexTrouv = entreposage[indexRange][indexSection][j].getNumeroProduit();

            }
        }
        return indexTrouv;
    }

    public boolean verifSectionPlein(int numeroRangee, int numeroSection) {
        boolean estLibre = true;
        for (int i = 0; i < entreposage[numeroRangee][numeroSection].length; i++) {
            if (entreposage[numeroRangee][numeroSection][i] == null) {
                estLibre = false;
                break;
            }
        }
        return estLibre;
    }


    public void retireBoite(Boite boite) {

        for (int categorie = 0; categorie < entreposage.length; categorie++) {
            Boite[][] section = entreposage[categorie];

            for (int sectionIndex = 0; sectionIndex < section.length; sectionIndex++) {
                Boite[] tablettes = section[sectionIndex];

                for (int tabletteIndex = 0; tabletteIndex < tablettes.length; tabletteIndex++) {
                    Boite currentBoite = tablettes[tabletteIndex];

                    if (currentBoite != null && currentBoite.equals(boite)) {

                        tablettes[tabletteIndex] = null;

                        descendreBoites(categorie, sectionIndex, tabletteIndex);
                        return;
                    }
                }
            }
        }

    }

    private void descendreBoites(int categorie, int sectionIndex, int tabletteIndex) {

        for (int i = sectionIndex; i < entreposage[categorie].length; i++) {

            if (i == entreposage[categorie].length - 1) {
                break;
            }
            Boite[] currentSection = entreposage[categorie][i];
            Boite[] nextSection = entreposage[categorie][i + 1];
            currentSection[tabletteIndex] = nextSection[tabletteIndex];

            nextSection[tabletteIndex] = null;
        }
    }

    public Boite[][] getBoites2D() {
        int dim1 = entreposage.length;
        int dim2 = entreposage[0].length;
        int dim3 = entreposage[0][0].length;

        Boite[][] boite2D = new Boite[dim1 * dim2][dim3];
        for (int i = 0; i < dim1; i++) {
            for (int j = 0; j < dim2; j++) {
                for (int k = 0; k < dim3; k++) {
                    boite2D[i * dim2 + j][k] = entreposage[i][j][k];
                }
            }
        }
        return boite2D;
    }

    /*public Boite[] getBoites1D() {

    }*/

    public Boite[][][] getBoites3D() {

        return entreposage;
    }


}

