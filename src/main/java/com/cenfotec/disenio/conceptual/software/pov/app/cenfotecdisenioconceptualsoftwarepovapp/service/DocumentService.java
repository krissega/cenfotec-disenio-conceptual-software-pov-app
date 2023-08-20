package com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.service;

import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.Document;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.Product;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.State;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.User;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.patterns.TaxStrategy.*;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.patterns.facturaDecorator.Factura;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.patterns.facturaDecorator.FacturaBase;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.patterns.facturaDecorator.FacturaDescuentosDecorator;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.patterns.facturaDecorator.FacturaImpuestosDecorator;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.repository.logic.DocumentDAO;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.repository.logic.UserDAO;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.util.ProductUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class DocumentService {
    @Autowired
    private DocumentDAO documentRepository;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private ProductService productService;
    @Autowired
    private AlertService alertService;




    public List<Document> getAll(){
        return documentRepository.getAll();
    }
    public Document getDocument(int idDocument){
        return documentRepository.getDocument(idDocument);
    }

    private TaxStrategy getTaxStrategy(String taxName){
        TaxStrategy strategy =  null;
        switch (taxName) {
            case "IVA":
                strategy = new IvaTax();
                break;
            case "Renta":
                strategy = new RentaTax();
                break;
            case "Importaciones":
                strategy = new ImportacionTax();
                break;
            default:
                System.out.println("Unrecognized Tax type");
                break;
        }
        return strategy;
    }
    public Document save(Document document){
        double total = 0;
        int quantity=0;
        for (Product product : document.getProducts()) {
            Product productInStock = productService.getProduct(product.getId()).orElse(new Product());
            quantity = ProductUtils.getProductQty(document.getProducts(), product);
            total += productInStock.getPrice() * quantity;
        }

        //////Strategy for taxes
        TaxStrategy strategy = this.getTaxStrategy(document.getTax().getDescription()); //Define la estrategia
        BillingService billingService = new BillingService(strategy);
        //Calcula los porcentajes correctos basado en la estrategia del tipo de impuestos
        double fixedPercentage = billingService.getFixedPercentage(document.getTax().getPercentage());
        //////

        //Calular factura con impuestos usando decorator
        Factura facturaBase = new FacturaBase(total);
        Factura facturaConDescuentos = new FacturaDescuentosDecorator(facturaBase, (document.getDiscount().getPercentage())/100);
        Factura facturaConImpuestos = new FacturaImpuestosDecorator(facturaConDescuentos, (fixedPercentage));
        document.setTotal(facturaConImpuestos.calcularTotal());
        Document savedDocument = documentRepository.saveDocument(document);
        //create alert
        alertService.alertNeeded(savedDocument);
        return savedDocument;
    }



    @Scheduled(fixedRate = 60000) // Se ejecuta cada minuto
    public void updateDocumentsState() {
        System.out.println("Tarea programada ejecutada.");
        LocalDateTime now = LocalDateTime.now();
        List<Document> documents = documentRepository.getDocumentsByStateAndValidBefore(State.PENDIENTE_DE_PAGO, now);

        for (Document document : documents) {
            document.setState(State.ANULADO);
            documentRepository.saveDocument(document);
            System.out.println("Se actualizó el estado de la factura: " + document.getId());
        }
    }
    public boolean deleteDocument(int idDocument){
        boolean deleted = false;
        if(getDocument(idDocument).getId()==idDocument){
            documentRepository.deleteDocument(idDocument);
            return true;
        }else{
            return false;
        }
    }

    public List<Document> getAllInvoices() {
        return documentRepository.getAllInvoices();
    }

    public List<Document> getAllTickets() {
        return documentRepository.getDocumentsByIdUser(1);//user 1 = tickets
    }
    public List<Document> getAllByUser(int idUser){
        return documentRepository.getDocumentsByIdUser(idUser);
    }

    public List<Document> getDocumentsByUserAndState(int userId, State state){
        User user = userDAO.getUser(userId).get();

        return documentRepository.getDocumentsByIdUserAndState(user, state);
    }



    public Document adminApprovePayment(Integer documentId) {
        Document document = documentRepository.getDocument(documentId);
        document.setApproved(true);
        documentRepository.updateApprovePaymentAdmin(document);
        System.out.println("Factura: " + document.getId() +" ha sido aprobada por el administrador");
        return document;
    }

    public Document updatePaymentState(Document document){
        document.setState(State.PAGADO);
        Document updatedDocument = documentRepository.updatePaymentState(document);
        //create alert
        alertService.alertNeeded(updatedDocument);
        return updatedDocument;
    }

    public Document getDocumentById(Integer id) {
        return documentRepository.findById(id).orElse(null);
    }

    public void saveDocument(Document document) {
        documentRepository.save(document);
    }


    public List<Document> getDocumentsByState(State state) {
        return documentRepository.getDocumentsByState(state);
    }
}
