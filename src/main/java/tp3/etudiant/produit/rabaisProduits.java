package tp3.etudiant.produit;

public class rabaisProduits {

    private double rabaisProduis;

    public rabaisProduits() {
        this.rabaisProduis = 0;
    }

    public double calculaRabaisProduis(boolean aRabais) {
        if (aRabais) {
            rabaisProduis = 0.20;
        }
        return rabaisProduis;
    }
}
