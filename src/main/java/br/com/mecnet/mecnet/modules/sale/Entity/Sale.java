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

    @Column(name = "employee_id", nullable = false)
    private UUID employee_id;

    @Column(name = "client", nullable = false)
    private String client;

    @Column(name = "cpfClient", nullable = false)
    private String cpfClient;

    @Column(name = "price", nullable = false)
    private Float price = 0f;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "employee")
    private Employee employee;

    @JsonIgnore
    @OneToMany()
    @JoinColumn(name = "productsList")
    private List<SaleProduct> productsList = new ArrayList<>();

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Sale(Sale sale){
      this.id = sale.id;
      this.seller = sale.seller;
      this.employee_id  = sale.employee_id;
      this.client = sale.client;
      this.cpfClient = sale.cpfClient;
      this.price = sale.price;
      this.productsList = sale.productsList;
      this.employee = sale.employee;
      this.createdAt = sale.createdAt;
      this.updatedAt = sale.updatedAt;
    }
}
