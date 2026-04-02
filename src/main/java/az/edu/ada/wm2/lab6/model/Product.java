package az.edu.ada.wm2.lab6.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String productName;
    private BigDecimal price;
    private LocalDate expirationDate;

@ManyToMany
@JoinTable(
    name = "product_category",
    joinColumns = @JoinColumn(name = "product_id"),
    inverseJoinColumns = @JoinColumn(name = "category_id")
)
private Set<Category> categories;


    // Constructors
    //public Product() {
    //}

    public Product(String productName, BigDecimal price, LocalDate expirationDate) {
        this.productName = productName;
        this.price = price;
        this.expirationDate = expirationDate;
    }

    public Product(UUID id, String productName, BigDecimal price, LocalDate expirationDate) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.expirationDate = expirationDate;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
    
    public static class ProductBuilder {
    public ProductBuilder categories(List<Category> categories) {
        this.categories = (categories == null)
                ? null
                : new java.util.HashSet<>(categories);
        return this;
    }
}

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", expirationDate=" + expirationDate +
                '}';
    }
}
