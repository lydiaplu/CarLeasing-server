package com.lydiaplullc.CarLeasing.model;

import com.lydiaplullc.CarLeasing.enums.RentalStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@Table(name = "rented_cars")
public class RentedCar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate startDate;

    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private RentalStatus status;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    // 这里在获得response时，出现了循环嵌套
//    @OneToOne(mappedBy = "rentedCar",
//            fetch = FetchType.LAZY,
//            cascade = CascadeType.ALL)
//    private Payment payment;
//
//    @OneToOne(mappedBy = "rentedCar",
//            fetch = FetchType.LAZY,
//            cascade = CascadeType.ALL)
//    private CarReview carReview;

    public RentedCar() {

    }
}
