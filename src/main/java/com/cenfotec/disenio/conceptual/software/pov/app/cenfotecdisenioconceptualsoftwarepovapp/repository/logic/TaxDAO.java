package com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.repository.logic;

import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.Tax;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.repository.TaxCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TaxDAO {
    @Autowired
    private TaxCrudRepository taxCrudRepository;
    public List<Tax> getAll(){
        return taxCrudRepository.findAll();
    }

    public Optional<Tax> getTaxById(int taxId) {
        return taxCrudRepository.findById(taxId);
    }


}

