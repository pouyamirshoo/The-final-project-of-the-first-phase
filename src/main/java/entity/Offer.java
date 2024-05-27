package entity;

import base.entity.BaseEntity;
import entity.enums.OfferCondition;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SoftDelete;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
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

    @PrePersist
    public void defaultValues(){
        if (offerCondition == null)
            offerCondition = OfferCondition.WAITING;
    }
}
