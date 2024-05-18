package tp3.etudiant.produit;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class CasqueSansFil extends Casque {
    public CasqueSansFil(String nom, double prix, boolean estStrereo) {
        super(nom, prix, estStrereo);
        setTypeProduits("CasqueSansFil");
    }

    @Override
    public String decrit() {
        return toString();
    }

    @Override
    public String toString() {
        return "Casque sans fil : Categorie= " + getNumeroCategorie() + ", Nom= " + getNom() + ", Prix" + getPrix();
    }

    @Override
    public void writeProduits(DataOutputStream dos) throws IOException {
        dos.writeUTF(getTypeProduits());
        super.writeProduits(dos);
        dos.writeBoolean(isEstStereo());
    }
}
