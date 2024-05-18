package tp3.etudiant.fichiers;

import tp3.etudiant.Magasin;

import java.io.*;

public class MagasinWriter {
    public static void serialize(Magasin magasin, File file) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(magasin);
        }
    }
}
