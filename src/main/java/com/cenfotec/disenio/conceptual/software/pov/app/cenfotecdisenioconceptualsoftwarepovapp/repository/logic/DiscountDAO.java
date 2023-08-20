package com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.repository.logic;

import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.Discount;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.repository.DiscountCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DiscountDAO {
    @Autowired
    private DiscountCrudRepository discountCrudRepository;
    public List<Discount> getAll(){
        return discountCrudRepository.findAll();
    }

    public Optional<Discount> getDiscountById(int discountId) {
        return discountCrudRepository.findById(discountId);
    }

}

