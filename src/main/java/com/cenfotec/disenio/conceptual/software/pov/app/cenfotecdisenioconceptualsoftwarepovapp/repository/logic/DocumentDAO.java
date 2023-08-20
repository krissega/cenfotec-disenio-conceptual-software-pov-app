package com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.repository.logic;

import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.*;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.patterns.productObserver.ProductSubject;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.repository.DocumentCrudRepository;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.repository.ProductCrudRepository;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.util.ProductUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class DocumentDAO {
    @Autowired
    private DocumentCrudRepository documentCrudRepository;


    @Autowired
    private ProductCrudRepository productCrudRepository;
    @Autowired
    private ProductSubject productSubject;


    public DocumentDAO(ProductSubject productSubject) {
        this.productSubject = productSubject;
    }

    public void updateProduct(Product product, ProductCrudRepository productCrudRepository,int qty) {
        productSubject.updateProduct(product, productCrudRepository,qty);
    }

    public List<Document> getAll(){return documentCrudRepository.findAll();}
    public List<Document> getDocumentsByIdUser(String  idUser){return findDocumentsByUser(idUser);}
    public Document saveUser(Document document){return documentCrudRepository.save(document);}
    public void deleteDocument(String idDocument){documentCrudRepository.deleteById(idDocument);}

    public Document getDocument(String  idDocument) {
        return documentCrudRepository.findById(idDocument);
    }


    public Document saveDocument(Document document) {
        Document d = documentCrudRepository.save(document);

        List<Product> products = document.getProducts();
        for (Product product : products) {
            Product id =  productCrudRepository.findById(product.getId());
            Product productToInsert = id;
            updateProduct(productToInsert,productCrudRepository, ProductUtils.getProductQty(products,product));
        }

        return d;
    }

    public Document updatePaymentState(Document document) {
        Document d = documentCrudRepository.save(document);
        return d;
    }

    public Document updateApprovePaymentAdmin(Document document){
        Document d = documentCrudRepository.save(document);
        return d;
    }

    public List<Document> getAllInvoices() {
        return documentCrudRepository.findAll();
    }

    public List<Document> getDocumentsByStateAndValidBefore(State state, LocalDateTime now){
        return findInvalidDocuments(state, now);
    }

    public Document findById(String  documentId) {
        return documentCrudRepository.findById(documentId);
    }


    public Document save(Document document) {
        return documentCrudRepository.save(document);
    }


    public List<Document> getDocumentsByIdUserAndState(User user, State pstate) {
        List<Document> allBeforeFilter =  documentCrudRepository.findAll();
        List<Document> invalidDocument = allBeforeFilter.stream()
                .filter(doc ->  doc.getIdUser().getId().equals(user.getId()) && doc.getState().equals(pstate))
                .collect(Collectors.toList());
        return invalidDocument;
    }

    public List<Document> getDocumentsByState(State state) {
        List<Document> allBeforeFilter =  documentCrudRepository.findAll();
        List<Document> invalidDocument = allBeforeFilter.stream()
                .filter(doc ->  doc.getState().equals(state))
                .collect(Collectors.toList());
        return invalidDocument;
    }

    public List<Document>findInvalidDocuments(State documetState, LocalDateTime now){
        List<Document> allBeforeFilter =  documentCrudRepository.findAll();
        List<Document> invalidDocument = allBeforeFilter.stream()
                .filter(doc -> documetState .equals(doc.getState())&& doc.getValid().isBefore(now))
                .collect(Collectors.toList());
       return invalidDocument;

    }
    public List<Document>findDocumentsByUser(String  userId){
        List<Document> allBeforeFilter =  documentCrudRepository.findAll();
        List<Document> invalidDocument = allBeforeFilter.stream()
                .filter(doc ->  doc.getIdUser().getId().equals(userId))
                .collect(Collectors.toList());
        return invalidDocument;

    }


}
