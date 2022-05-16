package com.gamestore.gamestore.model;


import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name="invoice")
public class Invoice {

    // FIELDS

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="invoice_id")
    private Integer id;

    @NotNull(message = "Invoice must have a customer name.")
    @NotBlank(message = "Invoice name cannot be empty.")
    @Size(max = 80, message = "Invoice name must be less than 80 characters.")
    @Column(name="name")
    private String name;

    @NotNull(message = "Invoice must have a street.")
    @NotBlank(message = "Invoice street cannot be empty.")
    @Size(max = 30, message = "Invoice street must be less than 30 characters.")
    @Column(name="street")
    private String street;

    @NotNull(message = "Invoice must have a city.")
    @NotBlank(message = "Invoice city cannot be empty.")
    @Size(max = 30, message = "Invoice city must be less than 30 characters.")
    @Column(name="city")
    private String city;

    @NotNull(message = "Invoice must have a state.")
    @NotBlank(message = "Invoice state cannot be empty.")
    @Size(min = 2, max = 2, message = "Invoice state must be 2 characters.")
    @Column(name="state")
    private String state;

    @NotNull(message = "Invoice must have a zipcode.")
    @NotBlank(message = "Invoice zipcode cannot be empty.")
    @Size(min = 5, max = 5, message = "Invoice zipcode must be less than 30 characters.")
    @Column(name="zipcode")
    private String zipcode;

    @NotNull(message = "Invoice must have an itemType.")
    @NotBlank(message = "Invoice itemType cannot be empty.")
    @Size(max = 20, message = "Invoice itemType must be less than 20 characters.")
    @Column(name="item_type")
    private String itemType;

    @NotNull(message = "Invoice must have an itemId.")
    @Min(value = 0, message = "Invoice itemId can not be negative.")
    private Integer itemId;

    @NotNull(message="Invoice must have a unitPrice.")
    @Min(value = 0, message="Invoice unitPrice must be a positive value.")
    @Column(name="unit_price", columnDefinition = "Decimal(5,2)")
    private BigDecimal unitPrice;

    @NotNull(message="Invoice must have a quantity.")
    @Min(value = 0, message = "Invoice quantity can not be negative.")
    private Integer quantity;

    @NotNull(message="Invoice must have a subtotal.")
    @Min(value = 0, message="Invoice subtotal must be a positive value.")
    @Column(name="subtotal", columnDefinition = "Decimal(5,2)")
    private BigDecimal subtotal;

    @NotNull(message="Invoice must have a tax.")
    @Min(value = 0, message="Invoice tax must be a positive value.")
    @Column(name="tax", columnDefinition = "Decimal(5,2)")
    private BigDecimal tax;

    @NotNull(message="Invoice must have a processingFee.")
    @Min(value = 0, message="Invoice processingFee must be a positive value.")
    @Column(name="processing_fee", columnDefinition = "Decimal(5,2)")
    private BigDecimal processingFee;

    @NotNull(message="Invoice must have a total.")
    @Min(value = 0, message="Invoice total must be a positive value.")
    @Column(name="total", columnDefinition = "Decimal(5,2)")
    private BigDecimal total;

    // CONSTRUCTORS - NO FIELD, ALL-BUT-ID, ALL FIELD
    public Invoice(){}

    public Invoice(String name, String street, String city, String state, String zipcode, String itemType, Integer itemId, BigDecimal unitPrice, Integer quantity, BigDecimal subtotal, BigDecimal tax, BigDecimal processingFee, BigDecimal total) {
        this.name = name;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.itemType = itemType;
        this.itemId = itemId;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.subtotal = subtotal;
        this.tax = tax;
        this.processingFee = processingFee;
        this.total = total;
    }

    public Invoice(Integer id, String name, String street, String city, String state, String zipcode, String itemType, Integer itemId, BigDecimal unitPrice, Integer quantity, BigDecimal subtotal, BigDecimal tax, BigDecimal processingFee, BigDecimal total) {
        this.id = id;
        this.name = name;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.itemType = itemType;
        this.itemId = itemId;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.subtotal = subtotal;
        this.tax = tax;
        this.processingFee = processingFee;
        this.total = total;
    }

    // STANDARD METHODS - Equals, Hashcode, toString


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return Objects.equals(id, invoice.id) && Objects.equals(name, invoice.name) && Objects.equals(street, invoice.street) && Objects.equals(city, invoice.city) && Objects.equals(state, invoice.state) && Objects.equals(zipcode, invoice.zipcode) && Objects.equals(itemType, invoice.itemType) && Objects.equals(itemId, invoice.itemId) && Objects.equals(unitPrice, invoice.unitPrice) && Objects.equals(quantity, invoice.quantity) && Objects.equals(subtotal, invoice.subtotal) && Objects.equals(tax, invoice.tax) && Objects.equals(processingFee, invoice.processingFee) && Objects.equals(total, invoice.total);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, street, city, state, zipcode, itemType, itemId, unitPrice, quantity, subtotal, tax, processingFee, total);
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", itemType='" + itemType + '\'' +
                ", itemId=" + itemId +
                ", unitPrice=" + unitPrice +
                ", quantity=" + quantity +
                ", subtotal=" + subtotal +
                ", tax=" + tax +
                ", processingFee=" + processingFee +
                ", total=" + total +
                '}';
    }

    // STANDARD METHODS - Getters and Setters

    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getStreet() {return street;}
    public void setStreet(String street) {this.street = street;}

    public String getCity() {return city;}
    public void setCity(String city) {this.city = city;}

    public String getState() {return state;}
    public void setState(String state) {this.state = state;}

    public String getZipcode() {return zipcode;}
    public void setZipcode(String zipcode) {this.zipcode = zipcode;}

    public String getItemType() {return itemType;}
    public void setItemType(String itemType) {this.itemType = itemType;}

    public Integer getItemId() {return itemId;}
    public void setItemId(Integer itemId) {this.itemId = itemId;}

    public BigDecimal getUnitPrice() {return unitPrice;}
    public void setUnitPrice(BigDecimal unitPrice) {this.unitPrice = unitPrice;}

    public Integer getQuantity() {return quantity;}
    public void setQuantity(Integer quantity) {this.quantity = quantity;}

    public BigDecimal getSubtotal() {return subtotal;}
    public void setSubtotal(BigDecimal subtotal) {this.subtotal = subtotal;}

    public BigDecimal getTax() {return tax;}
    public void setTax(BigDecimal tax) {this.tax = tax;}

    public BigDecimal getProcessingFee() {return processingFee;}
    public void setProcessingFee(BigDecimal processingFee) {this.processingFee = processingFee;}

    public BigDecimal getTotal() {return total;}
    public void setTotal(BigDecimal total) {this.total = total;}
}