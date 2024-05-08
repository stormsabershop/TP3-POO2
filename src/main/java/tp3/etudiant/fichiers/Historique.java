package tp3.etudiant.fichiers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Historique {

    File historique = new File("tp3/etudiant/fichiers/historique/historique.txt");
    FileWriter writer = null;
    BufferedWriter bw = null;

    public void ajouterEvenement(String evenement) {
        if (!historique.exists()) {
            try {
                historique.createNewFile();
            } catch (IOException e) {
                System.out.println("Chemin non valide");
                ;
            }
        } else {
            try {
                writer = new FileWriter(historique, true);
                bw = new BufferedWriter(writer);
                LocalDateTime maintenant = LocalDateTime.now();
                String evenementFormate = maintenant.format(DateTimeFormatter.ofPattern("dd-MM-uu : HH:mm:ss:SS")) + " -> " + evenement;
                bw.write(evenementFormate);
                bw.write(System.lineSeparator());
                bw.close();
                writer.close();
            } catch (IOException e) {
                System.out.println("Impossible d'acceder au ficheir");
            }
        }
    }


}