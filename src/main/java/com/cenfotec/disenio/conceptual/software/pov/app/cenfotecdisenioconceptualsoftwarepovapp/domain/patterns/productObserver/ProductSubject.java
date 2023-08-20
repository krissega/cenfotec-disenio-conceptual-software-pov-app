package com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.patterns.productObserver;

import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.Product;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.repository.ProductCrudRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductSubject {
    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void updateProduct(Product productToInsert, ProductCrudRepository productCrudRepository, int qty) {
        // Lógica para actualizar el producto
        StockObserver observer = new StockObserver();
        observer.update(productToInsert, productCrudRepository,qty);

        // Notificar a los observadores
        for (Observer observing : observers) {
            observing.update(productToInsert,productCrudRepository,qty);
        }
    }
}