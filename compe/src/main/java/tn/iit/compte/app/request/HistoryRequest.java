package tn.iit.compte.app.request;

import lombok.*;
import tn.iit.compte.app.constant.ActionHistory;
import tn.iit.compte.app.request.action.Create;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class HistoryRequest {

    private String id;
    @NotNull(message = "Type is required.", groups = {Create.class})
    private ActionHistory type;
    @NotNull(message = "Amount is required.", groups = {Create.class})
    private BigDecimal amount;
    private LocalDateTime createdAt;
    @NotNull(message = "History must be affected to compte", groups = {Create.class})
    private String compteId;
}
