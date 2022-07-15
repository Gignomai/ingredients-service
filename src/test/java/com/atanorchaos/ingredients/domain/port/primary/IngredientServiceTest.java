package com.atanorchaos.ingredients.domain.port.primary;

import com.atanorchaos.ingredients.domain.model.Ingredient;
import com.atanorchaos.ingredients.domain.port.secondary.IngredientRepository;
import com.atanorchaos.ingredients.domain.service.IngredientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class IngredientServiceTest {

    @Mock
    private IngredientRepository ingredientRepository;

    private IngredientService ingredientService;

    @BeforeEach
    private void setUp() {
        ingredientService = new IngredientServiceImpl(ingredientRepository);
    }

    @Test
    void shouldCreateAnIngredient() {
        Ingredient savedIngredient = Ingredient.builder()
                .id(UUID.randomUUID())
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
                .expectNextMatches(returnedIngredient -> savedIngredient.getId().equals(returnedIngredient.getId()) &&
                        savedIngredient.getName().equals(returnedIngredient.getName()) &&
                        savedIngredient.getDescription().equals(returnedIngredient.getDescription()) &&
                        savedIngredient.getType().equals(returnedIngredient.getType()))
                .verifyComplete();
    }

    @Test
    void shouldReturnAGivenIngredient() {
        UUID id = UUID.randomUUID();
        Ingredient savedIngredient = Ingredient.builder()
                .id(id)
                .name("test")
                .description("test")
                .type("test")
                .build();

        when(ingredientRepository.getIngredient(anyString())).thenReturn(Mono.just(savedIngredient));

        Mono<Ingredient> ingredientMono = ingredientService.getIngredient(id.toString());

        ArgumentCaptor<String> idArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(ingredientRepository, times(1)).getIngredient(idArgumentCaptor.capture());
        assertThat(idArgumentCaptor.getValue()).isEqualTo(id.toString());
        StepVerifier.create(ingredientMono)
                .expectNextMatches(returnedIngredient -> savedIngredient.getId().equals(returnedIngredient.getId()) &&
                        savedIngredient.getName().equals(returnedIngredient.getName()) &&
                        savedIngredient.getDescription().equals(returnedIngredient.getDescription()) &&
                        savedIngredient.getType().equals(returnedIngredient.getType()))
                .verifyComplete();
    }
}
