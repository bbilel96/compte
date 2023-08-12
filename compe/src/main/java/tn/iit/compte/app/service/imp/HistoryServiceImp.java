package tn.iit.compte.app.service.imp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import tn.iit.compte.app.constant.ActionHistory;
import tn.iit.compte.app.exception.custom.AmountLowMuch;
import tn.iit.compte.app.exception.custom.ObjectNotFound;
import tn.iit.compte.app.model.Compte;
import tn.iit.compte.app.model.History;
import tn.iit.compte.app.repository.HistoryRepository;
import tn.iit.compte.app.service.HistoryService;
import tn.iit.compte.app.util.request.ResponseMessage;
import tn.iit.compte.app.util.request.constant.Behavior;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
public class HistoryServiceImp implements HistoryService {
    private final HistoryRepository historyRepository;
    private final CompteServiceImp compteServiceImp;
    /**
     * get All History of Compte per Page.
     * @param compteId id of compte.
     * @param page number of Page
     * @param size size number
     * @return page Of history
     */
    @Override
    public Page<History> getAll(String compteId, int page, int size) {
        Compte foundedCompte = this.compteServiceImp.getCompteById(compteId);
        log.info("Getting all History of compte id: {}", compteId);
        return this.historyRepository.findAllByCompteOrderByCreatedAtDesc(foundedCompte, PageRequest.of(page, size));
    }

    /**
     * add new History of specific compte.
     * @param history new history.
     * @param compteId id of compte.
     * @return Response message with Behavior Success.
     */
    @Override
    public ResponseMessage addHistory(History history, String compteId) {
        if (history.getAmount().compareTo(BigDecimal.ZERO)<=0){
            throw new AmountLowMuch("Amount should not be negative");
        }
        Compte foundedCompte = this.compteServiceImp.getCompteById(compteId);

        if (!foundedCompte.checkOverDraw(history)){
            if (history.getType().equals(ActionHistory.WITHDRAW)) {
                throw new AmountLowMuch("Your can't draw more money.");
            }
            else {
                throw new AmountLowMuch("Your can't depose more money.");
            }

        };
        log.info("Saving new History of compte: {}", compteId);

        historyRepository.save(history);
        if (history.getType().equals(ActionHistory.WITHDRAW)){
            return new ResponseMessage("Your withdraw has been saved successfully", Behavior.SUCCESS);
        }
        return new ResponseMessage("Your depose has been saved successfully", Behavior.SUCCESS);
    }
}
