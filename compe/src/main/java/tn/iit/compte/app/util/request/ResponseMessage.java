package tn.iit.compte.app.util.request;

import lombok.*;
import tn.iit.compte.app.util.request.constant.Behavior;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ResponseMessage {
    private String message;
    @EqualsAndHashCode.Include
    private Behavior behavior;
    private List<RequestValidation> validations = new ArrayList<>();

    public ResponseMessage(String message, Behavior behavior) {
        this.message = message;
        this.behavior = behavior;
    }
}
