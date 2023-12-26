package com.example.workshift.persistence.shift;

import com.example.workshift.persistence.shop.ShopEntity;
import com.example.workshift.persistence.user.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Entity
@Table(name = "shifts")
@Data
@NoArgsConstructor
public class ShiftEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shift_generator")
    private Long id;

    @Version
    @Column(nullable = false)
    private Long version;

    @Column(name = "active_from", nullable = false)
    private Instant activeFrom;

    @Column(name = "active_to", nullable = false)
    private Instant activeTo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shop_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private ShopEntity shop;
}
