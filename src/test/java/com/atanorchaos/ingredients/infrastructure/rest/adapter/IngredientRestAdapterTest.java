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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientRestAdapterTest {

    @Mock
    private IngredientService ingredientService;

    private IngredientRestAdapter ingredientAdapter;

    @BeforeEach
    public void setUp() {
        ingredientAdapter = new IngredientRestAdapter(ingredientService);
    }

    @Test
    void shouldCreateAnIngredient() {
        IngredientDTO ingredientDTO = IngredientDTO.builder()
                .name("test")
                .description("test")
                .type("test")
                .build();

        String id = UUID.randomUUID().toString();
        Ingredient ingredient = Ingredient.builder()
                .id(id)
                .name("test")
                .description("test")
                .type("test")
                .build();
        when(ingredientService.create(any(Ingredient.class))).thenReturn(Mono.just(ingredient));

        Mono<String> idMono = ingredientAdapter.create(ingredientDTO);

        StepVerifier.create(idMono)
                .expectNextMatches(id::equals)
                .verifyComplete();

        ArgumentCaptor<Ingredient> ingredientArgumentCaptor = ArgumentCaptor.forClass(Ingredient.class);
        verify(ingredientService, times(1)).create(ingredientArgumentCaptor.capture());

        Ingredient recoveredIngredient = ingredientArgumentCaptor.getValue();
        assertNotNull(recoveredIngredient);
        assertEquals(ingredientDTO.getName(), recoveredIngredient.getName());
        assertEquals(ingredientDTO.getDescription(), recoveredIngredient.getDescription());
        assertEquals(ingredientDTO.getType(), recoveredIngredient.getType());
    }
}
