package com.gamestore.gamestore.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Objects;


@Entity
@Table(name="sales_tax_rate")
public class SalesTaxRate {

    @NotNull(message="Sales tax rate must have a state.")
    @Size(min = 2, max = 2, message = "Sales Tax state must be 2 characters.")
    @Column(name="state", unique = true)
    private String state;

    @NotNull(message="Sales tax rate must have a rate.")
    @Min(value = 0, message="Sales tax rate must be a positive value.")
    @Column(name="tax", columnDefinition = "Decimal(5,2)")
    private BigDecimal rate;

    public SalesTaxRate(){}

    public SalesTaxRate(String state, BigDecimal rate) {
        this.state = state;
        this.rate = rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalesTaxRate that = (SalesTaxRate) o;
        return Objects.equals(state, that.state) && Objects.equals(rate, that.rate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state, rate);
    }

    @Override
    public String toString() {
        return "SalesTaxRate{" +
                "state='" + state + '\'' +
                ", rate=" + rate +
                '}';
    }

    public String getState() {return state;}
    public void setState(String state) {this.state = state;}

    public BigDecimal getRate() {return rate;}
    public void setRate(BigDecimal rate) {this.rate = rate;}
}