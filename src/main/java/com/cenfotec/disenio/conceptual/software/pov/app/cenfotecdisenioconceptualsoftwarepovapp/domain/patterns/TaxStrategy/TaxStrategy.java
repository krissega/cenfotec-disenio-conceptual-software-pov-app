package com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.patterns.TaxStrategy;

import org.springframework.stereotype.Service;

@Service
public interface TaxStrategy {
    double calculateTax(double amount);
}
