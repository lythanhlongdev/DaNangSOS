package com.capstone2.dnsos.models.main;

import jakarta.persistence.*;
import lombok.*;

@ToString
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "history_media")
public class HistoryMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image1")
    private String image1;

    @Column(name = "image2")
    private String image2;

    @Column(name = "image3")
    private String image3;

    @Column(name = "voice")
    private String voice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "history_id")
    private History history;

    @PrePersist
    private void oneCreate() {
        this.image1 = "";
        this.image2 = "";
        this.image3 = "";
        this.voice = "";
    }
}