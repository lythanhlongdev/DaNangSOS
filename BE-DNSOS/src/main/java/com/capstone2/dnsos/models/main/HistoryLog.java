package com.capstone2.dnsos.models.main;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;


@ToString
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "history_logs")
public class HistoryLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Không áp dụng khi sử dụng @IdClass
    @Column(name = "log_id")
    private Long logId;

    @Column(name = "event_type")
    private String eventType;
    @Column(name = "field_name")
    private String fieldName;
    @Column(name = "old_value")
    private String oldValue;
    @Column(name = "new_value")
    private String newValue;
    @Column(name = "event_time")
    private LocalDateTime eventTime;

    @Column(name = "role")
    private String role;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "history_id")
    private History history;


    public HistoryLog(String fieldName, String oldValue, String newValue, String eventType, String role, History history) {
        this.eventType = eventType;
        this.fieldName = fieldName;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.role = role;
        this.history = history;
    }

    @PrePersist
    protected void onCreate() {
        eventTime = LocalDateTime.now();
    }

}

