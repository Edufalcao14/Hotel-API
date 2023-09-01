package io.github.edufalcao14.hotel.rest;

import io.github.edufalcao14.hotel.model.entity.Client;
import io.github.edufalcao14.hotel.model.repository.ClientRepository;
import io.github.edufalcao14.hotel.model.repository.OldClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api/clients")
public class ClientController {

    private final ClientRepository repository;
    private final OldClientRepository oldClientRepository;

    @Autowired
    public ClientController(@RequestBody ClientRepository repository ,@RequestBody OldClientRepository oldClientRepository){
        this.repository=repository;
        this.oldClientRepository=oldClientRepository;
    }

    @PostMapping("/checkIn")
    @ResponseStatus(HttpStatus.CREATED)
    public Client checkIn(@RequestBody Client client){

        return repository.save(client);
    }

    @GetMapping("/getClient={id}")
    public Client verifyClient (@PathVariable Integer id){
        return repository.
                findById(id).
                orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


    @DeleteMapping("/checkOut={id}")
    public void checkOut (@PathVariable Integer id){

        repository
                .findById(id)
                .map(client ->{
                    repository.delete(client);
                    return Void.TYPE;
                } ).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


}
