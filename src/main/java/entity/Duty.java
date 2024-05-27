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
public class Duty extends BaseEntity<Integer> {
    @Column(unique = true,name = "duty_name")
    @Pattern(regexp = "[a-zA-Z]+")
    @NotNull(message = "duty name can not be null")
    String dutyName;
    @OneToMany(mappedBy = "duty",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    List<SubDuty> subDuties;
}
