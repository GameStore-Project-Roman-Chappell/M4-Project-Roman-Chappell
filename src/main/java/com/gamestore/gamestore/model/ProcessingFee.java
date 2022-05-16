package com.gamestore.gamestore.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name="processing_fee")
public class ProcessingFee {

    @Id
    @NotNull(message = "Processing Fee must have a productType.")
    @NotBlank(message = "Processing Fee productType cannot be empty.")
    @Size(max = 20, message = "Processing Fee productType must be less than 20 characters.")
    @Column(name="product_type", unique = true)
    private String productType;

    @NotNull(message="Processing Fee must have a fee.")
    @Min(value = 0, message="Processing Fee fee must be a positive value.")
    @Column(name="fee", columnDefinition = "Decimal(5,2)")
    private BigDecimal fee;

    public ProcessingFee(){}

    public ProcessingFee( String productType, BigDecimal fee) {
        this.productType = productType;
        this.fee = fee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcessingFee that = (ProcessingFee) o;
        return Objects.equals(productType, that.productType) && Objects.equals(fee, that.fee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productType, fee);
    }

    @Override
    public String toString() {
        return "ProcessingFee{" +
                "productType='" + productType + '\'' +
                ", fee=" + fee +
                '}';
    }

    public String getProductType() {return productType;}
    public void setProductType(String productType) {this.productType = productType;}

    public BigDecimal getFee() {return fee;}
    public void setFee(BigDecimal fee) {this.fee = fee;}
}