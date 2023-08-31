package com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.service;

import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.*;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.patterns.TaxStrategy.*;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.patterns.facturaDecorator.Factura;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.patterns.facturaDecorator.FacturaBase;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.patterns.facturaDecorator.FacturaDescuentosDecorator;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.patterns.facturaDecorator.FacturaImpuestosDecorator;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.repository.logic.DocumentDAO;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.repository.logic.UserDAO;
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




    public List<Invoice> getAll(){
        return documentRepository.getAll();
    }
    public Invoice getDocument(String idDocument){
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
    public Invoice save(Invoice invoice){
        double total = 0;
        int quantity=0;
        for (Item item : invoice.getItems()) {
            Product productInStock = productService.getProduct(item.getProduct().getId());
            quantity = item.getQty();
            total += productInStock.getPrice() * quantity;
        }

        //////Strategy for taxes
        TaxStrategy strategy = this.getTaxStrategy(invoice.getTax().getDescription()); //Define la estrategia
        BillingService billingService = new BillingService(strategy);
        //Calcula los porcentajes correctos basado en la estrategia del tipo de impuestos
        double fixedPercentage = billingService.getFixedPercentage(invoice.getTax().getPercentage());
        //////

        //Calular factura con impuestos usando decorator
        Factura facturaBase = new FacturaBase(total);
        Factura facturaConDescuentos = new FacturaDescuentosDecorator(facturaBase, (invoice.getDiscount().getPercentage())/100);
        Factura facturaConImpuestos = new FacturaImpuestosDecorator(facturaConDescuentos, (fixedPercentage));
        invoice.setTotal(facturaConImpuestos.calcularTotal());
        Invoice savedInvoice = documentRepository.saveDocument(invoice);
        //create alert
        alertService.alertNeeded(savedInvoice);
        return savedInvoice;
    }



    @Scheduled(fixedRate = 60000) // Se ejecuta cada minuto
    public void updateDocumentsState() {
        System.out.println("Tarea programada ejecutada.");
        LocalDateTime now = LocalDateTime.now();
        List<Invoice> invoices = documentRepository.getDocumentsByStateAndValidBefore(State.PENDIENTE_DE_PAGO, now);

        for (Invoice invoice : invoices) {
            invoice.setState(State.ANULADO);
            documentRepository.saveDocument(invoice);
            System.out.println("Se actualizó el estado de la factura: " + invoice.getId());
        }
    }
    public boolean deleteDocument(String idDocument){
        boolean deleted = false;
        if(getDocument(idDocument).getId().equals(idDocument)){
            documentRepository.deleteDocument(idDocument);
            return true;
        }else{
            return false;
        }
    }

    public List<Invoice> getAllInvoices() {
        return documentRepository.getAllInvoices();
    }

    public List<Invoice> getAllTickets() {
        return documentRepository.getAllInvoices();//user 1 = tickets
    }
    public List<Invoice> getAllByUser(String idUser){
        return documentRepository.getDocumentsByIdUser(idUser);
    }

    public List<Invoice> getDocumentsByUserAndState(String userId, State state){
        User user = userDAO.getUser(userId).get();

        return documentRepository.getDocumentsByIdUserAndState(user, state);
    }



    public Invoice adminApprovePayment(String documentId) {
        Invoice invoice = documentRepository.getDocument(documentId);
        invoice.setApproved(true);
        documentRepository.updateApprovePaymentAdmin(invoice);
        System.out.println("Factura: " + invoice.getId() +" ha sido aprobada por el administrador");
        return invoice;
    }

    public Invoice updatePaymentState(Invoice invoice){
        invoice.setState(State.PAGADO);
        Invoice updatedInvoice = documentRepository.updatePaymentState(invoice);
        //create alert
        alertService.alertNeeded(updatedInvoice);
        return updatedInvoice;
    }

    public Invoice getDocumentById(String id) {
        return documentRepository.findById(id);
    }

    public void saveDocument(Invoice invoice) {
        documentRepository.save(invoice);
    }


    public List<Invoice> getDocumentsByState(State state) {
        return documentRepository.getDocumentsByState(state);
    }
}
