package com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.patterns.facturaDecorator;

public class FacturaDescuentosDecorator extends FacturaDecorator{
    private double descuento;

    public FacturaDescuentosDecorator(Factura facturaDecorada,double descuento){
        super(facturaDecorada);
        this.descuento = descuento;
    }

    @Override
    public double calcularTotal(){
        double totalBase = super.calcularTotal();
        double totalConDescuento = totalBase - (totalBase * descuento);
        return totalConDescuento;
    }

}
