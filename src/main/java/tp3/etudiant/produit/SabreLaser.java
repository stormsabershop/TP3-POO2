package tp3.etudiant.produit;

public class SabreLaser extends AbstarctJouet implements Elite {

    private boolean eliteOuNonElite;

    public SabreLaser(String nom, boolean siElite, double prix) {
        super(nom, prix);
        this.eliteOuNonElite = siElite;
    }


	@Override
	public void setEliteOuNonElite() {

	}

	public boolean isEliteOuNonElite() {
		return this.eliteOuNonElite;
	}

	public void recharger() {
		// TODO - implement SabreLaser.recharger
		throw new UnsupportedOperationException();
	}

	@Override
	public String decrit() {
		return toString();
	}

	@Override
	public String toString() {
		return "Sabre laser : Categorie= " + getNumeroCategorie() +", Nom= "+getNom() + "eliteOuNon"+eliteOuNonElite+", Prix= "+getPrix();
	}

	@Override
	public int getNumeroCategorie() {
		return 1;
	}
}