package com.atanorchaos.ingredients.infrastructure.repository.mongodb;

import com.atanorchaos.ingredients.domain.model.Ingredient;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MongoDbIngredientRepository extends ReactiveMongoRepository<Ingredient, UUID> {

}
