package com.dgm.store.product.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Date;

@Entity
@Table (name = "tbl_product")
@Data
@AllArgsConstructor @NoArgsConstructor @Builder
public class Product {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "El nombre no debe estar vacio")
    private String name;
    private String description;

    @PositiveOrZero(message = "El stock no puede ser menor a cero")
    private Double stock;

    @PositiveOrZero(message = "El precio no puede ser menor a cero")
    private Double price;
    private String status;

    @NotNull(message = "La categoria no puede quedar vacia")
    @Column (name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "category_id")
    @JsonIgnoreProperties ({"hibernateLazyInitializer", "handler"})
    private Category category;
}
