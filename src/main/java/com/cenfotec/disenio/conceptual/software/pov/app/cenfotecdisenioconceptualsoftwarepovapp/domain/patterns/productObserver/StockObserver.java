package com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.patterns.productObserver;

import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.Document;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.Product;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.repository.ProductCrudRepository;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.util.ProductUtils;

import java.util.List;


public class StockObserver implements Observer {

    @Override
    public void update(Product product,  ProductCrudRepository productCrudRepository, int quantity) {
        // Actualizar la cantidad disponible en stock del producto
        int initialQtyAvailable = product.getQtyStock();
        int soldQty = quantity;
        int qtyAvailable = initialQtyAvailable - soldQty;
        product.setQtyStock(qtyAvailable);

        // Guardar los cambios en el producto (actualizar en la base de datos, por ejemplo)
        productCrudRepository.save(product);

    }


}
