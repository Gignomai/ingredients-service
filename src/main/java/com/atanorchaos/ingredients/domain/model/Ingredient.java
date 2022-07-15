package com.atanorchaos.ingredients.domain.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "ingredients")
@Builder
@Getter
public class Ingredient {

    @Id
    UUID id;
    String name;
    String description;
    String type;
}
