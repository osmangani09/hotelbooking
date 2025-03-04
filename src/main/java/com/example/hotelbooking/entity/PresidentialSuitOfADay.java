package com.example.hotelbooking.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Entity
@Table(name = "presidential_suit_ofaday")
@Setter
@Getter
public class PresidentialSuitOfADay {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column
    private Long Id;
    @Temporal(TemporalType.DATE)
    @Column
    private Date availableDate;
    @Column
    private Boolean reserved;
    @ManyToOne(optional = true)
    @JoinColumn(name="reservation_id", referencedColumnName="id")
    private Reservation reservation;
}
