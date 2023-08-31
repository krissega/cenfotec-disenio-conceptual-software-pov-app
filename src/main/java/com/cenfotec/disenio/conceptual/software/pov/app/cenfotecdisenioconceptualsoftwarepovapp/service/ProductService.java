package com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.service;

import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.Invoice;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.Product;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.repository.logic.DocumentDAO;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.repository.logic.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private DocumentDAO productByDocumentRepository;


    public List<Product> getAll(){
        return productDAO.getAll();
    }
    public Product getProduct(String  idProduct){
        return productDAO.getProduct(idProduct);
    }

    public Product save(Product product){
        return productDAO.saveProduct(product);
    }

    public boolean deleteProduct(String  idProduct){
        boolean deleted = false;
        if(getProduct(idProduct)!=null){
            productDAO.deleteProduct(idProduct);
            return true;
        }else{
            return false;
        }
    }


    public Invoice getProductsByDocument(String documentId){
        System.out.println("Vamos por aqui");
        return productByDocumentRepository.getDocument(documentId);
    }

    public Product showProduct(String  idProduct) {
        return productDAO.getProduct(idProduct);
    }
}
