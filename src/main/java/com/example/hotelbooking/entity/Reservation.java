package com.example.hotelbooking.entity;

import com.example.hotelbooking.utility.ReservationStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.UUID;



@Entity
@Table(name = "reservation")
@Setter
@Getter
public class Reservation {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column
    private Long Id;
    @Column
    private String email;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private Integer guestCount;
    @Temporal(TemporalType.DATE)
    @Column
    private Date startDate;
    @Temporal(TemporalType.DATE)
    @Column
    private Date endDate;
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(
            name = "uuid4",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID reservationId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ReservationStatus status;

}
