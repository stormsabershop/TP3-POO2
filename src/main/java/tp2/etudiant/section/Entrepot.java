package tp2.etudiant.section;


import tp2.etudiant.boite.Boite;

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

        return false;
    }

    public void retireBoite(Boite boite) {

    }

    // passage 3d vers 2d les 2 preière dimension sont fusionnées
    public Boite[][] getBoites2D() {

        return null;
    }

    public Boite[] getBoites1D() {

        return null;
    }

    public Boite[] getBoites3D() {

        return null;
    }


}

