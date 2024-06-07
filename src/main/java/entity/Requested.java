package entity;

import base.entity.BaseEntity;
import jakarta.persistence.*;
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
@SoftDelete
@SuperBuilder
@Entity
public class Requested extends BaseEntity<Integer> {
    @OneToOne
    Expert expert;
    @ToString.Exclude
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    List<SubDuty> subDuties;
}
