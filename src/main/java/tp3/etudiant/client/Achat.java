package tp3.etudiant.client;

import tp3.echange.Descriptible;
import tp3.application.AbstractProduit;
import tp3.etudiant.section.Lists;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Achat implements Descriptible, Lists {


    private String acheteur;
    private double montantTaxes;
    private double montantRabais;
    private double montantBrute;

    private int numFacturation = 0;
    private static int compteurFacture;

    private LocalDateTime momentAchat;

    public Achat(String acheteur, LocalDateTime momentAchat, double montantRabais) {
        this.acheteur = acheteur;
        this.momentAchat = momentAchat;
        this.montantRabais = montantRabais;

    }

    public String imprimeFacture() {
        return "Facturé à :" + getAcheteur() + "\n"
                + "Facturé le :" + getMomentAchat() + "\n"
                + "\n" + "Contient :" + compteurFacture
                + "\n" + "Côut brute :" + getMontantBrute();
    }


    private String utilise2Decimale(double valeur) {
        return String.format("%.2f", valeur);
    }

    public double getMontantBrute() {
        double montant = 0;
        for (int i = 0; i < produits.size(); i++) {
            montant = produits.get(i).getPrix();
            montant += montant;

        }
        return montant;
    }

    public void setMontantBrute(double montantBrute) {
        this.montantBrute = montantBrute;
    }

    public LocalDateTime getMomentAchat() {
        return momentAchat;
    }

    public void setMomentAchat(LocalDateTime momentAchat) {
        this.momentAchat = momentAchat;
    }

    public String getAcheteur() {
        return acheteur;
    }

    public void setAcheteur(String acheteur) {
        this.acheteur = acheteur;
    }


    public int getNumFacturation() {
        return numFacturation;
    }


    @Override
    public String decrit() {
        return "";
    }

    public double getMontantRabais() {
        return montantRabais;
    }

    public void setMontantRabais(double montantRabais) {
        this.montantRabais = montantRabais;
    }
}
