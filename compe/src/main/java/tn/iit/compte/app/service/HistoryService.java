package tn.iit.compte.app.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import tn.iit.compte.app.model.History;
import tn.iit.compte.app.util.request.ResponseMessage;

@Service
public interface HistoryService {
    Page<History> getAll(String compteId, int page, int size);
    ResponseMessage addHistory (History history, String compteId);
}
