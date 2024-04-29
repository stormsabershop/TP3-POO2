package tp3.echange;

import tp3.etudiant.client.Achat;
import tp3.application.AbstractProduit;
import tp3.etudiant.boite.Boite;
import tp3.etudiant.section.AireI;
import tp3.etudiant.section.Entrepot;

import java.io.File;
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

    public Achat acheterPanier(String acheteur, LocalDateTime date, double rabaisGlobal);

    Collection<AbstractProduit> getContenuPanier();

    void retirerDuPanier(List<AbstractProduit> itemARemettre);

    Collection<Boite> getLivraisons();

    public void archive(File file);

    public void reconstruit(File file);

    void viderMagasin();

    String init(UI ui);

    void stop();
}
