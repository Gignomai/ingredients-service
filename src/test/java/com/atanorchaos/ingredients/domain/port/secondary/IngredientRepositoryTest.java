package com.atanorchaos.ingredients.domain.port.secondary;

import com.atanorchaos.ingredients.domain.model.Ingredient;
import com.atanorchaos.ingredients.infrastructure.repository.mongodb.MongoDbIngredientRepository;
import com.atanorchaos.ingredients.infrastructure.repository.mongodb.adapter.MongoDbIngredientRepositoryAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IngredientRepositoryTest {

    @Mock
    private MongoDbIngredientRepository mongoDbIngredientRepository;

    private MongoDbIngredientRepositoryAdapter mongoDbIngredientRepositoryAdapter;

    @BeforeEach
    private void setUp() {
        mongoDbIngredientRepositoryAdapter = new MongoDbIngredientRepositoryAdapter(mongoDbIngredientRepository);
    }

    @Test
    void create() {
        Ingredient savedIngredient = Ingredient.builder()
                .id(UUID.randomUUID().toString())
                .name("test")
                .description("test")
                .type("test")
                .build();

        Ingredient ingredient = Ingredient.builder()
                .name("test")
                .description("test")
                .type("test")
                .build();

        when(mongoDbIngredientRepository.save(any(Ingredient.class))).thenReturn(Mono.just(savedIngredient));

        Mono<Ingredient> ingredientMono = mongoDbIngredientRepositoryAdapter.create(ingredient);

        StepVerifier.create(ingredientMono)
                .expectNextMatches(returnedIngredient -> returnedIngredient.getId() != null)
                .verifyComplete();

    }
}