package tp3.etudiant.produit;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class Casque extends AbstractEcouteurs {

    rabaisProduits rabaisProduits = new rabaisProduits();
    private boolean estStereo;

    public Casque(String nom, double prix, boolean estStereo) {
        super(nom, prix);
        this.estStereo = estStereo;
        rabaisProduits.calculaRabaisProduis(estStereo);
        setTypeProduits("Casque");
    }

    @Override
    public String decrit() {
        return toString();
    }

    @Override
    public String toString() {
        return "Casque: Categorie= " + getNumeroCategorie() + ", Nom= " + getNom() + ", Prix" + getPrix();
    }

    public boolean isEstStereo() {
        return estStereo;
    }

    public void setEstStereo(boolean estStereo) {
        this.estStereo = estStereo;
    }

    @Override
    public void writeProduits(DataOutputStream dos) throws IOException {
        dos.writeUTF(getTypeProduits());
        super.writeProduits(dos);
        dos.writeBoolean(isEstStereo());
    }
}