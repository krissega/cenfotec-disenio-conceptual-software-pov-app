package com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.web.controller;

import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.Document;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.Product;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.State;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.service.AlertService;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.service.DocumentService;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.service.ProductService;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.util.ProductUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/document")
public class DocumentController {
    @Autowired
    private DocumentService documentService;
    private final ProductService productService;
    @Autowired
    private AlertService alertService;

    private static final Logger logger = LoggerFactory.getLogger(DocumentController.class);
    public DocumentController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/all")
    public ResponseEntity<List<Document>> getAllDocuments() {
        List<Document> documents = new ArrayList<>();
        documents = documentService.getAll();
        if (documents.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        for (Document document : documents) {
            logger.info("Products of Document " + document.getId()+"\n");
        }
        return new ResponseEntity<>(documents, HttpStatus.OK);
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Document> createDocument(@RequestBody Document document) {
        int quantity=0;
        for (Product product : document.getProducts()) {
            System.out.println("product: " + product.getName());
            Product productInStock = productService.getProduct(product.getId());
            quantity = ProductUtils.getProductQty(document.getProducts(), product);
            if (quantity <= 0 || product == null) {
                throw new RuntimeException("Product with the following ID " + product.getId() + " does not exists");
            }

            if (productInStock.getQtyStock() < quantity) {
                throw new RuntimeException("The following product: " + product.getName() + " does not have enough items in the inventory");
            }
        }

        return new ResponseEntity<>(documentService.save(document), HttpStatus.CREATED);
    }


    @GetMapping("/listinvoice")
    public List<Document> getAllInvoices() {
        return documentService.getAllInvoices();
    }//Bills = Factura

    @GetMapping("/listticket")
    public List<Document> getAllTickets() {
        return documentService.getAllTickets();
    }//Ticket = tiquete de caja

    @GetMapping("/list/{idUser}")
    public List<Document> getDocumentsByUserId(@PathVariable("idUser") String  idUser) {
        return documentService.getAllByUser(idUser);
    }

    @GetMapping("/show/{idDocument}")
    public Document getDocumentsByDocumentId(@PathVariable("idDocument") String idDocument) {
        return documentService.getDocumentById(idDocument);
    }

    @GetMapping("/filterbystate/{idUser}/{state}")
    public List<Document> getDocumentsByUserAndState(@PathVariable("idUser") int idUser, @PathVariable("state") State state) {
        return documentService.getDocumentsByUserAndState(idUser, state);
    }

    @GetMapping("/filterbystate/{state}")
    public List<Document> getDocumentsByState(@PathVariable("state") State state) {
        return documentService.getDocumentsByState(state);
    }

    @GetMapping("/amount")
    public int getLimitAmount(@RequestBody Document document) {
        return alertService.getLimitAmount();
    }

    //@PostMapping(value = "/userpay", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)

    @PostMapping(value = "/userpay", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Document> payDocument(@RequestBody Document document) {
        return new ResponseEntity<>(documentService.updatePaymentState(document), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}/adminapprove", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Document> adminPaymentApproval(@PathVariable("id") String  documentId) {
        return new ResponseEntity<>(documentService.adminApprovePayment(documentId), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}/pay")
    public ResponseEntity<String> updateDocumentStateToPaid(@PathVariable("id") String  documentId) {
        Document document = documentService.getDocumentById(documentId);
        if (document == null) {
            return ResponseEntity.notFound().build();
        }

        document.setState(State.PAGADO);
        documentService.saveDocument(document);

        return ResponseEntity.ok("Document with ID " + documentId + " has been updated to PAGADO.");
    }

    @PutMapping(value = "/{id}/cancel")
    public ResponseEntity<String> updateDocumentStateToCancel(@PathVariable("id") String documentId, @RequestBody Map<String, String> requestBody) {
        System.out.println(requestBody.get("comment"));

        Document document = documentService.getDocumentById(documentId);
        String message = "";
        String comment = requestBody.get("comment");
        document.setComment(comment);
        if (document == null) {
            return ResponseEntity.notFound().build();
        }

        if(!document.getApproved()){
            document.setState(State.ANULADO);
            documentService.saveDocument(document);
            message = "Document with ID " + documentId + " has been updated to ANULADO.";
        }else{
            message = "Document cannot be canceled, has been already approved by the admin";
        }


        return ResponseEntity.ok(message);
    }

}