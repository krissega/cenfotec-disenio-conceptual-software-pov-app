package com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@org.springframework.data.mongodb.core.mapping.Document(collection= "plain_documents")
@Data
@AllArgsConstructor
@NoArgsConstructor


public class PlainDocument {
    private Integer id;
    private State state;
    private Boolean  approved;
    private DocumentType documentType;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime valid;
    private Tax tax;
    private Discount discount;
    private User user;
    private double total;

}
