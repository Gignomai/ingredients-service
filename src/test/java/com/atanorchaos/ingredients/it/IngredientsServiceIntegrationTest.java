package com.atanorchaos.ingredients.it;

import com.atanorchaos.ingredients.BreweryApplication;
import com.atanorchaos.ingredients.infrastructure.api.rest.model.IngredientDTO;
import com.atanorchaos.ingredients.infrastructure.repository.mongodb.MongoDbIngredientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = BreweryApplication.class)
public class IngredientsServiceIntegrationTest {

    private static final String URL_BASE = "http://localhost:";

    @LocalServerPort
    private int port;

    TestRestTemplate testRestTemplate;

    @Autowired
    MongoDbIngredientRepository mongoDbIngredientRepository;


    @BeforeEach
    private void setUp() {
        testRestTemplate = new TestRestTemplate();
    }

    @Test
    void shouldCreateAnIngredient() {
        final URI url = URI.create(URL_BASE + port + "/ingredients");
        final IngredientDTO ingredientDTO = IngredientDTO.builder()
                .name("name")
                .description("description")
                .type("type")
                .build();
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final HttpEntity<IngredientDTO> request = new HttpEntity<>(ingredientDTO, headers);

        final ResponseEntity<Void> response = testRestTemplate.postForEntity(url, request, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        List<String> location = response.getHeaders().get("location");
        assertThat(location).isNotNull();
        assertThat(location.size()).isEqualTo(1);
        assertThat(location.get(0)).startsWith(url.toString());
        assertThat(UUID.fromString(location.get(0).split("/")[4])).isNotNull();

        StepVerifier.create(mongoDbIngredientRepository.findAll())
                .expectNextCount(1L)
                .expectComplete()
                .verify();
        StepVerifier.create(mongoDbIngredientRepository.findAll())
                .expectNextMatches(expectedIngredient -> expectedIngredient.getId() != null &&
                        expectedIngredient.getName().equals("name") &&
                        expectedIngredient.getDescription().equals("description") &&
                        expectedIngredient.getType().equals("type"))
                .expectComplete()
                .verify();
    }
}
