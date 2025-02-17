package entity;

import base.entity.BaseEntity;
import entity.enums.OfferCondition;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString(callSuper = true)
@SoftDelete
@SuperBuilder
@Entity
public class Offer extends BaseEntity<Integer> {
    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    Expert expert;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    Order order;
    @Column(name = "offer_condition")
    @Enumerated(EnumType.STRING)
    OfferCondition offerCondition;
    @Column(name = "offer_price")
    @NotNull(message = "offer price can not be null")
    Integer offerPrice;
    @Column(name = "take_long")
    @NotNull(message = "takeLong can not be null")
    Integer takeLong;
    @Column(name = "delay_days")
    Integer delayDays;
    @Column(name = "creat_Offer_Date", updatable = false)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    Date creatOfferDate;
    @Column(name = "update_Offer_Date")
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    Date updateOfferDate;

    @PrePersist
    public void defaultValues() {
        if (offerCondition == null) {
            offerCondition = OfferCondition.WAITING;
        }
        if (delayDays == null) {
            delayDays = 0;
        }
    }
}
