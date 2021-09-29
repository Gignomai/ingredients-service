package com.atanorchaos.ingredients.it;

import com.atanorchaos.ingredients.BreweryApplication;
import com.atanorchaos.ingredients.infrastructure.api.rest.model.IngredientDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = BreweryApplication.class)
public class IngredientsServiceIntegrationTest {

    private static final String URL_BASE = "http://localhost:";

    @LocalServerPort
    private int port;

    TestRestTemplate testRestTemplate;


    @BeforeEach
    private void setUp() {
        testRestTemplate = new TestRestTemplate();
    }

    @Test
    void shouldCreateAnIngredient() {
        URI url = URI.create(URL_BASE + port + "/ingredients");
        IngredientDTO ingredientDTO = IngredientDTO.builder()
                .name("name")
                .description("description")
                .type("type")
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<IngredientDTO> request = new HttpEntity<>(ingredientDTO, headers);

        ResponseEntity<Void> response = testRestTemplate.postForEntity(url, request, Void.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
}
