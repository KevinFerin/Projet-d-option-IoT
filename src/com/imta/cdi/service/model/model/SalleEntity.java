package com.imta.cdi.service.model.model;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="salle")
public class SalleEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long idsalle;
    private Integer numerosalle;
    private String batimentsalle;

    protected SalleEntity(){}

    public SalleEntity(Long idsalle, Integer numerosalle, String batimentsalle) {
        this.idsalle = idsalle;
        this.numerosalle = numerosalle;
        this.batimentsalle = batimentsalle;
    }

    public Long getIdsalle() {
        return idsalle;
    }

    public String getBatimentsalle() {
        return batimentsalle;
    }

    public Integer getNumerosalle() {
        return numerosalle;
    }

    public void setIdsalle(Long idsalle) {
        this.idsalle = idsalle;
    }

    public void setNumerosalle(Integer numerosalle) {
        this.numerosalle = numerosalle;
    }

    public void setBatimentsalle(String batimentsalle) {
        this.batimentsalle = batimentsalle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalleEntity that = (SalleEntity) o;
        return Objects.equals(idsalle, that.idsalle) &&
                Objects.equals(numerosalle, that.numerosalle) &&
                Objects.equals(batimentsalle, that.batimentsalle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idsalle, numerosalle, batimentsalle);
    }

    @Override
    public String toString() {
        return "SalleEntity{" +
                "idsalle=" + idsalle +
                ", numerosalle=" + numerosalle +
                ", batimentsalle='" + batimentsalle + '\'' +
                '}';
    }
}
