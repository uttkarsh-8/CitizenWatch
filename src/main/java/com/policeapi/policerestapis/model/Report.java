package com.policeapi.policerestapis.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reports")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String description;
    private String location;// will consider storing latitude and longitude
    private String type;
    private Date createdAt = new Date();
    private String imageUrl;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    private String status = "IN_PROGRESS";
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) { // Set createdAt only if it's not set already
            createdAt = new Date();
        }
    }
}
