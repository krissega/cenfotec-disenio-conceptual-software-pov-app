package com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.service;

import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.Tax;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.repository.logic.TaxDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaxService {
    @Autowired
    private TaxDAO taxDAO;

    public List<Tax> getAll() {
        return taxDAO.getAll();
    }

    public Optional<Tax> getTax(int taxId) {
        return taxDAO.getTaxById(taxId);
    }
}

