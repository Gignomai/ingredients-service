package com.atanorchaos.ingredients.domain.port.secondary;

import com.atanorchaos.ingredients.domain.model.Ingredient;
import reactor.core.publisher.Mono;

public interface IngredientRepository {
    Mono<Ingredient> create(Ingredient ingredient);
}
