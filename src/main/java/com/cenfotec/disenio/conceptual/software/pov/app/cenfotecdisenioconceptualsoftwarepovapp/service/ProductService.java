package com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.service;

import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.Document;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.Product;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.repository.logic.DocumentDAO;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.repository.logic.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private DocumentDAO productByDocumentRepository;


    public List<Product> getAll(){
        return productRepository.getAll();
    }
    public Optional<Product> getProduct(int idProduct){
        return productRepository.getProduct(idProduct);
    }

    public Product save(Product product){
        return productRepository.saveProduct(product);
    }

    public boolean deleteProduct(int idProduct){
        boolean deleted = false;
        if(getProduct(idProduct).isPresent()){
            productRepository.deleteProduct(idProduct);
            return true;
        }else{
            return false;
        }
    }


    public Document getProductsByDocument(int documentId){
        System.out.println("Vamos por aqui");
        return productByDocumentRepository.getDocument(documentId);
    }

    public Optional<Product> showProduct(int idProduct) {
        return productRepository.findById(idProduct);
    }
}
