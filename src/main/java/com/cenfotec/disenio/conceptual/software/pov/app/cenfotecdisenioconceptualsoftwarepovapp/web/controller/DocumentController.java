package com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.web.controller;

import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.Invoice;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.Item;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.Product;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.State;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.service.AlertService;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.service.DocumentService;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.service.ProductService;
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
    public ResponseEntity<List<Invoice>> getAllDocuments() {
        List<Invoice> invoices = new ArrayList<>();
        invoices = documentService.getAll();
        if (invoices.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        for (Invoice invoice : invoices) {
            logger.info("Products of Invoice " + invoice.getId()+"\n");
        }
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    @PostMapping(value = "/create", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Invoice> createDocument(@RequestBody Invoice invoice) {
        int quantity=0;
        for (Item product : invoice.getProducts()) {
            System.out.println("product: " + product.getProduct().getName());
            Product productInStock = productService.getProduct(product.getProduct().getId());
            quantity = product.getQtyToSale();
            if (quantity <= 0 || product == null) {
                throw new RuntimeException("Product with the following ID " + product.getProduct().getId() + " does not exists");
            }

            if (productInStock.getQtyStock() < quantity) {
                throw new RuntimeException("The following product: " + product.getProduct().getName() + " does not have enough items in the inventory");
            }
        }

        return new ResponseEntity<>(documentService.save(invoice), HttpStatus.CREATED);
    }


    @GetMapping("/listinvoice")
    public List<Invoice> getAllInvoices() {
        return documentService.getAllInvoices();
    }//Bills = Factura

    @GetMapping("/listticket")
    public List<Invoice> getAllTickets() {
        return documentService.getAllTickets();
    }//Ticket = tiquete de caja

    @GetMapping("/list/{idUser}")
    public List<Invoice> getDocumentsByUserId(@PathVariable("idUser") String  idUser) {
        return documentService.getAllByUser(idUser);
    }

    @GetMapping("/show/{idDocument}")
    public Invoice getDocumentsByDocumentId(@PathVariable("idDocument") String idDocument) {
        return documentService.getDocumentById(idDocument);
    }

    @GetMapping("/filterbystate/{idUser}/{state}")
    public List<Invoice> getDocumentsByUserAndState(@PathVariable("idUser") String idUser, @PathVariable("state") State state) {
        return documentService.getDocumentsByUserAndState(idUser, state);
    }

    @GetMapping("/filterbystate/{state}")
    public List<Invoice> getDocumentsByState(@PathVariable("state") State state) {
        return documentService.getDocumentsByState(state);
    }

    @GetMapping("/amount")
    public int getLimitAmount(@RequestBody Invoice invoice) {
        return alertService.getLimitAmount();
    }

    //@PostMapping(value = "/userpay", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)

    @PostMapping(value = "/userpay", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Invoice> payDocument(@RequestBody Invoice invoice) {
        return new ResponseEntity<>(documentService.updatePaymentState(invoice), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}/adminapprove", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Invoice> adminPaymentApproval(@PathVariable("id") String  documentId) {
        return new ResponseEntity<>(documentService.adminApprovePayment(documentId), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}/pay")
    public ResponseEntity<String> updateDocumentStateToPaid(@PathVariable("id") String  documentId) {
        Invoice invoice = documentService.getDocumentById(documentId);
        if (invoice == null) {
            return ResponseEntity.notFound().build();
        }

        invoice.setState(State.PAGADO);
        documentService.saveDocument(invoice);

        return ResponseEntity.ok("Invoice with ID " + documentId + " has been updated to PAGADO.");
    }

    @PutMapping(value = "/{id}/cancel")
    public ResponseEntity<String> updateDocumentStateToCancel(@PathVariable("id") String documentId, @RequestBody Map<String, String> requestBody) {
        System.out.println(requestBody.get("comment"));

        Invoice invoice = documentService.getDocumentById(documentId);
        String message = "";
        String comment = requestBody.get("comment");
        invoice.setComment(comment);
        if (invoice == null) {
            return ResponseEntity.notFound().build();
        }

        if(!invoice.getApproved()){
            invoice.setState(State.ANULADO);
            documentService.saveDocument(invoice);
            message = "Invoice with ID " + documentId + " has been updated to ANULADO.";
        }else{
            message = "Invoice cannot be canceled, has been already approved by the admin";
        }


        return ResponseEntity.ok(message);
    }

}