package com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection= "alerts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Alert {

    @Id
    private Integer id;
    private String message;
    private Boolean  readed;
    public Integer getId() {
        return id;
    }


}
