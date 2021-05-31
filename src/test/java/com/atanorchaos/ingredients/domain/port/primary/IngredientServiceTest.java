package com.atanorchaos.ingredients.domain.port.primary;

import com.atanorchaos.ingredients.domain.model.Ingredient;
import com.atanorchaos.ingredients.domain.port.secondary.IngredientRepository;
import com.atanorchaos.ingredients.domain.service.IngredientServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class IngredientServiceTest {

    @Mock
    private IngredientRepository ingredientRepository;

    private IngredientService ingredientService;

    @BeforeAll
    private void setUp() {
        ingredientService = new IngredientServiceImpl(ingredientRepository);
    }

    @Test
    void shouldCreateAnIngredient() {
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

        when(ingredientRepository.create(any(Ingredient.class))).thenReturn(Mono.just(savedIngredient));
        Mono<Ingredient> ingredientMono = ingredientService.create(ingredient);

        StepVerifier.create(ingredientMono)
                .expectNextMatches(returnedIngredient -> returnedIngredient.getId() != null &&
                        "test".equals(returnedIngredient.getName()) &&
                        "test".equals(returnedIngredient.getDescription()) &&
                        "test".equals(returnedIngredient.getType()))
                .verifyComplete();
    }
}
