package com.atanorchaos.ingredients.domain.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Builder
@Getter
public class Ingredient {

    @Id
    String id;
    String name;
    String description;
    String type;
}
