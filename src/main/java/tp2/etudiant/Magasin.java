package tp2.etudiant;

import tp2.application.AbstractProduit;
import tp2.echange.Descriptible;
import tp2.echange.Modele;
import tp2.etudiant.boite.Boite;
import tp2.etudiant.client.Achat;
import tp2.etudiant.client.Panier;
import tp2.etudiant.section.AireI;
import tp2.etudiant.section.Entrepot;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Magasin implements Modele {


    private Collection<Achat> achats = new ArrayList<>();

    private Panier panier;


    public Magasin() {
        // Instanciez les attributs nécessaires
        panier = new Panier();
    }


    @Override
    public Collection<AireI> getAllSections() {

        return null;
    }

    @Override
    public Descriptible getCharite() {
        return null;
    }

    @Override
    public int recevoirCommande(Collection<Boite> commande) {

        return -1;
    }

    public void placerProduits(Collection<Boite> boites, AireI section) {
        Iterator<Boite> iterator = boites.iterator();
        while (iterator.hasNext()){
            section.placerProduits(iterator.next());
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
        return null;
    }

}
