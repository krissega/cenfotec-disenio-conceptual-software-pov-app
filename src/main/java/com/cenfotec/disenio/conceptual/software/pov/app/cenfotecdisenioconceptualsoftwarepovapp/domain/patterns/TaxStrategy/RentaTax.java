package com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.patterns.TaxStrategy;

public class RentaTax implements TaxStrategy{

    @Override
    public double calculateTax(double amount) {
        double percentage = ((amount + 13 + 12.5)/100);
        return percentage;
    }
}
