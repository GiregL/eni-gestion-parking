package fr.eni.gestionParking.ihm;

import fr.eni.gestionParking.bo.Personne;
import javafx.util.StringConverter;

public class PersonneStringConverter extends StringConverter<Personne> {
    @Override
    public String toString(Personne personne) {
        return personne.getNom() + " " + personne.getPrenom();
    }

    @Override
    public Personne fromString(String s) {
        return null;
    }
}
