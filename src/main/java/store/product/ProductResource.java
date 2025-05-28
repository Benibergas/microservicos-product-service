package store.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import store.product.ProductController;
import store.product.ProductIn;
import store.product.ProductOut;

@RestController
public class ProductResource implements ProductController {

    @Autowired
    private ProductService productService;

    @Override
    public ResponseEntity<ProductOut> create(ProductIn productIn) {
        Product created = productService.create(ProductParser.to(productIn));
        return ResponseEntity.ok().body(ProductParser.to(created));
    }

    @Override
    public ResponseEntity<List<ProductOut>> findAll() {
        return ResponseEntity
            .ok()
            .body(productService.findAll().stream().map(ProductParser::to).toList());
    }

    @Override
    public ResponseEntity<ProductOut> findById(@PathVariable String id) {
        Product found = productService.findById(id);
        return ResponseEntity.ok(ProductParser.to(found));
    }

    @Override
    public ResponseEntity<String> deleteById(@PathVariable String id) {
        productService.deleteById(id);
        return ResponseEntity.noContent().build(); // HTTP 204
    }

}