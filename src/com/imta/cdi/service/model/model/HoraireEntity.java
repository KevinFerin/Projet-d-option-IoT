package com.imta.cdi.service.model.model;



import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="horaire")
public class HoraireEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String contenu;

    protected HoraireEntity() {}

    public HoraireEntity(Long id, String contenu) {
        this.id = id;
        this.contenu = contenu;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HoraireEntity horaireEntity = (HoraireEntity) o;
        return (Objects.equals(id,horaireEntity.id)&&
                Objects.equals(contenu,horaireEntity.contenu));


    }

    @Override
    public int hashCode() {
        return Objects.hash(id, contenu);
    }

    @Override
    public String toString() {
        return "HoraireEntity{" +
                "id=" + id +
                ", contenu='" + contenu + '\'' +
                '}';
    }
}
