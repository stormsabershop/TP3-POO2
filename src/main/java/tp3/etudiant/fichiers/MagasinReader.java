package tp3.etudiant.fichiers;

import tp3.etudiant.Magasin;

import java.io.*;

public class MagasinReader {
    public static Magasin deserialize(File file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (Magasin) ois.readObject();
        }
    }
}
