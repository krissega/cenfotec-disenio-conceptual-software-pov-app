package com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.patterns.TaxStrategy;

public class IvaTax implements TaxStrategy{

    @Override
    public double calculateTax(double amount) {
        double percentage = (13/100);
        return percentage;
    }
}
