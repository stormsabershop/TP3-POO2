package tp3.etudiant.produit;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class SabreLaser extends AbstarctJouet implements Elite {

    rabaisProduits rabaisProduits = new rabaisProduits();
    private boolean eliteOuNonElite;

    public SabreLaser(String nom, double prix, boolean siElite) {
        super(nom, prix);
        this.eliteOuNonElite = siElite;
        rabaisProduits.calculaRabaisProduis(siElite);
        setTypeProduits("SabreLazer");
    }


	@Override
	public void setEliteOuNonElite(boolean eliteOuNonElite) {
		this.eliteOuNonElite = eliteOuNonElite;
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
        return "Sabre laser : Categorie= " + getNumeroCategorie() + ", Nom= " + getNom() + "eliteOuNon" + eliteOuNonElite + ", Prix= " + getPrix();
    }

    @Override
    public void writeProduits(DataOutputStream dos) throws IOException {
        dos.writeUTF(getTypeProduits());
        super.writeProduits(dos);
        dos.writeBoolean(isEliteOuNonElite());
    }

}