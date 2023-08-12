package tn.iit.compte.app.dto;

import lombok.*;
import tn.iit.compte.app.mapper.UserMapper;
import tn.iit.compte.app.model.Compte;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CompteDto {
    @EqualsAndHashCode.Include
    private String id;
    private BigDecimal maxBalance;
    private BigDecimal totalBalance;
    private String userId;
    private UserDto user = new UserDto();

    public CompteDto(Compte compte){
        this.id = compte.getId();
        this.maxBalance = compte.getMaxBalance();
        this.totalBalance = compte.getTotalBalance();
        this.userId = compte.getUser().getId();
        this.user = UserMapper.modelToDto(compte.getUser());
    }
}
