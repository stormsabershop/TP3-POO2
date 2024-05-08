package tp3.etudiant;

import tp3.etudiant.fichiers.Historique;
import tp3.application.AbstractProduit;
import tp3.echange.Descriptible;
import tp3.echange.Modele;
import tp3.echange.UI;
import tp3.etudiant.boite.Boite;
import tp3.etudiant.client.Achat;
import tp3.etudiant.client.Panier;
import tp3.etudiant.produit.Casque;
import tp3.etudiant.produit.CasqueSansFil;
import tp3.etudiant.produit.Figurine;
import tp3.etudiant.produit.SabreLaser;
import tp3.etudiant.section.*;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class Magasin implements Modele, Lists, VracNBproduits {


    private Collection<Achat> achats;

    private Panier panier;
    private Entrepot entrepot; // modifier
    private Charite charite;
    private Collection<AireI> sections;
    private AiresDesPresentoires airesDesPresentoires;

    private int nombreProduitsAvantPanier;
    private int nombreProduitsApresPanier;
    private Historique historique = new Historique();
    private UI ui;
    private static final String BASE_PATH = "tp3/etudiant/fichiers/archive/";


    public static final int NOMBRE_DE_PLACE_MAX_DANS_AIRE_DES_PRESENTOIRES = 15;
    public static final int NOMBRE_DE_AIRE_DES_PRESENTOIRES_MAX = 6;

    private Vrac vrac;


    public Magasin() {
        // Instanciez les attributs nécessaires
        this.achats = new ArrayList<>();
        this.panier = new Panier();
        this.entrepot = new Entrepot();
        this.vrac = new Vrac();
        this.sections = new ArrayList<AireI>(List.of(vrac, new Presentoires()));
        this.charite = new Charite();
        this.airesDesPresentoires = new AiresDesPresentoires();
        this.nombreProduitsAvantPanier = 0;
        this.nombreProduitsApresPanier = 0;

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
            historique.ajouterEvenement("Commande d’un ou plusieurs produits");

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
            if (section.getAllProduits().isEmpty()) {
                section.placerProduits(boite);
                entrepot.retireBoite(boite);
                historique.ajouterEvenement("Transfert de produits dans une section");
            } else if (section.getAllProduits().size() < NOMBRE_DE_PLACE_MAX_DANS_AIRE_DES_PRESENTOIRES) {
                List<AbstractProduit> produitsDansLaBoite = boite.getContenu();
                int nombreDePlacesRestantes = NOMBRE_DE_PLACE_MAX_DANS_AIRE_DES_PRESENTOIRES - section.getAllProduits().size();
                if (nombreDePlacesRestantes >= produitsDansLaBoite.size()) {
                    section.placerProduits(produitsDansLaBoite);
                    historique.ajouterEvenement("Transfert de produits dans une section");
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
    public void mettreDansPanier(Collection<AbstractProduit> items) { //a lire
        if (vrac.getAllProduits().size() > nombreProduitsAvantPanier) {
            nombreProduitsAvantPanier = vrac.getAllProduits().size();
        }
        int nombreDeProduitsAjoutesDansLePanier = 0;
        for (AbstractProduit abstractProduit : items) {
            panier.ajouteProduit(abstractProduit, provientDeAire(abstractProduit));
            historique.ajouterEvenement("Transfert de produits dans le panier");
            if (provientDeAire(abstractProduit) == vrac){
                nombreDeProduitsAjoutesDansLePanier++;
            }
        }
        nombreProduitsApresPanier = vrac.getAllProduits().size() - nombreDeProduitsAjoutesDansLePanier;
    }

    public AireI provientDeAire(AbstractProduit produit) {
        AireI aireTest = null;
        for (AireI section : sections) {
            if (section.estPresent(produit)) {
                aireTest = section;
            }
        }
        return aireTest;
    }

    @Override
    public void retirerDuPanier(List<AbstractProduit> itemARetirer) {

        Boite boite;

        for (AbstractProduit item : itemARetirer) {
            boite = new Boite(item);
            Collection<Boite> boiteCollection = new ArrayList<>();
            boiteCollection.add(boite);
            for (AireI section : sections) {
                if (provientDeAire(item) == section)
                    placerProduits(boiteCollection, section);
                historique.ajouterEvenement("Transfert de produits dans une section");

            }
            panier.retireProduit(item);
        }
    }

    @Override
    public Achat acheterPanier(String acheteur, LocalDateTime date, double rabaisGlobal) {
        assert panier!=null : "Panier ne doit pas etre null.";
        Achat achat = new Achat(acheteur, date, rabaisGlobal);
        Collection<AbstractProduit> produits = getContenuPanier();
        achat.setProduits(produits);
        achat.setNombreProduitsAvantPanier(nombreProduitsAvantPanier);
        achat.setNombreProduitsApresPanier(nombreProduitsApresPanier);
        achat.calculerMontantBrut();
        achat.calculCout();
        achat.calculerContient();
        achats.add(achat);
        historique.ajouterEvenement("Achat d'un produits");
        panier.vide();
        return achat;
    }

    public void viderPanier() {
        panier.vide();
    }

    @Override
    public Collection<AbstractProduit> getContenuPanier() {
        return panier.getAchats();
    }


    @Override
    public Collection<Boite> getLivraisons() {
        return null;
    }

    @Override
    public void archive(File file) {

    }

    @Override
    public void reconstruit(File file) {

    }

    @Override
    public void viderMagasin() {

    }

    @Override
    public String init(UI ui) { // lire magain
        this.ui = ui;
        List<AbstractProduit> retListe = new ArrayList<>();

        // Attrapez l'exception de fin de fichier pour déterminer la fin de l'exécution.
        DataInputStream dis = null;

        try {
            dis = new DataInputStream(new BufferedInputStream(new FileInputStream(BASE_PATH + "produits.mag")));

            while (true) {
                String typeProuits = dis.readUTF();
                if (typeProuits.equals("Casque")) {
                    retListe.add(new Casque(dis.readUTF(), dis.readDouble(), dis.readBoolean()));
                }
                if (typeProuits.equals("CasqueSansFil")) {
                    retListe.add(new CasqueSansFil(dis.readUTF(), dis.readDouble(), dis.readBoolean()));
                }
                if (typeProuits.equals("Figurine")) {
                    retListe.add(new Figurine(dis.readUTF(), dis.readDouble()));
                } else if (typeProuits.equals("SabreLazer")) {
                    retListe.add(new SabreLaser(dis.readUTF(), dis.readDouble(), dis.readBoolean()));
                }


            }

        } catch (EOFException eof) {

            System.out.println("");

        } catch (IOException ioe) {
            System.out.println("Impossible d'acceder au fichier");
            System.exit(1);
        } finally {
            ui.setProduitsDisponibles(retListe);
            if (dis != null) {
                try {
                    dis.close();
                } catch (IOException e) {
                    System.out.println("Impossible d'acceder au fichier");
                }
            }
        }

        return "A propos";
    }

    @Override
    public void stop() { // ecris magasin
        OutputStream os = null;
        BufferedOutputStream bos = null;
        DataOutputStream dos = null;
        try {
            os = new FileOutputStream(BASE_PATH + "produits.mag", true);
            bos = new BufferedOutputStream(os);
            dos = new DataOutputStream(bos);

            for (AbstractProduit produit : ui.getProduitsDisponibles()) {
                produit.writeProduits(dos);
            }

        } catch (IOException e) {
            System.out.println("Impossible d'acceder au fichier");
        } finally {
            try {
                historique.ajouterEvenement("Enregistrement du contenu du magasin");
                if (dos != null) {
                    dos.close();
                }
            } catch (IOException e) {
                System.out.println("Impossible d'acceder au fichier");
            }
        }
    }


    public Entrepot getEntrepot() {
        return entrepot;
    }

    public Collection<Achat> getAchats() {
        return achats;
    }

    public void setAchats(Collection<Achat> achats) {
        this.achats = achats;
    }

    public Panier getPanier() {
        return panier;
    }

    public void setPanier(Panier panier) {
        this.panier = panier;
    }

    public void setEntrepot(Entrepot entrepot) {
        this.entrepot = entrepot;
    }

    public void setCharite(Charite charite) {
        this.charite = charite;
    }

    public Collection<AireI> getSections() {
        return sections;
    }

    public void setSections(Collection<AireI> sections) {
        this.sections = sections;
    }

    public AiresDesPresentoires getAiresDesPresentoires() {
        return airesDesPresentoires;
    }

    public void setAiresDesPresentoires(AiresDesPresentoires airesDesPresentoires) {
        this.airesDesPresentoires = airesDesPresentoires;
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

    public Vrac getVrac() {
        return vrac;
    }

    public void setVrac(Vrac vrac) {
        this.vrac = vrac;
    }
}
