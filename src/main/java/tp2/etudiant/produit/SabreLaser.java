package tp2.etudiant.produit;
import tp2.application.AbstractProduit;
public class SabreLaser extends AbstractProduit implements EliteEtTechnologie {

	private boolean eliteOuNonElite;
	private TypeDeTechnologie typeTechnologie;

	public SabreLaser(String nom) {
		super(nom);
	}

	public void setEliteOuNonElite() {
		// TODO - implement SabreLaser.setEliteOuNonElite
		throw new UnsupportedOperationException();
	}

	public void setTypeTechnologie() {
		// TODO - implement SabreLaser.setTypeTechnologie
		throw new UnsupportedOperationException();
	}

	public TypeDeTechnologie getTypeTechnologie() {
		return this.typeTechnologie;
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
				", typeTechnologie=" + typeTechnologie +
				'}';
	}
}