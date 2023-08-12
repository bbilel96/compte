package tn.iit.compte.app.request;

import lombok.*;
import tn.iit.compte.app.request.action.Create;
import tn.iit.compte.app.request.action.Update;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CompteRequest {
    @NotNull(message = "Maximum balance must contain value.", groups = {Create.class, Update.class})
    private BigDecimal maxBalance;
    @NotNull(message = "Compte must be affected to user.", groups = {Create.class})
    private String userId;
}
