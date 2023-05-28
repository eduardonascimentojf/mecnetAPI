package br.com.mecnet.mecnet.modules.sale.Entity;

import br.com.mecnet.mecnet.modules.employee.entity.Employee;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "TB_SALE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;

    @Column(name = "seller", nullable = false)
    private String seller;

    @Column(name = "client", nullable = false)
    private String client;

    @Column(name = "cpfClient", nullable = false)
    private String cpfClient;

    @Column(name = "price", nullable = false)
    private Float price = 0f;

    @Column(name = "isActive", nullable = false)
    private Boolean isActive = true;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee_id;

    @OneToMany(mappedBy = "sale_id")
    private List<SaleProduct> productsList = new ArrayList<>();

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


}
