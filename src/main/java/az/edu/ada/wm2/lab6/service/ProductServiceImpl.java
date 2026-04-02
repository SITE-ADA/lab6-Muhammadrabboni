package az.edu.ada.wm2.lab6.service;

import az.edu.ada.wm2.lab6.model.Product;
import az.edu.ada.wm2.lab6.model.Category;
import az.edu.ada.wm2.lab6.model.dto.ProductRequestDto;
import az.edu.ada.wm2.lab6.model.dto.ProductResponseDto;
import az.edu.ada.wm2.lab6.model.mapper.ProductMapper;
import az.edu.ada.wm2.lab6.repository.ProductRepository;
import az.edu.ada.wm2.lab6.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository,
                              ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductResponseDto createProduct(ProductRequestDto dto) {
        Product product = productMapper.toEntity(dto);

        List<Category> categories = categoryRepository.findAllById(dto.getCategoryIds());
        product.setCategories(new java.util.HashSet<>(categories));

        return productMapper.toResponseDto(productRepository.save(product));
    }

    @Override
    public ProductResponseDto getProductById(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return productMapper.toResponseDto(product);
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toResponseDto)
                .toList();
    }

    @Override
    public ProductResponseDto updateProduct(UUID id, ProductRequestDto dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setProductName(dto.getProductName());
        product.setPrice(dto.getPrice());
        product.setExpirationDate(dto.getExpirationDate());

        List<Category> categories = categoryRepository.findAllById(dto.getCategoryIds());
        product.setCategories(new java.util.HashSet<>(categories));

        return productMapper.toResponseDto(productRepository.save(product));
    }

    @Override
    public void deleteProduct(UUID id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductResponseDto> getProductsExpiringBefore(LocalDate date) {
        return productRepository.findAll()
                .stream()
                .filter(p -> p.getExpirationDate() != null &&
                             p.getExpirationDate().isBefore(date))
                .map(productMapper::toResponseDto)
                .toList();
    }

    @Override
    public List<ProductResponseDto> getProductsByPriceRange(BigDecimal min, BigDecimal max) {
        return productRepository.findAll()
                .stream()
                .filter(p -> p.getPrice() != null &&
                             p.getPrice().compareTo(min) >= 0 &&
                             p.getPrice().compareTo(max) <= 0)
                .map(productMapper::toResponseDto)
                .toList();
    }
}
