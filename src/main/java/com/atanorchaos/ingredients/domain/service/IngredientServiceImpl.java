package com.atanorchaos.ingredients.domain.service;

import com.atanorchaos.ingredients.domain.model.Ingredient;
import com.atanorchaos.ingredients.domain.port.primary.IngredientService;
import com.atanorchaos.ingredients.domain.port.secondary.IngredientRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class IngredientServiceImpl implements IngredientService {

    IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public Mono<Ingredient> create(Ingredient ingredient) {
        return ingredientRepository.create(ingredient);
    }
}
