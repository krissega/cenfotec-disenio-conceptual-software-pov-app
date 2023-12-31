package com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.service;

import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.Alert;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.Document;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.Product;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.State;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.repository.logic.AlertDAO;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.repository.logic.DocumentDAO;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.repository.logic.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlertService {
    @Autowired
    private ProductDAO productDAO;
    @Autowired
    private DocumentDAO documentRepository;
    @Autowired
    private AlertDAO alertDAO;
    @Autowired
    ProductService productService;


    public Alert createAlert(Alert alert){
        return alertDAO.saveAlert(alert);
    }

    public int getLimitAmount() {
        return alertDAO.getLimitAmount();
    }

    public void alertNeeded(Document document){
        for (Product product : document.getProducts()) {
            Product productInStock = productService.getProduct(product.getId());
            if(productInStock.getQtyStock() <= 20){
                Alert alert = new Alert();
                alert.setReaded(false);
                alert.setMessage("Product "+productInStock.getName()+" has low inventory with "+productInStock.getQtyStock()+" items");
                this.createAlert(alert);
            }
        }
        if(this.getLimitAmount() >= 200000){
            Alert alert = new Alert();
            alert.setReaded(false);
            alert.setMessage("Documents have reached the limit amount of 200.000 for Pending status");
            this.createAlert(alert);
        }
        if(document.getState().equals(State.PAGADO)){
            Alert alert = new Alert();
            alert.setReaded(false);
            alert.setMessage("Document with id "+document.getId()+" has been paid, please confirm and approve if payment is correct.");
            this.createAlert(alert);
        }
    }


}
