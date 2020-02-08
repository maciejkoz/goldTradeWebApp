package spring.hibernate;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Printers")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Printers implements HibernateEntity {

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "Employees_Printers",
            joinColumns = {@JoinColumn(name = "printerId", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "employeeId", referencedColumnName = "ID")})


    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Employees> employeesSet;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    @Column(name = "ID")
    private int id;



    @Column(name = "Brand")
    @NonNull
    private String brand;

    @Column(name = "Model")
    @NonNull
    private String model;


    public void addEmployee(Employees employee) {
        employeesSet.add(employee);
        employee.getPrintersSet().add(this);
    }

    public void removeEmployee(Employees employee) {
        employeesSet.remove(employee);
        employee.getPrintersSet().remove(this);
    }

    public Set<Employees> getEmployeesSet() {
        if (employeesSet == null) {
            employeesSet = new HashSet<>();
        }
        return employeesSet;
    }

    public Printers() {
    }

    public Printers(Set<Employees> employeesSet, @NonNull String brand, @NonNull String model) {
        this.employeesSet = employeesSet;
        this.brand = brand;
        this.model = model;

    }

    public Printers(int id, @NonNull String brand, @NonNull String model) {
        this.id = id;
        this.brand = brand;
        this.model = model;

    }
}

