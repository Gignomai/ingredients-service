package com.atanorchaos.ingredients.infrastructure.api.rest.controller;

import com.atanorchaos.ingredients.infrastructure.api.rest.adapter.IngredientRestAdapter;
import com.atanorchaos.ingredients.infrastructure.api.rest.model.IngredientDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.net.URI;

@RestController
@Log4j2
public class IngredientController {

    private final IngredientRestAdapter restAdapter;

    @Autowired
    public IngredientController(IngredientRestAdapter restAdapter) {
        this.restAdapter = restAdapter;
    }

    @PostMapping(path = "/ingredients", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<String>> postIngredient(
            final @RequestBody IngredientDTO ingredient,
            final UriComponentsBuilder uriComponentsBuilder) {

        return restAdapter.create(ingredient)
                .subscribeOn(Schedulers.single())
                .map(id -> ResponseEntity
                        .created(createLocationHeader(uriComponentsBuilder, id.toString()))
                        .body(id.toString()));
    }

    private URI createLocationHeader(final UriComponentsBuilder uriComponentsBuilder, final String id) {
        return uriComponentsBuilder.path("/ingredients")
                .pathSegment(id)
                .build()
                .toUri();
    }
}
