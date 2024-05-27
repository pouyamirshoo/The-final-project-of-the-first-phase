package entity;

import base.entity.BaseEntity;
import entity.enums.BestTime;
import entity.enums.OrderCondition;
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
    @Column(name = "need_expert")
    @Future
    @NotNull
    @Temporal(TemporalType.DATE)
    Date needExpert;
    @Column(name = "best_time")
    @Enumerated(EnumType.STRING)
    @NotNull
    BestTime bestTime;
    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    List<Offer> offers;

    @PrePersist
    public void defaultValues(){
        if (orderCondition == null)
            orderCondition = OrderCondition.RECEIVING_OFFERS;
    }
}
