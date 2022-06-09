package fr.eni.gestionParking.bo;

import java.util.Objects;

/**
 * Représentation d'une Personne
 */
public class Personne implements XMLSerializable<Personne> {

    private Integer id;
    private String nom;
    private String prenom;

    public Personne(Integer id, String nom, String prenom) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
    }

    public Personne() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Personne personne = (Personne) o;
        return Objects.equals(id, personne.id) && Objects.equals(nom, personne.nom) && Objects.equals(prenom, personne.prenom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, prenom);
    }

    @Override
    public String toString() {
        return this.getNom() + " " + this.getPrenom();
    }

    @Override
    public String toXML() {
        StringBuilder builder = new StringBuilder("<Personne>");
        builder.append("<ID>").append(this.getId() == null ? "" : this.getId()).append("</ID>");
        builder.append("<Nom>").append(this.nom).append("</Nom>");
        builder.append("<Prenom>").append(this.prenom).append("</Prenom>");
        builder.append("</Personne>");
        return builder.toString();
    }
}
