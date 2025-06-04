package store.product;

import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;


@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Cacheable(value = "products", key = "#id")
    public Product findById(String id) {
        System.out.println("Buscando produto por ID do banco de dados: " + id);
        return productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with ID: " + id))
                .to();
    }


    @CacheEvict(value = "products", allEntries = true) 
    @CachePut(value = "products", key = "#result.id()") 
    public Product create(Product product) {
        
        System.out.println("Criando novo produto: " + product.name()); 
        Product savedProduct = productRepository.save(new ProductModel(product)).to();
        return savedProduct;
        
    }


    @Cacheable(value = "products", key = "'allProducts'")
    public List<Product> findAll() {

        System.out.println("Buscando todos os produtos do banco de dados.");
        return StreamSupport
                .stream(productRepository.findAll().spliterator(), false)
                .map(ProductModel::to)
                .toList();
    }


    
    @CacheEvict(value = "products", allEntries = true) 
    public String deleteById(String id) {

        System.out.println("Deletando produto com ID: " + id);
        ProductModel product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found for deletion with ID: " + id));
        productRepository.delete(product);
        return id;
    }
}