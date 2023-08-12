package tn.iit.compte.app.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tn.iit.compte.app.dto.CompteDto;
import tn.iit.compte.app.mapper.CompteMapper;
import tn.iit.compte.app.request.CompteRequest;
import tn.iit.compte.app.request.action.Create;
import tn.iit.compte.app.request.action.Update;
import tn.iit.compte.app.service.CompteService;
import tn.iit.compte.app.service.imp.CompteServiceImp;
import tn.iit.compte.app.util.request.ResponseMessage;
import tn.iit.compte.app.util.request.constant.Behavior;

@RestController
@RequestMapping("compte")
@Slf4j
@RequiredArgsConstructor
public class CompteController {
    private final CompteService compteServiceImp;

    @PostMapping("")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<ResponseMessage> createCompte(@RequestBody @Validated({Create.class}) CompteRequest compteRequest) {
        return new ResponseEntity<>(
                compteServiceImp.createCompte(
                        CompteMapper.requestToModel(compteRequest)
                ),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteCompte(@PathVariable String id) {
        return new ResponseEntity<>(
                compteServiceImp.deleteCompte(id),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompteDto> getCompteById(@PathVariable String id) {
        return new ResponseEntity<>(
                CompteMapper.modelToDto(
                        compteServiceImp.getCompteById(id)
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/{userId}/{page}/{size}")
    public ResponseEntity<Page<CompteDto>> getCompteByUserId(@PathVariable String userId, @PathVariable int page, @PathVariable int size) {
        return new ResponseEntity<>(
                compteServiceImp
                        .getCompteByUserId(userId, page, size)
                        .map(CompteMapper::modelToDto),
                HttpStatus.OK
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> updateCompte(@RequestBody @Validated({Update.class}) CompteRequest compteRequest, @PathVariable String id) {
        ResponseMessage responseMessage = compteServiceImp.updateCompte(CompteMapper.requestToModel(compteRequest), id);
                if (responseMessage.getBehavior().equals(Behavior.FAIL)){
                    return new ResponseEntity<>(
                        responseMessage, HttpStatus.BAD_REQUEST
                    );
                }
        return new ResponseEntity<>(
                responseMessage, HttpStatus.OK
        );
    }


}
