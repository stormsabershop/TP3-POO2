package tp2.etudiant.produit;
import tp2.application.AbstractProduit;
public class SabreLaser extends AbsstractJouet implements Elite {

	private boolean eliteOuNonElite;

	public SabreLaser(String nom, boolean siElite,int prix) {
		super(nom,prix);
		this.eliteOuNonElite= siElite;
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
}