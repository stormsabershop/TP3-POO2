package tp2.etudiant.client;

import tp2.application.AbstractProduit;
import tp2.echange.Descriptible;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Achat implements Descriptible {

    public static final double TAXE = 0.15;
    private static final double COUT_LIVRAISON_PAR_BOITE = 1.99;

    private String acheteur;


    private int numFacturation = 0;
    private static int compteurFacture;

    private LocalDateTime dateAchat;


    public Achat(String acheteur, Panier panier, LocalDateTime dateAchat) {
        this.acheteur = acheteur;
        this.dateAchat = dateAchat;
        this.numFacturation = compteurFacture++;
    }

    public double calculeCout() {

        return -1;
    }




    public int getNumFacturation() {
        return numFacturation;
    }


    @Override
    public String decrit() {
        return "";
    }
}
