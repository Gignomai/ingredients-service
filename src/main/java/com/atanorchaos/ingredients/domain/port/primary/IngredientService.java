package com.atanorchaos.ingredients.domain.port.primary;

import com.atanorchaos.ingredients.domain.model.Ingredient;
import reactor.core.publisher.Mono;

public interface IngredientService {
    Mono<Ingredient> create(Ingredient ingredient);
}
