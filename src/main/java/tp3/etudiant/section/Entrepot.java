package tp3.etudiant.section;


import tp3.etudiant.boite.Boite;

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

    /*public boolean entreposeBoite1(Boite boite) {
        boolean passage = false;

        for (int i = 0; i < entreposage.length; i++) {
            if (entreposage.length <= NOMBRE_CATEGORIES || entreposage.length > 0) {
                if (boite.getNumeroCategorie() == categorieRange(i)) {
                    for (int j = 0; j < entreposage[i].length; j++) {
                        if (entreposage[i].length < NOMBRE_SECTION) {
                            for (int k = 0; k < entreposage[i][j].length; k++) {
                                if (entreposage[i][j].length < NOMBRE_TABLETTE){
                                    entreposage[i][j][k] = boite;
                                    passage = true;
                                }
                            }
                        }
                    }
                }
            }
        }

        return passage;
    }*/


    public boolean entreposeBoite10(Boite boite) {
        int nbExec = 0;
        boolean retval = false;
        for (int i = 0; i < entreposage.length && retval == false; i++) {
            if (rangerLibre(i)) {
                entreposage[i][0][0] = boite;
                retval = true;
                nbExec++;
            } else {
                if (boite.getNumeroCategorie() == categorieRange(i)) {
                    for (int j = entreposage[i].length-1; j >= 0 && retval == false; j--) {
                        if (boite.getNumeroProduit() == produitSection(i, j)) {
                            if (!verifSectionPlein(i, j)) {
                                for (int k = 0; k < entreposage[i][j].length && retval == false; k++) {
                                    if (entreposage[i][j][k] == null) {
                                        entreposage[i][j][k] = boite;
                                        retval = true;
                                        nbExec++;
                                    } else{
                                        if (k > 0){
                                            entreposage[i][j][k-1] = entreposage[i][j][k];
                                            entreposage[i][j][k] = boite;
                                            nbExec++;
                                        }
                                    }
                                }
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

    /*
    public boolean ExentreposeBoite(Boite boite) {
        int nbExec = 0;
        boolean retval = false;
        for (int i = 0; i < entreposage.length && retval == false; i++) {
            if (rangerLibre(i)) {
                entreposage[i][0][0] = boite;
                retval = true;
                nbExec++;
            } else {
                if (boite.getNumeroCategorie() == categorieRange(i)) {
                    for (int j = 0; j < entreposage[i].length && retval == false; j++) {
                        if (boite.getNumeroProduit() == produitSection(i, j)) {
                            if (!verifSectionPlein(i, j)) {
                                for (int k = 0; k < entreposage[i][j].length && retval == false; k++) {
                                    if (entreposage[i][j][k] == null) {
                                        entreposage[i][j][k] = boite;
                                        retval = true;
                                        nbExec++;
                                    }
                                }
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

     */

    public boolean entreposeBoite(Boite boite) {
        int nbExec = 0;
        boolean retval = false;
        for (int i = 0; i < entreposage.length && retval == false; i++) {
            if (rangerLibre(i)) {
                entreposage[i][0][0] = boite;
                retval = true;
                nbExec++;
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
                                nbExec++;
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

   /* public boolean entreposeBoite3(Boite boite) {
        int categorie = boite.getNumeroCategorie();

        // Vérifier si la catégorie est valide
        if (categorie < 0 || categorie >= entreposage.length) {
            return false;
        }

        // Parcourir les sections de la catégorie
        for (int sectionIndex = 0; sectionIndex < entreposage[categorie].length; sectionIndex++) {
            Boite[] tablettes = entreposage[categorie][sectionIndex];
            // Vérifier si la section peut accueillir la boîte
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
                    if (tabletteIndex == 0 || tablettes[tabletteIndex - 1] != null) {
                        // Placer la boîte sur la tablette libre
                        tablettes[tabletteIndex] = boite;
                        return true; // Boîte placée avec succès
                    }
                }
            }
        }
        // Aucune section n'a pu accueillir la boîte
        return false;
    }*/



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

