package tn.iit.compte.app.util.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class RequestValidation {
    private String name;
    private String errorMessage;

}