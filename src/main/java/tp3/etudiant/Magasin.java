package tp3.etudiant;

import tp3.application.AbstractProduit;
import tp3.echange.Descriptible;
import tp3.echange.Modele;
import tp3.etudiant.boite.Boite;
import tp3.etudiant.client.Achat;
import tp3.etudiant.client.Panier;
import tp2.etudiant.section.*;
import tp3.etudiant.section.*;

import java.time.LocalDateTime;
import java.util.*;

public class Magasin implements Modele {


    private Collection<Achat> achats;

    private Panier panier;
    private Entrepot entrepot; // modifier
    private Charite charite;
    private Collection<AireI> sections;
    private AiresDesPresentoires airesDesPresentoires;
    private Vrac vrac;


    public static final int NOMBRE_DE_PLACE_MAX_DANS_AIRE_DES_PRESENTOIRES = 15;
    public static final int NOMBRE_DE_AIRE_DES_PRESENTOIRES_MAX = 6;


    public Magasin() {
        // Instanciez les attributs nécessaires
        this.achats = new ArrayList<>();
        this.panier = new Panier();
        this.entrepot = new Entrepot();
        this.sections = new ArrayList<AireI>(List.of(new Vrac(), new Presentoires()));
        this.charite = new Charite();
        this.airesDesPresentoires = new AiresDesPresentoires();

    }


    @Override
    public Collection<AireI> getAllSections() {

        return sections;
    }

    @Override
    public Descriptible getCharite() {

        return charite;
    }

    @Override
    public int recevoirCommande(Collection<Boite> commande) {

        int boitesNonPlacees = 0;

        // Parcours de chaque boîte de la commande
        for (Boite boite : commande) {
            boolean placee = entrepot.entreposeBoite(boite);


            if (!placee) {
                boitesNonPlacees++;
            }
        }

        return boitesNonPlacees;
    }



    public void placerProduits(Collection<Boite> boites, AireI section) {
        Iterator<Boite> iterator = boites.iterator();
        while (iterator.hasNext()) {
            Boite boite = iterator.next();
            if (section.getAllProduits().size() == 0) {
                section.placerProduits(boite);
                entrepot.retireBoite(boite);
            } else if (section.getAllProduits().size() < NOMBRE_DE_PLACE_MAX_DANS_AIRE_DES_PRESENTOIRES) {
                List<AbstractProduit> produitsDansLaBoite = boite.getContenu();
                int nombreDePlacesRestantes = NOMBRE_DE_PLACE_MAX_DANS_AIRE_DES_PRESENTOIRES - section.getAllProduits().size();
                if (nombreDePlacesRestantes >= produitsDansLaBoite.size()) {
                    section.placerProduits(produitsDansLaBoite);
                } else {
                    List<AbstractProduit> produitsAPlacer = new ArrayList<>();
                    List<AbstractProduit> surplus = new ArrayList<>();
                    Iterator<AbstractProduit> produitIterator = produitsDansLaBoite.iterator();
                    int count = 0;
                    while (produitIterator.hasNext() && count < nombreDePlacesRestantes) {
                        produitsAPlacer.add(produitIterator.next());
                        count++;
                    }
                    while (produitIterator.hasNext()) {
                        surplus.add(produitIterator.next());
                    }
                    section.placerProduits(produitsAPlacer);
                    charite.donneProduits(surplus);
                }
                entrepot.retireBoite(boite);
            } else {
                charite.donneProduits(boite.getContenu());
                entrepot.retireBoite(boite);
            }
        }
        if (section.getAllProduits().size() >= NOMBRE_DE_PLACE_MAX_DANS_AIRE_DES_PRESENTOIRES && sections.size() < NOMBRE_DE_AIRE_DES_PRESENTOIRES_MAX) {
            sections.add(new Presentoires());
        }
    }



















    @Override
    public void mettreDansPanier(Collection<AbstractProduit> items) {

        Iterator<AbstractProduit> iterator = items.iterator();
        while (iterator.hasNext()){
            panier.ajouteProduit(iterator.next());
        }

    }

    @Override
    public Collection<AbstractProduit> getContenuPanier() {
        return panier.getAchats();
    }

    @Override
    public void retirerDuPanier(List<AbstractProduit> itemARetirer) {
        Iterator<AbstractProduit> iterator = itemARetirer.iterator();
        while (iterator.hasNext()){
            panier.retireProduit(iterator.next());
        }
    }

    public Achat acheterPanier(String achateur, LocalDateTime dateTime) {
        Achat achat = new Achat(achateur, panier, dateTime);
        achats.add(achat);
        panier.vide();
        return achat;
    }

    @Override
    public void init() {

    }

    public Entrepot getEntrepot() {
        return entrepot;
    }
//    public Vrac getVrac(){
//        return vrac;
//    }
//    public AiresDesPresentoires getAiresDesPresentoires(){
//        return airesDesPresentoires;
//    }

}
