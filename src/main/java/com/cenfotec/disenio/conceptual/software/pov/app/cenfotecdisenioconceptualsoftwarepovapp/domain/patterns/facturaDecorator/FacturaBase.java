package com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.patterns.facturaDecorator;

public class FacturaBase implements Factura{
    private double total;

    public FacturaBase(double total){
        this.total = total;
    }

    @Override
    public double calcularTotal(){
        return total;
    }
}
