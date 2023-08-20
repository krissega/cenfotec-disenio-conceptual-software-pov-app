package com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.patterns.facturaDecorator;

public abstract class FacturaDecorator implements Factura {
    protected Factura facturaDecorada;

    public FacturaDecorator(Factura facturaDecorada){
        this.facturaDecorada = facturaDecorada;
    }

    @Override
    public double calcularTotal(){
        return facturaDecorada.calcularTotal();
    }
}
