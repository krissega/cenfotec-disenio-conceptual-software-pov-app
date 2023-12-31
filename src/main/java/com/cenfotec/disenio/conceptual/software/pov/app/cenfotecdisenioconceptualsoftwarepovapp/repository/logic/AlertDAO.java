package com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.repository.logic;

import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.Alert;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.Document;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.State;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.repository.AlertCrudRepository;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.repository.DocumentCrudRepository;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.repository.ProductCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class AlertDAO {
    @Autowired
    private ProductCrudRepository productCrudRepository;
    @Autowired
    private AlertCrudRepository alertCrudRepository;
    @Autowired
    private DocumentCrudRepository documentCrudRepository;
    public Alert saveAlert(Alert alert){
        return alertCrudRepository.save(alert);
    }

    public int getLimitAmount(){
        int totalPending=0;
        List<Document> allBeforeFilter =  documentCrudRepository.findAll();
        List<Document > pendingPayments = allBeforeFilter.stream()
                .filter(doc  ->  doc.getState().equals(State.PENDIENTE_DE_PAGO))
                .collect(Collectors.toList());

        for (Document pyvot: pendingPayments) {
            totalPending += (int) Math.round(pyvot.getTotal());
        }
        return totalPending;
    }

}
