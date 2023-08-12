package tn.iit.compte.app.service.imp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import tn.iit.compte.app.exception.custom.ObjectNotFound;
import tn.iit.compte.app.model.Compte;
import tn.iit.compte.app.model.User;
import tn.iit.compte.app.repository.CompteRepository;
import tn.iit.compte.app.service.CompteService;
import tn.iit.compte.app.service.UserService;
import tn.iit.compte.app.util.request.ResponseMessage;
import tn.iit.compte.app.util.request.constant.Behavior;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompteServiceImp implements CompteService {
    private final CompteRepository compteRepository;
    private final UserServiceImp userServiceImp;

    /**
     * Get compte by User id.
     *
     * @param userId id of the user.
     * @return Page of user.
     */
    @Override
    public Page<Compte> getCompteByUserId(String userId, int page, int size) {
        log.info("Get All user: {} comptes per page: {}, size: {} ", userId, page, size);
        return compteRepository.findAllByUsers(userId, PageRequest.of(page, size));
    }

    /**
     * get Compte by id.
     *
     * @param id of compte
     * @return Compte
     */
    @Override
    public Compte getCompteById(String id) {
        Compte compte = compteRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Compte with {}, does not existe in data base.", id);
                    throw new ObjectNotFound("Compte does not exist.");
                });
        log.info("Compte Found: {}", compte);
        return compte;
    }

    /**
     * Create new account.
     *
     * @param compte that will be added to database.
     * @return Response Message.
     */
    @Override
    public ResponseMessage createCompte(Compte compte) {
        if (compte.getMaxBalance().compareTo(BigDecimal.ZERO)<=0){
            throw new ObjectNotFound("Amount should not be negative");
        }
        log.info("Save new Compte: {}", compte);
        User foundedUser = this.userServiceImp.getUserById(compte.getUser().getId());
        compte.setUser(foundedUser);
        this.compteRepository.save(compte);
        return new ResponseMessage("Compte has been created successfully", Behavior.SUCCESS);
    }

    /**
     * Update Account by id.
     *
     * @param compte that contain new data.
     * @return Response message with behavior SUCCESS.
     */
    @Override
    public ResponseMessage updateCompte(Compte compte, String id) {
        Compte foundedCompte = this.getCompteById(id);

        boolean updated = foundedCompte.update(compte);

        log.info("update compte");
        if (updated) {
            this.compteRepository.save(foundedCompte);
            return new ResponseMessage("Compte has been updated successfully", Behavior.SUCCESS);

        }
        return new ResponseMessage("Compte does not updated please make sure that your maximum amount is greater then total amount.", Behavior.FAIL);
    }

    /**
     * delete Compte By id.
     *
     * @param id of Compte.
     * @return Response Message with behavior SUCCESS.
     */
    @Override
    public ResponseMessage deleteCompte(String id) {
        Compte foundedCompte = this.getCompteById(id);
        compteRepository.delete(foundedCompte);
        log.info("Compte has been deleted successfully.");
        return new ResponseMessage("Compte has been deleted successfully", Behavior.SUCCESS);
    }
}
