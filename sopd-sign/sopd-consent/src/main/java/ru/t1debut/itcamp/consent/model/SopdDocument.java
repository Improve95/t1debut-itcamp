package ru.t1debut.itcamp.consent.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "sopd_documents")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SopdDocument {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int version;

    private String key;

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;
}
