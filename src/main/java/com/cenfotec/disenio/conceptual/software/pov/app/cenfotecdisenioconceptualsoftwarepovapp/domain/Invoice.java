package com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.time.LocalDateTime;
import java.util.List;

@Document(collection= "invoices")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {
    @Id
    private String id;
    private State state;
    private Boolean  approved;
    private DocumentType documentType;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime valid;
    private  String idDiscount;
    private  String idTax;
    private  User idUser;
   // private User user;
    private Tax tax;
    private Discount discount;
    private List<Item> products;
    private double total;


}
