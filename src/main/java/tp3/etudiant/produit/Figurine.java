package tp3.etudiant.produit;

import java.io.DataOutputStream;
import java.io.IOException;

public class Figurine extends AbstarctJouet {


    public Figurine(String nom, double prix) {
        super(nom, prix);
        setTypeProduits("Figurine");
    }


    @Override
    public String decrit() {
        return toString();
    }

    @Override
    public String toString() {
        return "Figurine : Categorie= " + getNumeroCategorie() + ", Nom= " + getNom() + ", Prix= " + getPrix();
    }

    @Override
    public void writeProduits(DataOutputStream dos) throws IOException {
        dos.writeUTF(getTypeProduits());
        super.writeProduits(dos);
    }


}