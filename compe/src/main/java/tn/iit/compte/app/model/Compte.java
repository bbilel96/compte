package tn.iit.compte.app.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;
import tn.iit.compte.app.config.generate.StringPrefixSequenceGenerator;
import tn.iit.compte.app.constant.ActionHistory;
import tn.iit.compte.app.request.CompteRequest;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Slf4j
@Table(name = "t_compte")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Compte implements Serializable {
    @Serial
    private static final long serialVersionUID = 2L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "compte_sequence")
    @GenericGenerator(name = "compte_sequence",
            strategy = "tn.iit.compte.app.config.generate.StringPrefixSequenceGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = StringPrefixSequenceGenerator.INCREMENT_PARAM, value = "50"),
                    @org.hibernate.annotations.Parameter(name = StringPrefixSequenceGenerator.VALUE_PREFIX_PARAMETER, value = "C_"),
                    @org.hibernate.annotations.Parameter(name = StringPrefixSequenceGenerator.NUMBER_FORMAT_DEFAULT, value = "%05d"),


            })
    @EqualsAndHashCode.Include
    private String id;
    @Column(nullable = false)
    private BigDecimal maxBalance;
    @Transient
    private BigDecimal totalBalance = BigDecimal.ZERO;
    @ManyToOne(
    )
    @JoinColumn(
            name= "user_id", referencedColumnName = "id",
            nullable = false, foreignKey = @ForeignKey(name = "user_FK")
    )
    private User user = new User();
    @OneToMany(
            mappedBy = "compte",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE}
    )
    @ToString.Exclude
    private Set<History> histories = new HashSet<>();

    public Compte(String id, BigDecimal maxBalance, User user) {
        this.id = id;
        this.maxBalance = maxBalance;
        this.totalBalance = BigDecimal.ZERO;
        this.user = user;
    }

    public Compte (CompteRequest compteRequest) {
        this.getUser().setId(compteRequest.getUserId());
        this.totalBalance = BigDecimal.ZERO;
        this.maxBalance = compteRequest.getMaxBalance();
    }
    public boolean update (Compte compte){
        if (this.totalBalance.compareTo(compte.maxBalance) > 0){
            return false;
        }
        this.maxBalance = compte.maxBalance;
        return true;
    }
    public boolean checkOverDraw (History history){
        if (history.getType().equals(ActionHistory.DEPOSE)){
            log.info("{}  {}",getTotalBalance().add(history.getAmount()), getTotalBalance().add(history.getAmount()).compareTo(maxBalance));
            return getTotalBalance().add(history.getAmount()).compareTo(maxBalance) <= 0;
        }
        else {
            log.info("{}", getTotalBalance().subtract(history.getAmount()).compareTo(BigDecimal.ZERO));
            return getTotalBalance().subtract(history.getAmount()).compareTo(BigDecimal.ZERO) >= 0;
        }

    }
    public BigDecimal getTotalBalance() {

        this.totalBalance = new BigDecimal(0);
        this.histories.forEach(history -> {
            if (history.getType() == ActionHistory.WITHDRAW){
                totalBalance = totalBalance.subtract(history.getAmount());
            }
            else {
                totalBalance =  totalBalance.add(history.getAmount());
            }
        });

       return this.totalBalance;
    }

}
