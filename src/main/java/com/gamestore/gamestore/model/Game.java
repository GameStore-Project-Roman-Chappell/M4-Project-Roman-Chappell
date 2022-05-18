package com.gamestore.gamestore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name="game")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Game {
    // FIELDS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="game_id")
    private Integer id;

    @NotNull(message = "Games must have a title.")
    @NotBlank(message = "Game title cannot be empty.")
    @Size(max = 50, message = "Game title must be less than 50 characters.")
    @Column(name="title")
    private String title;

    @NotNull(message = "Games must have an ESRB Rating.")
    @NotBlank(message = "ESRB Rating cannot be empty.")
    @Size(max = 50, message = "ESRB Rating must be less than 50 characters.")
    @Column(name="esrb_rating")
    private String esrbRating;


    @NotNull(message = "Games must have a description.")
    @Column(name="description")
    private String description;


    @NotNull(message = "Games must have a Studio.")
    @NotBlank(message = "Studio cannot be empty.")
    @Size(max = 50, message = "Studio name must be less than 50 characters.")
    @Column(name="studio")
    private String studio;


    @NotNull(message="Games must have a price.")
    @Min(value = 0, message="Price must be a positive value.")
    @Column(name="price", columnDefinition = "Decimal(5,2)")
    private BigDecimal price;


    @NotNull(message="Games must have a quantity.")
    @Min(value = 0, message = "Game Quantity can not be negative.")
    private Integer quantity;

    // CONSTRUCTORS - NO FIELD, ALL-BUT-ID, ALL FIELD

    public Game(){}

    public Game(String title, String esrbRating, String description, String studio, BigDecimal price, Integer quantity) {
        this.title = title;
        this.esrbRating = esrbRating;
        this.description = description;
        this.studio = studio;
        this.price = price;
        this.quantity = quantity;
    }

    public Game(Integer id, String title, String esrbRating, String description, String studio, BigDecimal price, Integer quantity) {
        this.id = id;
        this.title = title;
        this.esrbRating = esrbRating;
        this.description = description;
        this.studio = studio;
        this.price = price;
        this.quantity = quantity;
    }

    // HELPER METHODS
    public void addQuantity(Integer qty){
        this.quantity += qty;
    }
    public void removeQuantity(Integer qty){
        this.quantity -= qty;
    }

    // STANDARD METHODS - Equals, Hashcode, toString

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Objects.equals(id, game.id) && Objects.equals(title, game.title) && Objects.equals(esrbRating, game.esrbRating) && Objects.equals(description, game.description) && Objects.equals(studio, game.studio) && Objects.equals(price, game.price) && Objects.equals(quantity, game.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, esrbRating, description, studio, price, quantity);
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", esrbRating='" + esrbRating + '\'' +
                ", description='" + description + '\'' +
                ", studio='" + studio + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }


    // STANDARD METHODS - Getters and Setters
    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    public String getEsrbRating() {return esrbRating;}
    public void setEsrbRating(String esrbRating) {this.esrbRating = esrbRating;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public String getStudio() {return studio;}
    public void setStudio(String studio) {this.studio = studio;}

    public BigDecimal getPrice() {return price;}
    public void setPrice(BigDecimal price) {this.price = price;}

    public Integer getQuantity() {return quantity;}
    public void setQuantity(Integer quantity) {this.quantity = quantity;}
}