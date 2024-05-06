package tp3.etudiant.produit;

public class Casque extends AbstractEcouteurs {

    rabaisProduits rabaisProduits = new rabaisProduits();
    private boolean estStereo;

    public Casque(String nom, double prix, boolean estStereo) {
        super(nom, prix);
        this.estStereo = estStereo;
        rabaisProduits.calculaRabaisProduis(estStereo);
    }

    @Override
    public String decrit() {
        return toString();
    }

    @Override
    public String toString() {
        return "Casque: Categorie= " + getNumeroCategorie() + ", Nom= " + getNom() + ", Prix" + getPrix();
    }


}