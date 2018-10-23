package com.imta.cdi.service.model.model;


import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name="actualite")
public class ActualiteEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String titre;
    private String contenu;
    private String auteur;
    private Date date;

    protected ActualiteEntity() {}

    public ActualiteEntity(Long id, String titre, String contenu, String auteur, Date date) {
        this.id = id;
        this.titre = titre;
        this.contenu = contenu;
        this.auteur = auteur;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActualiteEntity actualiteEntity = (ActualiteEntity) o;
        return Objects.equals(id, actualiteEntity.id) &&
                Objects.equals(titre, actualiteEntity.titre) &&
                Objects.equals(contenu, actualiteEntity.contenu) &&
                Objects.equals(auteur, actualiteEntity.auteur) &&
                Objects.equals(date, actualiteEntity.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titre, contenu, auteur, date);
    }

    @Override
    public String toString() {
        return "ActualiteEntity{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", contenu='" + contenu + '\'' +
                ", auteur='" + auteur + '\'' +
                ", date=" + date +
                '}';
    }
}