package com.atanorchaos.ingredients.infrastructure.api.rest.controller;

import com.atanorchaos.ingredients.infrastructure.api.rest.adapter.IngredientRestAdapter;
import com.atanorchaos.ingredients.infrastructure.api.rest.model.IngredientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@RestController
public class IngredientController {

    private IngredientRestAdapter restAdapter;

    @Autowired
    public IngredientController(IngredientRestAdapter restAdapter) {
        this.restAdapter = restAdapter;
    }

    @PostMapping(path = "/ingredients", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> postIngredient(@RequestBody IngredientDTO ingredient,
                                       UriComponentsBuilder uriComponentsBuilder) {
        return restAdapter.create(ingredient);
    }
}
