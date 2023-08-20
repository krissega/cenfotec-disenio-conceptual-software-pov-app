package com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.patterns.TaxStrategy;

public class BillingService {
    private TaxStrategy taxStrategy;

    public BillingService(TaxStrategy taxStrategy) {
        this.taxStrategy = taxStrategy;
    }

    public double getFixedPercentage(double percentage) {
        double fixedPercentage = percentage/100;
        return fixedPercentage;
    }
}
