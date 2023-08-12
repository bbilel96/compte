package tn.iit.compte.app.mapper;

import tn.iit.compte.app.dto.CompteDto;
import tn.iit.compte.app.dto.HistoryDto;
import tn.iit.compte.app.model.Compte;
import tn.iit.compte.app.model.History;
import tn.iit.compte.app.request.CompteRequest;
import tn.iit.compte.app.request.HistoryRequest;

public class HistoryMapper {
    public static HistoryDto modelToDto(History history) {
        return new HistoryDto(history);
    }

    public static History requestToModel(HistoryRequest historyRequest) {
        return new History(historyRequest);
    }
}
