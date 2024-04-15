package tp2.etudiant;

import tp2.application.AbstractProduit;
import tp2.echange.Descriptible;
import tp2.echange.Modele;
import tp2.etudiant.boite.Boite;
import tp2.etudiant.client.Achat;
import tp2.etudiant.client.Panier;
import tp2.etudiant.section.*;

import java.lang.reflect.Array;
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
        this.sections = new ArrayList<AireI>(List.of(new Vrac(), new AiresDesPresentoires()));
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

    public void placerProduits1(Collection<Boite> boites, AireI section) {
        Iterator<Boite> iterator = boites.iterator();
        while (iterator.hasNext()){
            Boite boite = iterator.next();
            section.placerProduits(boite);
            entrepot.retireBoite(boite);
        }
    }

    public void placerProduits(Collection<Boite> boites, AireI section) {
        Iterator<Boite> iterator = boites.iterator();
        while (iterator.hasNext()){
            Boite boite = iterator.next();
            if (section.getAllProduits().size() == 0) {
                section.placerProduits(boite);
                entrepot.retireBoite(boite);
            } else if (section.getAllProduits().size() != NOMBRE_DE_PLACE_MAX_DANS_AIRE_DES_PRESENTOIRES) {
                List<AbstractProduit> produitsDansLaBoite = boite.getContenu();
                if (NOMBRE_DE_PLACE_MAX_DANS_AIRE_DES_PRESENTOIRES - section.getAllProduits().size() > produitsDansLaBoite.size()){
                    section.placerProduits(produitsDansLaBoite);
                } else if (NOMBRE_DE_PLACE_MAX_DANS_AIRE_DES_PRESENTOIRES - section.getAllProduits().size() == produitsDansLaBoite.size()) {
                    section.placerProduits(produitsDansLaBoite);
                    if (sections.size() < NOMBRE_DE_AIRE_DES_PRESENTOIRES_MAX){
                        sections.add(new AiresDesPresentoires());
                    }
                } else {
                    int nombreDePlaceRestantes;
                    Iterator<AbstractProduit> iterator1 = produitsDansLaBoite.iterator();
                    AbstractProduit[] tab = new AbstractProduit[produitsDansLaBoite.size()];
                    int n = 0;
                    while (iterator1.hasNext()){
                        tab[n] = iterator1.next();
                        n++;
                    }
                    for (int i = 0; i < tab.length; i++) {
                        nombreDePlaceRestantes = NOMBRE_DE_PLACE_MAX_DANS_AIRE_DES_PRESENTOIRES - section.getAllProduits().size();
                        if (nombreDePlaceRestantes < NOMBRE_DE_PLACE_MAX_DANS_AIRE_DES_PRESENTOIRES){
                            section.placerProduits(List.of(tab[i]));
                        } else {
                            section.gereSurplus(List.of(tab[i]));
                        }
                    }

                    /*while (iterator1.hasNext()){
                        AbstractProduit product = iterator1.next();
                        nombreDePlaceRestantes = NOMBRE_DE_PLACE_MAX_DANS_AIRE_DES_PRESENTOIRES - section.getAllProduits().size();
                        if (nombreDePlaceRestantes < NOMBRE_DE_PLACE_MAX_DANS_AIRE_DES_PRESENTOIRES){
                            section.placerProduits(List.of(product));
                        } else {
                            section.gereSurplus(List.of(product));
                        }

                    }

                     */
                    if (sections.size() < NOMBRE_DE_AIRE_DES_PRESENTOIRES_MAX){
                        sections.add(new AiresDesPresentoires());
                    }
                    entrepot.retireBoite(boite);
                }


            }

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
