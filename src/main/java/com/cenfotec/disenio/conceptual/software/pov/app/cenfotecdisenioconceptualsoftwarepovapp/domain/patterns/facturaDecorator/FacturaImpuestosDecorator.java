package com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.patterns.facturaDecorator;

public class FacturaImpuestosDecorator extends FacturaDecorator{
    private double impuesto;

    public FacturaImpuestosDecorator(Factura facturaDecorada,double impuesto){
        super(facturaDecorada);
        this.impuesto = impuesto;
    }

    @Override
    public double calcularTotal(){
        double totalBase = super.calcularTotal();
        double totalConImpuestos = totalBase + (totalBase * impuesto);
        return totalConImpuestos;
    }
}
