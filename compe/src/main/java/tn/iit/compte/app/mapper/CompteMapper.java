package tn.iit.compte.app.mapper;

import tn.iit.compte.app.dto.CompteDto;
import tn.iit.compte.app.model.Compte;
import tn.iit.compte.app.request.CompteRequest;

public class CompteMapper {
    public static CompteDto modelToDto(Compte compte) {
        return new CompteDto(compte);
    }

    public static Compte requestToModel(CompteRequest compteRequest) {
        return new Compte(compteRequest);
    }
}
