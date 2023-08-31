package com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection= "discounts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Discount {

    @Id
    private String  id;
    private String description;
    private double percentage;
    private List<Document> documents;

}
