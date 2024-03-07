package tp2.echange;

import tp2.application.AbstractProduit;
import tp2.etudiant.boite.Boite;
import tp2.etudiant.client.Achat;
import tp2.etudiant.section.AireI;
import tp2.etudiant.section.Entrepot;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface Modele {

    public Collection<AireI> getAllSections();
    public
    Descriptible getCharite();

    public Entrepot getEntrepot();

    /**
     * Commande le nombre produit demandé et les place directement dans l'entrepôt
     *
     * @param boites les boites à entreposer
     * @return le nobre de boite qui n'on t pas pu être entreposée
     */
    public int recevoirCommande(Collection<Boite> boites);

    void placerProduits(Collection<Boite> selectedProduits, AireI sectionVisee);

    public void mettreDansPanier(Collection<AbstractProduit> items);

    public Achat acheterPanier(String acheteur, LocalDateTime date);

    Collection<AbstractProduit> getContenuPanier();

    void retirerDuPanier(List<AbstractProduit> itemARemettre);


    void init();
}
