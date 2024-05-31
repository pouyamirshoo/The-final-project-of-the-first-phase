package entity;

import entity.enums.ExpertCondition;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SoftDelete;
import util.validation.ValidationCode;

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
public class Expert extends Person{
    @Column(unique = true,name = "national_code")
    @NotNull(message = "Expert national code can not be null")
    @ValidationCode
    String nationalCode;
    @Column(name = "expert_image")
    @Lob
    @Size(max = 300)
    @NotEmpty(message = "must upload an image")
    byte[] expertImage;
    @Column(name = "expert_condition")
    @Enumerated(EnumType.STRING)
    ExpertCondition expertCondition;
    @Column(columnDefinition = "TEXT",name = "reject_reason")
    String rejectReason;
    @Max(5)
    Integer rate;
    Integer balance;
    @ManyToMany(cascade = CascadeType.MERGE)
    List<SubDuty> subDuties;
    @OneToMany(mappedBy = "expert",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    List<Offer> offers;

    @PrePersist
    public void defaultValues(){
        if (expertCondition == null){
            expertCondition = ExpertCondition.AWAITING;
        }
        if (balance == null){
            balance = 0;
        }
        if (rate == null) {
            rate = 0;
        }
    }

}
