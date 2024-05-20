package tp3.etudiant.client;

import tp3.echange.Descriptible;
import tp3.application.AbstractProduit;
import tp3.etudiant.Magasin;
import tp3.etudiant.produit.Figurine;
import tp3.etudiant.produit.SabreLaser;
import tp3.etudiant.section.AireI;
import tp3.etudiant.section.Lists;
import tp3.etudiant.section.Vrac;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Achat implements Descriptible, Lists {

    private String acheteur;
    private int contient;
    private LocalDateTime momentAchat;
    private Collection<AbstractProduit> produits;

    // Constantes pour les taxes et rabais
    private static final double TAUX_TAXES = 0.14;
    private static final double RABAIS_VRAC_MAXIMAL = 20;
    private double montantRabaisGlobal;
    private double montantRabaisProduits;
    private double montantBrute;

    private double rabais;
    private double rabaisVrac;
    private double rabaisPresentoir;

    public static final double RAB_PRESENTOIR = 0.15;
    private double coutFinal;

    private Collection<AbstractProduit> produitsAchetesPresentoir;
    private int nombreProduitsAvantPanier;
    private int nombreProduitsApresPanier;

    public static final double DELAI_FIXE_RABAISP = 4;

    private String produitContient = "\n";
    private String detailsRabais = "";

    public Achat(String acheteur, LocalDateTime momentAchat, double montantRabaisGlobal) {
        this.acheteur = acheteur;
        this.contient = 0;
        this.momentAchat = momentAchat;
        this.produits = null;
        this.montantRabaisGlobal = montantRabaisGlobal;
        this.coutFinal = 0;
    }

    public double calculCout() {
        boolean isVrac = false;
        coutFinal = 0;
        for (AbstractProduit produit : produits) {
            coutFinal += produit.getPrix();
        }
        double rab = calculerRabaisVrac(coutFinal);
        if (rab > 0) {
            coutFinal -= rab;
        }
        rab = calculerRabaisPresentoir();
        if (rab > 0) {
            coutFinal -= rab;
        }
        coutFinal -= calculerToutLesRabais();

        return coutFinal;
    }


    public double calculerMontantBrut(){
        montantBrute = 0;
        for (AbstractProduit produit : produits) {
            montantBrute += produit.getPrix();
        }
        return montantBrute;
    }
    public String getAcheteur() {
        return acheteur;
    }

    public void setAcheteur(String acheteur) {
        this.acheteur = acheteur;
    }

    public int calculerContient() {
        contient = produits.size();
        for (AbstractProduit produit : produits) {
            produitContient += produit.getNom() + " : " + produit.getPrix() + "\n";
        }
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
        if (rabaisVrac >= 1) {
            rabais += rabaisVrac;
        }
        if (rabaisPresentoir >= 1) {
            rabais += rabaisVrac;
        }
        return "Facturé à :" + getAcheteur() + "\n"
                + "Facturé le :" + getMomentAchat() + "\n"
                + "\n" + "Contient :" + contient + " produits"
                + produitContient
                + "\n" + "Côut brute :" + String.format("%.2f", montantBrute)
                + "\n" + "Rabais accordés: " + String.format("%.2f", rabais)
                + "\n" + "Taxes: " + String.format("%.2f", coutFinal * 0.15)
                + "\n" + "Total: " + String.format("%.2f", (coutFinal - (coutFinal * 0.15)))
                + "\n" + "Details des rabais accordés"
                + "\n" + detailsRabais;
    }

    public double calculerToutLesRabais(){
        rabais = 0;
        double cout = montantBrute;
        cout -= ((int) montantRabaisGlobal * cout) / 100;
        rabais = montantBrute - cout;

        for (AbstractProduit produit: produits) {
            if (produit.getClass() == SabreLaser.class || produit.getClass() == Figurine.class){
                rabais += ((15 * produit.getPrix()) / 100);
                detailsRabais += "\n" + "rabais produit: " + produit.getNom() + " de " + String.format("%.2f", ((15 * produit.getPrix()) / 100));
            }
        }
        detailsRabais += "\n" + "Rabais Global de " + String.format("%.2f", rabais + rabaisVrac + rabaisPresentoir);
        return rabais;
    }

    public double calculerRabaisVrac(double prixTotal) {

        double fractionVolumeAchete = (double) nombreProduitsApresPanier / nombreProduitsAvantPanier;
        double rabaisDuVrac = RABAIS_VRAC_MAXIMAL * fractionVolumeAchete * prixTotal / 100;
        if (fractionVolumeAchete == 1.0) {
            rabaisDuVrac = 0;
        }
        rabaisVrac = rabaisDuVrac;
        nombreProduitsAvantPanier = 0;
        nombreProduitsApresPanier = 0;
        detailsRabais += "\n" + "section rabais - Vrac de " + String.format("%.2f", rabaisDuVrac);
        return rabaisDuVrac;
    }

    public double calculerRabaisPresentoir() {

        rabaisPresentoir = 0;
        for (AbstractProduit produit : produitsAchetesPresentoir) {
            System.out.println(produit.getDate().getDayOfMonth() + " - " + momentAchat.getDayOfMonth() + " = " + (produit.getDate().getDayOfMonth() - momentAchat.getDayOfMonth()));
            rabaisPresentoir += (((produit.getDate().getDayOfMonth()) - momentAchat.getDayOfMonth()) > DELAI_FIXE_RABAISP) ? produit.getPrix() * RAB_PRESENTOIR : 0;
        }
        produitsAchetesPresentoir = new ArrayList<>();
        detailsRabais += "\n" + "section rabais - Presentoir de " + String.format("%.2f", rabaisPresentoir);
        return rabaisPresentoir;
    }

    @Override
    public String decrit() {
        return " salut je suis decrit";
    }

    public int getNombreProduitsAvantPanier() {
        return nombreProduitsAvantPanier;
    }

    public void setNombreProduitsAvantPanier(int nombreProduitsAvantPanier) {
        this.nombreProduitsAvantPanier = nombreProduitsAvantPanier;
    }

    public int getNombreProduitsApresPanier() {
        return nombreProduitsApresPanier;
    }

    public void setNombreProduitsApresPanier(int nombreProduitsApresPanier) {
        this.nombreProduitsApresPanier = nombreProduitsApresPanier;
    }

    public Collection<AbstractProduit> getProduitsAchetesPresentoir() {
        return produitsAchetesPresentoir;
    }

    public void setProduitsAchetesPresentoir(Collection<AbstractProduit> produitsAchetesPresentoir) {
        this.produitsAchetesPresentoir = produitsAchetesPresentoir;
    }
}
