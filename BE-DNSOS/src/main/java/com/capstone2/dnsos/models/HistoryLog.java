package com.capstone2.dnsos.models;

import com.capstone2.dnsos.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;


@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="HistoryLogs")
@IdClass(HistoryLogId.class)
public class HistoryLog  implements Serializable {

    // Lứu log thay đổi trạng thái lịch sử
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    @Id
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "history_id")
    private History history;

    @CreationTimestamp
    @Column(name = "change_time")
    private LocalDateTime changeTime;
}

