package tp3.etudiant;

import tp3.etudiant.fichiers.Historique;
import tp3.application.AbstractProduit;
import tp3.echange.Descriptible;
import tp3.echange.Modele;
import tp3.echange.UI;
import tp3.etudiant.boite.Boite;
import tp3.etudiant.client.Achat;
import tp3.etudiant.client.Panier;
import tp3.etudiant.fichiers.*;
import tp3.etudiant.produit.Casque;
import tp3.etudiant.produit.CasqueSansFil;
import tp3.etudiant.produit.Figurine;
import tp3.etudiant.produit.SabreLaser;
import tp3.etudiant.section.*;

import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;

public class Magasin implements Modele, Lists, VracNBproduits, Serializable {


    private Collection<Achat> achats;

    private Panier panier;
    private Entrepot entrepot; // modifier
    private Charite charite;
    private Collection<AireI> sections;
    private AiresDesPresentoires airesDesPresentoires;

    private int nombreProduitsAvantPanier;
    private int nombreProduitsApresPanier;
    private transient Historique historique = new Historique();
    private transient UI ui;
    private static final String BASE_PATH = "tp3/etudiant/fichiers/archive/";

    private Collection<AbstractProduit> produitsDePresentoir;


    public static final int NOMBRE_DE_PLACE_MAX_DANS_AIRE_DES_PRESENTOIRES = 15;
    public static final int NOMBRE_DE_AIRE_DES_PRESENTOIRES_MAX = 6;

    private Vrac vrac;


    public Magasin() {
        // Instanciez les attributs nécessaires
        historique.ajouterEvenement("Ouverture de l’application");
        produitsDePresentoir = new ArrayList<>();
        this.achats = new ArrayList<>();
        this.panier = new Panier();
        this.entrepot = new Entrepot();
        this.vrac = new Vrac();
        this.sections = new ArrayList<>(List.of(vrac, new Presentoires()));
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
    public void mettreDansPanier(Collection<AbstractProduit> items) {
        produitsDePresentoir = new ArrayList<>();
        if (vrac.getAllProduits().size() > nombreProduitsAvantPanier) {
            nombreProduitsAvantPanier = vrac.getAllProduits().size();
        }
        int nombreDeProduitsAjoutesDansLePanier = 0;
        for (AbstractProduit abstractProduit : items) {
            panier.ajouteProduit(abstractProduit, provientDeAire(abstractProduit));
            historique.ajouterEvenement("Transfert de produits dans le panier");
            if (provientDeAire(abstractProduit) == vrac) {
                nombreDeProduitsAjoutesDansLePanier++;
            } else {
                produitsDePresentoir.add(abstractProduit);
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
        achat.setProduitsAchetesPresentoir(produitsDePresentoir);
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
        nombreProduitsAvantPanier = 0;
        nombreProduitsApresPanier = 0;

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
        try {
            MagasinWriter.serialize(this, file);
            historique.ajouterEvenement("Contenu du magasin archivé.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Impossible d'archiver le contenu du magasin.");
        }
    }

    @Override
    public void reconstruit(File file) {

        try {
            Magasin magasin = MagasinReader.deserialize(file);
            this.achats = magasin.achats;
            this.panier = magasin.panier;
            this.entrepot = magasin.entrepot;
            this.charite = magasin.charite;
            this.sections = magasin.sections;
            vrac.placerProduits(magasin.vrac.getAllProduits());
            this.airesDesPresentoires = magasin.airesDesPresentoires;
            this.nombreProduitsAvantPanier = magasin.nombreProduitsAvantPanier;
            this.nombreProduitsApresPanier = magasin.nombreProduitsApresPanier;
            this.produitsDePresentoir = magasin.produitsDePresentoir;
            historique.ajouterEvenement("Contenu du magasin reconstruit à partir du fichier d'archive.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Impossible de reconstruire le contenu du magasin.");
        }
    }

    @Override
    public void viderMagasin() {
        historique.ajouterEvenement("le magasin de fait vider");
        System.out.println("vider magasin debut");
        produitsDePresentoir = new ArrayList<>();
        this.achats = new ArrayList<>();
        this.panier = new Panier();
        this.entrepot = new Entrepot();
        this.vrac = new Vrac();
        this.sections = new ArrayList<>(List.of(vrac, new Presentoires()));
        vrac.viderAire();
        this.charite = new Charite();
        this.airesDesPresentoires = new AiresDesPresentoires();
        this.nombreProduitsAvantPanier = 0;
        this.nombreProduitsApresPanier = 0;
        System.out.println("VIDE MAGASIN FIN");

    }

    @Override
    public String init(UI ui) {
        this.ui = ui;
        List<AbstractProduit> retListe = new ArrayList<>();
        List<AbstractProduit> retListe2 = new ArrayList<>();

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

        ////////////////////////////////////////////////////////////////////////////////////////////////

        DataInputStream dis2 = null;

        try {
            dis2 = new DataInputStream(new BufferedInputStream(new FileInputStream(BASE_PATH + "vrac.mag")));

            while (true) {
                String typeProuits = dis2.readUTF();
                if (typeProuits.equals("Casque")) {
                    retListe2.add(new Casque(dis2.readUTF(), dis2.readDouble(), dis2.readBoolean()));
                }
                if (typeProuits.equals("CasqueSansFil")) {
                    retListe2.add(new CasqueSansFil(dis2.readUTF(), dis2.readDouble(), dis2.readBoolean()));
                }
                if (typeProuits.equals("Figurine")) {
                    retListe2.add(new Figurine(dis2.readUTF(), dis2.readDouble()));
                } else if (typeProuits.equals("SabreLazer")) {
                    retListe2.add(new SabreLaser(dis2.readUTF(), dis2.readDouble(), dis2.readBoolean()));
                }


            }

        } catch (EOFException eof) {

            System.out.println("");

        } catch (IOException ioe) {
            System.out.println("Impossible d'acceder au fichier");
            System.exit(1);
        } finally {
            vrac.placerProduits(retListe2);
            if (dis2 != null) {
                try {
                    dis2.close();
                } catch (IOException e) {
                    System.out.println("Impossible d'acceder au fichier");
                }
            }
        }

        ////////////////////////////////////////////////////////////////////////////////////////////

        // lire les sections


        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(BASE_PATH + "sections.obj")));
            sections = new ArrayList<>();
            while (true) {
                try {
                    AireI section = (AireI) ois.readObject();
                    sections.add(section);
                    if (section instanceof Vrac) {
                        vrac = (Vrac) section;
                        System.out.println(vrac.getAllProduits());
                    }
                } catch (EOFException eof) {
                    break;
                }
            }
            if (sections.isEmpty()) {
                vrac = new Vrac();
                sections.addAll(List.of(vrac, new Presentoires()));
            } else if (!sections.contains(vrac)) {
                vrac = new Vrac();
                sections.add(vrac);
            } else {
                sections.add(new Presentoires());
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Impossible d'acceder au fichier");
            e.printStackTrace();
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    System.out.println("Impossible d'acceder au fichier");
                    e.printStackTrace();
                }
            }
        }

