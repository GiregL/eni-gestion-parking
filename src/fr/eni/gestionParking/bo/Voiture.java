package fr.eni.gestionParking.bo;

import java.util.Objects;

public class Voiture {

    private Integer id;
    private String nom;
    private String plaqueImmatriculation;

    private Personne personne;

    public Voiture(Integer id, String nom, String pi, Personne personne) {
        this.id = id;
        this.nom = nom;
        this.plaqueImmatriculation = pi;
        this.personne = personne;
    }

    public Voiture() {
    }

    public boolean isLinked() {
        return this.personne != null;
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

    public String getPlaqueImmatriculation() {
        return plaqueImmatriculation;
    }

    public void setPlaqueImmatriculation(String plaqueImmatriculation) {
        this.plaqueImmatriculation = plaqueImmatriculation;
    }

    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Voiture voiture = (Voiture) o;
        return Objects.equals(id, voiture.id) && Objects.equals(nom, voiture.nom) && Objects.equals(plaqueImmatriculation, voiture.plaqueImmatriculation) && Objects.equals(personne, voiture.personne);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, plaqueImmatriculation, personne);
    }

    @Override
    public String toString() {
        return "Voiture {" +
                "\n\tid=" + id +
                "\n\tnom='" + nom + '\'' +
                "\n\tpi='" + plaqueImmatriculation + '\'' +
                "\n\tpersonne=" + personne +
                '}';
    }
}
