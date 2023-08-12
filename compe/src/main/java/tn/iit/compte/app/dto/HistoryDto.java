package tn.iit.compte.app.dto;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import tn.iit.compte.app.constant.ActionHistory;
import tn.iit.compte.app.model.History;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class HistoryDto {
    private String id;
    private ActionHistory Type;
    private BigDecimal amount;
    private LocalDateTime createdAt;

    public HistoryDto(History history) {
       this.id = history.getId();
       this.Type = history.getType();
       this.amount = history.getAmount();
       this.createdAt = history.getCreatedAt();
    }
}
