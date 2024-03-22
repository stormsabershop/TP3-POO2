package tp2.etudiant.produit;

public class SabreLaser extends Produit implements EliteEtTechnologie {

	private boolean eliteOuNonElite;
	private TypeDeTechnologie typeTechnologie;

	public void acheter() {
		// TODO - implement SabreLaser.acheter
		throw new UnsupportedOperationException();
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
		System.out.println(typeTechnologie.getBatteryWattage());
	}

}