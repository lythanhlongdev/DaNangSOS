package com.capstone2.dnsos.models.main;

import com.capstone2.dnsos.enums.Status;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;
import java.util.List;

@ToString
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
//@EntityListeners(HistoryChangeListener.class)
@Table(name = "histories")
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "note", length = 500)
    private String note;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    @ManyToOne
    @JoinColumn(name = "rescue_stations_id")
    private RescueStation rescueStation;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    //    @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinTable(
//            name = "history_rescues",
//            joinColumns = {@JoinColumn(name = "history_id")},
//            inverseJoinColumns = {@JoinColumn(name = "rescue_id")}
//    )
    @OneToMany(mappedBy = "history", fetch = FetchType.EAGER)
    private List<HistoryRescue> HistoryRescue;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
