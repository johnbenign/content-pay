package com.task.threeline.content.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class BuyContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long buyerId;
    @ManyToOne
    private Content content;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
