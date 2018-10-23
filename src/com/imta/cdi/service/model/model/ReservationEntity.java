package com.imta.cdi.service.model.model;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name="reservation")
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idreservation;
    private Long idsalle;
    private Date datedebut;
    private Date datefin;
    private String nomdereserve;

    protected ReservationEntity() {}

    public ReservationEntity(Long idreservation, Long idsalle, Date datedebut, Date datefin, String nomdereserve) {
        this.idreservation = idreservation;
        this.idsalle = idsalle;
        this.datedebut = datedebut;
        this.datefin = datefin;
        this.nomdereserve = nomdereserve;
    }

    public Long getIdreservation() {
        return idreservation;
    }

    public void setIdreservation(Long idreservation) {
        this.idreservation = idreservation;
    }

    public Long getIdsalle() {
        return idsalle;
    }

    public void setIdsalle(Long idsalle) {
        this.idsalle = idsalle;
    }

    public Date getDatedebut() {
        return datedebut;
    }

    public void setDatedebut(Date datedebut) {
        this.datedebut = datedebut;
    }

    public Date getDatefin() {
        return datefin;
    }

    public void setDatefin(Date datefin) {
        this.datefin = datefin;
    }

    public String getNomdereserve() {
        return nomdereserve;
    }

    public void setNomdereserve(String nomdereserve) {
        this.nomdereserve = nomdereserve;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationEntity that = (ReservationEntity) o;
        return Objects.equals(idreservation, that.idreservation) &&
                Objects.equals(idsalle, that.idsalle) &&
                Objects.equals(datedebut, that.datedebut) &&
                Objects.equals(datefin, that.datefin) &&
                Objects.equals(nomdereserve, that.nomdereserve);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idreservation, idsalle, datedebut, datefin, nomdereserve);
    }

    @Override
    public String toString() {
        return "ReservationEntity{" +
                "idreservation=" + idreservation +
                ", idsalle=" + idsalle +
                ", datedebut=" + datedebut +
                ", datefin=" + datefin +
                ", nomdereserve='" + nomdereserve + '\'' +
                '}';
    }
}
