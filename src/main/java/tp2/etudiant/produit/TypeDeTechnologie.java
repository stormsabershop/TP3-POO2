package tp2.etudiant.produit;

public abstract class TypeDeTechnologie {

	private int batteryWattage;

	public abstract void calculerPourcentageBatterie();

	public int getBatteryWattage() {
		return batteryWattage;
	}

	public void setBatteryWattage(int batteryWattage) {
		this.batteryWattage = batteryWattage;
	}

	public TypeDeTechnologie(int batteryWattage) {
		this.batteryWattage = batteryWattage;
	}
}