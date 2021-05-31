package com.atanorchaos.ingredients.infrastructure.repository.mongodb.adapter;

import com.atanorchaos.ingredients.domain.model.Ingredient;
import com.atanorchaos.ingredients.domain.port.secondary.IngredientRepository;
import com.atanorchaos.ingredients.infrastructure.repository.mongodb.MongoDbIngredientRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class MongoDbIngredientRepositoryAdapter implements IngredientRepository {
    private MongoDbIngredientRepository mongoDbIngredientRepository;

    public MongoDbIngredientRepositoryAdapter(MongoDbIngredientRepository mongoDbIngredientRepository) {
        this.mongoDbIngredientRepository = mongoDbIngredientRepository;
    }

    @Override
    public Mono<Ingredient> create(Ingredient ingredient) {
        return mongoDbIngredientRepository.save(ingredient);
    }
}
