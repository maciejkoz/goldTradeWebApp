package spring.hibernate;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "Cars")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Cars implements HibernateEntity {

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "EmployeeId")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @NonNull
    private Employees employees;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "Brand")
    @OrderColumn
    @NonNull
    private String brand;

    @Column(name = "Model")
    @NonNull
    private String model;

    @Column(name = "RegistrationDate")
    @NonNull
    @DateTimeFormat(pattern = "dd-mm-yyyy")
    private Date registrationDate;


    public Cars() {
    }

}
