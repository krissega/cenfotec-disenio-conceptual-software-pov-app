package com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "taxes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tax {
    private Integer id;
    private String description;
    private double percentage;
    private List<Document> documents;
}
