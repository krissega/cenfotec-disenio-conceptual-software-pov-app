package com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.repository.logic;

import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.*;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.patterns.productObserver.ProductSubject;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.repository.DocumentCrudRepository;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.repository.ProductCrudRepository;
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

    public List<Invoice> getAll(){
        return documentCrudRepository.findAll();}
    public List<Invoice> getDocumentsByIdUser(String  idUser){return findDocumentsByUser(idUser);}
    public Invoice saveUser(Invoice invoice){return documentCrudRepository.save(invoice);}
    public void deleteDocument(String idDocument){documentCrudRepository.deleteById(idDocument);}

    public Invoice getDocument(String  idDocument) {
        return documentCrudRepository.findById(idDocument).get();
    }


    public Invoice saveDocument(Invoice invoice) {
        Invoice d = documentCrudRepository.save(invoice);

        List<Item> products = invoice.getProducts();
        for (Item product : products) {
            Product id =  productCrudRepository.findById(product.getProduct().getId()).get();
            Product productToInsert = id;
            updateProduct(productToInsert,productCrudRepository, product.getQtyToSale());
        }

        return d;
    }

    public Invoice updatePaymentState(Invoice invoice) {
        Invoice d = documentCrudRepository.save(invoice);
        return d;
    }

    public Invoice updateApprovePaymentAdmin(Invoice invoice){
        Invoice d = documentCrudRepository.save(invoice);
        return d;
    }

    public List<Invoice> getAllInvoices() {
        return documentCrudRepository.findAll();
    }

    public List<Invoice> getDocumentsByStateAndValidBefore(State state, LocalDateTime now){
        return findInvalidDocuments(state, now);
    }

    public Invoice findById(String  documentId) {
        return documentCrudRepository.findById(documentId).get();
    }


    public Invoice save(Invoice invoice) {
        return documentCrudRepository.save(invoice);
    }


    public List<Invoice> getDocumentsByIdUserAndState(User user, State pstate) {
        List<Invoice> allBeforeFilter =  documentCrudRepository.findAll();
        List<Invoice> invalidInvoice = allBeforeFilter.stream()
                .filter(doc ->  doc.getIdUser().getId().equals(user.getId()) && doc.getState().equals(pstate))
                .collect(Collectors.toList());
        return invalidInvoice;
    }

    public List<Invoice> getDocumentsByState(State state) {
        List<Invoice> allBeforeFilter =  documentCrudRepository.findAll();
        List<Invoice> invalidInvoice = allBeforeFilter.stream()
                .filter(doc ->  doc.getState().equals(state))
                .collect(Collectors.toList());
        return invalidInvoice;
    }

    public List<Invoice>findInvalidDocuments(State documetState, LocalDateTime now){
        List<Invoice> allBeforeFilter =  documentCrudRepository.findAll();
        List<Invoice> invalidInvoice = allBeforeFilter.stream()
                .filter(doc -> documetState .equals(doc.getState())&& doc.getValid().isBefore(now))
                .collect(Collectors.toList());
       return invalidInvoice;

    }
    public List<Invoice>findDocumentsByUser(String  userId){
        List<Invoice> allBeforeFilter =  documentCrudRepository.findAll();
        List<Invoice> invalidInvoice = allBeforeFilter.stream()
                .filter(doc ->  doc.getIdUser().getId().equals(userId))
                .collect(Collectors.toList());
        return invalidInvoice;

    }


}
