package tn.iit.compte.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tn.iit.compte.app.dto.HistoryDto;
import tn.iit.compte.app.mapper.HistoryMapper;
import tn.iit.compte.app.request.HistoryRequest;
import tn.iit.compte.app.request.action.Create;
import tn.iit.compte.app.service.HistoryService;
import tn.iit.compte.app.service.imp.HistoryServiceImp;
import tn.iit.compte.app.util.request.ResponseMessage;

@RestController
@RequestMapping("history")
@RequiredArgsConstructor

public class HistoryController {
    private final HistoryService historyServiceImp;

    @GetMapping("/{compteId}/{page}/{size}")
    public ResponseEntity<Page<HistoryDto>> getAllByCompteId(@PathVariable String compteId, @PathVariable int page, @PathVariable int size) {
        return new ResponseEntity<>(
                this.historyServiceImp.getAll(compteId, page, size)
                        .map(HistoryMapper::modelToDto),
                HttpStatus.OK
        );
    }

    @PostMapping("/{compteId}/")
    public ResponseEntity<ResponseMessage> create(@PathVariable String compteId, @RequestBody @Validated(Create.class) HistoryRequest historyRequest) {
        return new ResponseEntity<>(
                this.historyServiceImp.addHistory(
                        HistoryMapper.requestToModel(historyRequest)
                        , compteId
                ),
                HttpStatus.OK
        );
    }
}
