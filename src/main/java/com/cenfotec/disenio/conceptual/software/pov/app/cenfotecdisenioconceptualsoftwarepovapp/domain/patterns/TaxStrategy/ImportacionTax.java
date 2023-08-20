package com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.patterns.TaxStrategy;

public class ImportacionTax implements TaxStrategy{

    @Override
    public double calculateTax(double amount) {
        double percentage = ((amount + 13)/100);
        return percentage;
    }
}
