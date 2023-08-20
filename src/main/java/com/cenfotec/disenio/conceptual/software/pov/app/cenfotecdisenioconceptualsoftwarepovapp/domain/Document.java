package com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@org.springframework.data.mongodb.core.mapping.Document(collection= "documents")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Document {
    private Integer id;
    private State state;
    private Boolean  approved;
    private DocumentType documentType;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime valid;
    private  Integer idDiscount;
    private  Integer idTax;
    private  User idUser;
    private User user;
    private Tax tax;
    private Discount discount;
    private List<Product> products;
    private double total;


}
