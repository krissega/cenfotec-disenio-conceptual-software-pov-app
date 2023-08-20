package com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.util;

import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.Product;

import java.util.List;

public class ProductUtils {

    public static int getProductQty(List<Product> list, Product product){
        int qty=0;
        for (Product p :list) {
            if(p.getId()==product.getId()){
                qty++;
            }
        }
        return qty;
    }
}
