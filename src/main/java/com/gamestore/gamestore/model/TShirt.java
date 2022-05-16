package com.gamestore.gamestore.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name="t_shirt")
public class TShirt {

    // FIELDS

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="t_shirt_id")
    private Integer id;

    @NotNull(message = "T-Shirt must have a size.")
    @NotBlank(message = "T-Shirt size cannot be empty.")
    @Size(max = 20, message = "T-Shirt size must be less than 20 characters.")
    @Column(name="size")
    private String size;

    @NotNull(message = "T-Shirt must have a color.")
    @NotBlank(message = "T-Shirt color cannot be empty.")
    @Size(max = 20, message = "T-Shirt color must be less than 20 characters.")
    @Column(name="color")
    private String color;

    @NotNull(message = "T-Shirt must have a description.")
    @Column(name="description")
    private String description;

    @NotNull(message="T-Shirt must have a price.")
    @Min(value = 0, message="Price must be a positive value.")
    @Column(name="price", columnDefinition = "Decimal(5,2)")
    private BigDecimal price;


    @NotNull(message="T-Shirt must have a quantity.")
    @Min(value = 0, message = "Game Quantity can not be negative.")
    private Integer quantity;

    // CONSTRUCTORS - NO FIELD, ALL-BUT-ID, ALL FIELD

    public TShirt(){}

    public TShirt(String size, String color, String description, BigDecimal price, Integer quantity) {
        this.size = size;
        this.color = color;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public TShirt(Integer id, String size, String color, String description, BigDecimal price, Integer quantity) {
        this.id = id;
        this.size = size;
        this.color = color;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    // STANDARD METHODS - Equals, Hashcode, toString

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TShirt tShirt = (TShirt) o;
        return Objects.equals(id, tShirt.id) && Objects.equals(size, tShirt.size) && Objects.equals(color, tShirt.color) && Objects.equals(description, tShirt.description) && Objects.equals(price, tShirt.price) && Objects.equals(quantity, tShirt.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, size, color, description, price, quantity);
    }


    // STANDARD METHODS - Getters and Setters

    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}

    public String getSize() {return size;}
    public void setSize(String size) {this.size = size;}

    public String getColor() {return color;}
    public void setColor(String color) {this.color = color;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public BigDecimal getPrice() {return price;}
    public void setPrice(BigDecimal price) {this.price = price;}

    public Integer getQuantity() {return quantity;}
    public void setQuantity(Integer quantity) {this.quantity = quantity;}
}