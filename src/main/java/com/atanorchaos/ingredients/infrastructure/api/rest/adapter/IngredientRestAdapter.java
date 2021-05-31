package com.atanorchaos.ingredients.infrastructure.api.rest.adapter;

import com.atanorchaos.ingredients.domain.model.Ingredient;
import com.atanorchaos.ingredients.domain.port.primary.IngredientService;
import com.atanorchaos.ingredients.infrastructure.api.rest.model.IngredientDTO;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class IngredientRestAdapter {

    IngredientService ingredientService;

    public IngredientRestAdapter(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    public Mono<String> create(IngredientDTO ingredientDTO) {
        return ingredientService.create(toIngredient(ingredientDTO))
                .map(Ingredient::getId);
    }

    private Ingredient toIngredient(IngredientDTO ingredientDTO) {
        return Ingredient.builder()
                .name(ingredientDTO.getName())
                .description(ingredientDTO.getDescription())
                .type(ingredientDTO.getType())
                .build();
    }
}
