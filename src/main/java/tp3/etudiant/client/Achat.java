package tp3.etudiant.client;

import tp3.echange.Descriptible;
import tp3.application.AbstractProduit;
import tp3.etudiant.section.Lists;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Achat implements Descriptible, Lists {

/*
    private String acheteur;
    private final double MONTANT_TAXES = 0.14;
    private double montantRabais;
    private double montantBrute;

    private int numFacturation = 0;
    private static int compteurFacture;

    Panier panier;

    private LocalDateTime momentAchat;

    public Achat(String acheteurs, LocalDateTime momentAchat, double montantRabais) {
        this.acheteur = acheteurs;
        this.momentAchat = momentAchat;
        this.montantRabais = montantRabais;
        compteurFacture = 0;

    }

    public double calculcout() {
        double prixPanier = 0;
        for (int i = 0; i < panier.getAchats().size(); i++) {
            prixPanier = panier.getAchats().get(i).getPrix();
            prixPanier = prixPanier * (prixPanier - getMontantRabais());
        }
        return prixPanier;
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

 */

    private String acheteur;
    private int contient;
    private LocalDateTime momentAchat;
    private Collection<AbstractProduit> produits;

    // Constantes pour les taxes et rabais
    private static final double TAUX_TAXES = 0.14;
    private double montantRabaisGlobal;
    private double montantRabaisProduits;
    private double montantBrute;

    public Achat(String acheteur, LocalDateTime momentAchat, double montantRabaisGlobal) {
        this.acheteur = acheteur;
        this.contient = 0;
        this.momentAchat = momentAchat;
        this.produits = null;
        this.montantRabaisGlobal = montantRabaisGlobal;
    }

    public double calculCout() {
        double coutTotal = 0;
        for (AbstractProduit produit : produits) {
            coutTotal += produit.getPrix();
        }

        coutTotal -= (montantRabaisGlobal * coutTotal);

        coutTotal -= montantRabaisProduits;

        coutTotal *= (1 + TAUX_TAXES);

        return coutTotal;
    }


    public double calculerMontantBrut(){
        montantBrute = 0;
        for (AbstractProduit produit : produits) {
            montantBrute += produit.getPrix();
        }
        return montantBrute;
    }

    // Méthode pour calculer le montant des rabais sur les produits
    public void calculRabaisProduits() {
        // Implémentez ici le calcul des rabais sur les produits selon les spécifications fournies
    }

    // Autres getters et setters
    public String getAcheteur() {
        return acheteur;
    }

    public void setAcheteur(String acheteur) {
        this.acheteur = acheteur;
    }

    public int calculerContient(){
        contient = produits.size();
        return contient;
    }

    public LocalDateTime getMomentAchat() {
        return momentAchat;
    }

    public void setMomentAchat(LocalDateTime momentAchat) {
        this.momentAchat = momentAchat;
    }

    public Collection<AbstractProduit> getProduits() {
        return produits;
    }

    public void setProduits(Collection<AbstractProduit> produits) {
        this.produits = produits;
    }

    public String imprimeFacture() {
        return "Facturé à :" + getAcheteur() + "\n"
                + "Facturé le :" + getMomentAchat() + "\n"
                + "\n" + "Contient :" + contient
                + "\n" + "Côut brute :" + montantBrute
                + "\n" + "Côut final :" + calculCout();
    }
    @Override
    public String decrit() {
        return " salut je suis decrit";
    }
}
