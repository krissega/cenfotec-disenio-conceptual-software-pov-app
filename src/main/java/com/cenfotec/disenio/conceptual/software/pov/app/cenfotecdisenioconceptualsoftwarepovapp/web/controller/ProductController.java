package com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.web.controller;


import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.Document;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.Product;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {



    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public List<Product> getAllProducts(HttpServletRequest request){

//        if (isAdmin(request)) {
//            return productProxyService.getAllProductsForAdmin();
//        } else {
//            //throw new UnauthorizedAccessException("Acceso denegado para usuarios no administradores");
//        }
        return productService.getAll();
    }

    @GetMapping("/show/{idProduct}")
    public Optional<Product> showProduct(@PathVariable("idProduct") int idProduct){
        return productService.showProduct(idProduct);
    }

//    @GetMapping("/list")
//    public List<Product> getAllProducts(){
//        return productService.getAll();
//    }

    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return new ResponseEntity<>(productService.save(product), HttpStatus.CREATED);
    }

    @GetMapping("/document/{idDocument}")
    public ResponseEntity <Document> getProductsByDocument(@PathVariable("idDocument") int idDocument){
        return new ResponseEntity<>(productService.getProductsByDocument(idDocument), HttpStatus.CREATED);
    }
}