        /*ObjectInputStream ois = null;

        try {
            ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(BASE_PATH + "sections.obj")));

            while (true) {
                sections.add((AireI) ois.readObject());
            }

        } catch (EOFException eof) {

            System.out.println(" ");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ioe) {
            System.out.println("Impossible d'acceder au fichier");
            System.exit(1);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    System.out.println("Impossible d'acceder au fichier");
                }
            }
        }
         */


        // lire a propos
        List<String> retPhrases = new ArrayList<>();

        String filePath = "resources/APropos.txt";
        URL url = Magasin.class.getClassLoader().getResource("APropos.txt");

        try (InputStreamReader fr = new InputStreamReader(url.openStream());
             BufferedReader bf = new BufferedReader(fr)) {

            String phrase = bf.readLine();
            while (phrase != null) {
                retPhrases.add(phrase);
                phrase = bf.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
            return "Erreur lors de la lecture du fichier APropos.txt";
        }


        retPhrases.add("");
        retPhrases.add("Jérôme Ouellet");
        retPhrases.add("Boubou Tamboura");

        StringBuilder content = new StringBuilder();
        for (String line : retPhrases) {
            content.append(line).append("\n");
        }

        return content.toString();
    }

    @Override
    public void stop() {
        OutputStream os = null;
        BufferedOutputStream bos = null;
        DataOutputStream dos = null;
        try {
            os = new FileOutputStream(BASE_PATH + "produits.mag");
            bos = new BufferedOutputStream(os);
            dos = new DataOutputStream(bos);

            for (AbstractProduit produit : ui.getProduitsDisponibles()) {
                produit.writeProduits(dos);
            }

        } catch (IOException e) {
            System.out.println("Impossible d'acceder au fichier");
        } finally {
            try {
                if (dos != null) {
                    dos.close();
                }
            } catch (IOException e) {
                System.out.println("Impossible d'acceder au fichier");
            }
        }


        ////////////////////////////////////////////////////////////////////////////////

        DataOutputStream oos2 = null;
        FileOutputStream os22 = null;
        BufferedOutputStream bos22 = null;

        try {
            os22 = new FileOutputStream(BASE_PATH + "vrac.mag");
            bos22 = new BufferedOutputStream(os22);
            oos2 = new DataOutputStream(bos22);
            ;

            for (AbstractProduit produit : vrac.getAllProduits()) {
                produit.writeProduits(oos2);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("impossibe d'aceder au fichier");
        } finally {
            try {
                if (oos2 != null) {
                    oos2.close();
                }
            } catch (IOException e) {
                System.out.println("Impossible d'acceder au fichier");
                e.printStackTrace();
            }
        }


        ////////////////////////////////////////////////////////////////////////////////////////

        ObjectOutputStream oos = null;
        FileOutputStream os2 = null;
        BufferedOutputStream bos2 = null;

        //ecrire les sections
        try {
            os2 = new FileOutputStream(BASE_PATH + "sections.obj");
            bos2 = new BufferedOutputStream(os2);
            oos = new ObjectOutputStream(bos2);

            for (AireI section : sections) {
                if (section.getAllProduits().size() > 0) {
                    oos.writeObject(section);
                }

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Impossible d'acceder au fichier");
        } finally {
            try {
                historique.ajouterEvenement("Enregistrement du contenu du magasin");
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
                System.out.println("Impossible d'acceder au fichier");
                e.printStackTrace();
            }
        }


        historique.ajouterEvenement("Fermeture de l’application");


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
