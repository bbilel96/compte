package tn.iit.compte.app.service;

import org.springframework.data.domain.Page;
import tn.iit.compte.app.model.Compte;
import tn.iit.compte.app.util.request.ResponseMessage;

public interface CompteService {
    Page<Compte> getCompteByUserId (String userId, int page, int size);
    Compte getCompteById(String id);
    ResponseMessage createCompte(Compte compte);
    ResponseMessage updateCompte (Compte compte, String id);
    ResponseMessage deleteCompte(String id);
}
