package com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.web.controller;

import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.Tax;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.service.TaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/tax")
public class TaxController {
    @Autowired
    private TaxService taxService;

    @GetMapping("/list")
    public List<Tax> getAllTaxes(HttpServletRequest request){
        return taxService.getAll();
    }

    @GetMapping("/show/{id}")
    public Tax getTax(@PathVariable("id") String  taxId){
        return taxService.getTax(taxId).get();
    }
}
