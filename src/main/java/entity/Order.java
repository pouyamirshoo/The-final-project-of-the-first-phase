package entity;

import base.entity.BaseEntity;
import entity.enums.BestTime;
import entity.enums.OrderCondition;
import entity.enums.PaymentMethod;
import entity.enums.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SoftDelete;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@SoftDelete
@SuperBuilder
@Table(name = "customer_Order")
@Entity
public class Order extends BaseEntity<Integer> {
    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    Customer customer;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    SubDuty subDuty;
    @Column(name = "order_condition")
    @Enumerated(EnumType.STRING)
    OrderCondition orderCondition;
    @Column(name = "date_create_order")
    @NotNull
    @Temporal(TemporalType.DATE)
    Date dateCreatOrder;
    @NotNull(message = "you must enter price")
    int offerPrice;
    @Column(columnDefinition = "TEXT")
    @NotNull(message = "enter description")
    String description;
    @Column(name = "need_expert")
    @Future
    @NotNull
    @Temporal(TemporalType.DATE)
    Date needExpert;
    @Column(name = "best_time")
    @Enumerated(EnumType.STRING)
    @NotNull
    BestTime bestTime;
    @Column(name = "take_offer_limit")
    @Future
    @NotNull
    @Temporal(TemporalType.DATE)
    Date takeOfferLimit;
    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    PaymentMethod paymentMethod;
    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    PaymentStatus paymentStatus;
    @ToString.Exclude
    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    List<Offer> offers;

    @PrePersist
    public void defaultValues(){
        if (orderCondition == null) {
            orderCondition = OrderCondition.RECEIVING_OFFERS;
        }
        if (paymentMethod == null){
            paymentMethod = PaymentMethod.CASH;
        }
        if (paymentStatus == null){
            paymentStatus = PaymentStatus.UNPAID;
        }
    }
}
