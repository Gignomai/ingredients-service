package com.atanorchaos.ingredients.infrastructure.rest.adapter;

import com.atanorchaos.ingredients.domain.model.Ingredient;
import com.atanorchaos.ingredients.domain.port.primary.IngredientService;
import com.atanorchaos.ingredients.infrastructure.api.rest.adapter.IngredientRestAdapter;
import com.atanorchaos.ingredients.infrastructure.api.rest.model.IngredientDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientRestAdapterTest {

    @Mock
    private IngredientService ingredientService;

    private IngredientRestAdapter ingredientAdapter;

    @BeforeEach
    private void setUp() {
        ingredientAdapter = new IngredientRestAdapter(ingredientService);
    }

    @Test
    void shouldCreateAnIngredient() {
        final IngredientDTO ingredientDTO = IngredientDTO.builder()
                .name("test")
                .description("test")
                .type("test")
                .build();

        final UUID id = UUID.randomUUID();
        final Ingredient ingredient = Ingredient.builder()
                .id(id)
                .name("test")
                .description("test")
                .type("test")
                .build();
        when(ingredientService.create(any(Ingredient.class))).thenReturn(Mono.just(ingredient));

        final Mono<UUID> idMono = ingredientAdapter.create(ingredientDTO);

        StepVerifier.create(idMono)
                .expectNextMatches(id::equals)
                .verifyComplete();

        final ArgumentCaptor<Ingredient> ingredientArgumentCaptor = ArgumentCaptor.forClass(Ingredient.class);
        verify(ingredientService, times(1)).create(ingredientArgumentCaptor.capture());

        final Ingredient recoveredIngredient = ingredientArgumentCaptor.getValue();
        assertThat(recoveredIngredient).isNotNull();
        assertThat(recoveredIngredient.getName()).isEqualTo(ingredientDTO.getName());
        assertThat(recoveredIngredient.getDescription()).isEqualTo(ingredientDTO.getDescription());
        assertThat(recoveredIngredient.getType()).isEqualTo(ingredientDTO.getType());
    }
}
