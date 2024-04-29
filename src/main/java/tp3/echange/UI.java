package tp3.echange;

import tp3.application.AbstractProduit;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public interface UI {
    List<AbstractProduit> getProduitsDisponibles();

    void setProduitsDisponibles(List<AbstractProduit> produitsDisponibles);
}
