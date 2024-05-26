package entity;

import base.entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SoftDelete;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@SoftDelete
@Builder
@Entity
public class Duty extends BaseEntity<Integer> {
    @Pattern(regexp = "[a-zA-Z]+")
    @NotNull(message = "duty name can not be null")
    String dutyName;
    @OneToMany(mappedBy = "duty",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    List<SubDuty> subDuties;
}
