package com.atanorchaos.ingredients.it;

import com.atanorchaos.ingredients.domain.model.Ingredient;
import com.atanorchaos.ingredients.infrastructure.repository.mongodb.MongoDbIngredientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class MongoDbIngredientRepositoryIntegrationTest {

    @Autowired
    MongoDbIngredientRepository mongoDbIngredientRepository;

    @Test
    void save() {
        Ingredient ingredient = Ingredient.builder()
                .name("Test name")
                .description("Test description")
                .type("Test type")
                .build();
        Ingredient ingredient2 = Ingredient.builder()
                .name("Test name2")
                .description("Test description2")
                .type("Test type2")
                .build();


        Mono<Ingredient> ingredientMono = mongoDbIngredientRepository.save(ingredient);
        Mono<Ingredient> ingredientMono2 = mongoDbIngredientRepository.save(ingredient2);

        StepVerifier.create(ingredientMono)
                .expectNextMatches(expectedIngredient -> expectedIngredient.getId() != null &&
                        expectedIngredient.getName().equals("Test name") &&
                        expectedIngredient.getDescription().equals("Test description") &&
                        expectedIngredient.getType().equals("Test type"))
                .expectComplete()
                .verify();
        StepVerifier.create(ingredientMono2)
                .expectNextMatches(expectedIngredient2 -> expectedIngredient2.getId() != null &&
                        expectedIngredient2.getName().equals("Test name2") &&
                        expectedIngredient2.getDescription().equals("Test description2") &&
                        expectedIngredient2.getType().equals("Test type2"))
                .expectComplete()
                .verify();

        StepVerifier.create(mongoDbIngredientRepository.findAll())
                .expectNextCount(2L)
                .expectComplete()
                .verify();
    }
}
