package com.gamestore.gamestore.model;


import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name="console")
public class Console {


    // FIELDS
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="console_id")
    private Integer id;

    @NotNull(message = "Console must have a model.")
    @NotBlank(message = "Console model cannot be empty.")
    @Size(max = 50, message = "Console model must be less than 50 characters.")
    @Column(name="model")
    private String model;

    @NotNull(message = "Console must have a manufacturer.")
    @NotBlank(message = "Console manufacturer cannot be empty.")
    @Size(max = 50, message = "Console manufacturer must be less than 50 characters.")
    @Column(name="manufacturer")
    private String manufacturer;


    @Size(max = 20, message = "Console memory amount must be less than 50 characters.")
    @Column(name="memory_amount", nullable = true)
    private String memoryAmount;

    @Size(max = 20, message = "Console processor must be less than 50 characters.")
    @Column(name="processor", nullable = true)
    private String processor;

    @NotNull(message="Console must have a price.")
    @Min(value = 0, message="Price must be a positive value.")
    @Column(name="price", columnDefinition = "Decimal(5,2)")
    private BigDecimal price;


    @NotNull(message="Console must have a quantity.")
    @Min(value = 0, message = "Console Quantity can not be negative.")
    private Integer quantity;

    // CONSTRUCTORS - NO FIELD, ALL-BUT-ID, ALL FIELD

    public Console(){}

    public Console(String model, String manufacturer, String memoryAmount, String processor, BigDecimal price, Integer quantity) {
        this.model = model;
        this.manufacturer = manufacturer;
        this.memoryAmount = memoryAmount;
        this.processor = processor;
        this.price = price;
        this.quantity = quantity;
    }

    public Console(Integer id, String model, String manufacturer, String memoryAmount, String processor, BigDecimal price, Integer quantity) {
        this.id = id;
        this.model = model;
        this.manufacturer = manufacturer;
        this.memoryAmount = memoryAmount;
        this.processor = processor;
        this.price = price;
        this.quantity = quantity;
    }

    // STANDARD METHODS - Equals, Hashcode, toString


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Console console = (Console) o;
        return Objects.equals(id, console.id) && Objects.equals(model, console.model) && Objects.equals(manufacturer, console.manufacturer) && Objects.equals(memoryAmount, console.memoryAmount) && Objects.equals(processor, console.processor) && Objects.equals(price, console.price) && Objects.equals(quantity, console.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, model, manufacturer, memoryAmount, processor, price, quantity);
    }

    @Override
    public String toString() {
        return "Console{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", memoryAmount='" + memoryAmount + '\'' +
                ", processor='" + processor + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }

    // STANDARD METHODS - Getters and Setters

    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}

    public String getModel() {return model;}
    public void setModel(String model) {this.model = model;}

    public String getManufacturer() {return manufacturer;}
    public void setManufacturer(String manufacturer) {this.manufacturer = manufacturer;}

    public String getMemoryAmount() {return memoryAmount;}
    public void setMemoryAmount(String memoryAmount) {this.memoryAmount = memoryAmount;}

    public String getProcessor() {return processor;}
    public void setProcessor(String processor) {this.processor = processor;}

    public BigDecimal getPrice() {return price;}
    public void setPrice(BigDecimal price) {this.price = price;}

    public Integer getQuantity() {return quantity;}
    public void setQuantity(Integer quantity) {this.quantity = quantity;}
}