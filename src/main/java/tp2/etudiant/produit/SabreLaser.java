package tp2.etudiant.produit;
import tp2.application.AbstractProduit;
public class SabreLaser extends AbstractProduit implements Elite {

	private boolean eliteOuNonElite;

	public SabreLaser(String nom, boolean siElite) {
		super(nom);
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
	public int getNumeroCategorie() {
		return 0;
	}

	@Override
	public String decrit() {
		return toString();
	}

	@Override
	public String toString() {
		return "SabreLaser{" +
				"eliteOuNonElite=" + eliteOuNonElite +
				'}';
	}
}