package tn.iit.compte.app.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import tn.iit.compte.app.config.generate.StringPrefixSequenceGenerator;
import tn.iit.compte.app.constant.ActionHistory;
import tn.iit.compte.app.request.HistoryRequest;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "t_history")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class History implements Serializable {
    @Serial
    private static final long serialVersionUID = 3L;
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "history_sequence")
    @GenericGenerator(name = "history_sequence",
            strategy = "tn.iit.compte.app.config.generate.StringPrefixSequenceGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = StringPrefixSequenceGenerator.INCREMENT_PARAM, value = "50"),
                    @org.hibernate.annotations.Parameter(name = StringPrefixSequenceGenerator.VALUE_PREFIX_PARAMETER, value = "C_"),
                    @org.hibernate.annotations.Parameter(name = StringPrefixSequenceGenerator.NUMBER_FORMAT_DEFAULT, value = "%05d"),


            })
    private String id;
    private ActionHistory type;
    private BigDecimal amount;
    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    @ManyToOne(
    )
    @JoinColumn(
            name= "compte_id", referencedColumnName = "id",
            nullable = false, foreignKey = @ForeignKey(name = "compte_FK")
    )
    private Compte compte = new Compte();
    public History (HistoryRequest historyRequest){
        this.amount = historyRequest.getAmount();
        this.type = historyRequest.getType();
        this.compte.setId(historyRequest.getCompteId()) ;
    }
}
