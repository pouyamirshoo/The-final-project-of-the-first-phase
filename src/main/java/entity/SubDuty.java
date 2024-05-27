package entity;

import base.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SoftDelete;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@SoftDelete
@SuperBuilder
@Entity
public class SubDuty extends BaseEntity<Integer> {
    @Column(unique = true,name = "sub_duty_name")
    @Pattern(regexp = "[a-zA-Z]+")
    @NotNull(message = "sub duty name can not be null")
    String subDutyName;
    @NotNull(message = "sub duty price can not be null")
    Integer price;
    @Column(columnDefinition = "TEXT")
    @NotNull(message = "description most not null")
    String description;
    @ManyToOne
    Duty duty;
    @OneToMany(mappedBy = "subDuty",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    List<Order> orders;
}
