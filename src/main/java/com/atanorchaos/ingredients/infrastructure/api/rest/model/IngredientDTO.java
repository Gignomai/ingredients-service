package com.atanorchaos.ingredients.infrastructure.api.rest.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class IngredientDTO {

    String name;
    String description;
    String type;
}
