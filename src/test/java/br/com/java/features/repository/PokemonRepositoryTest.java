package br.com.java.features.repository;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PokemonRepositoryTest {

    @Test
    void shouldReturnByName() throws ExecutionException, InterruptedException {
        final var pokemon = PokemonRepository.findByName("Beedrill");

        Awaitility.await().between(1, TimeUnit.SECONDS, 1100, TimeUnit.MILLISECONDS).until(pokemon::isDone);

        final var response = pokemon.get();
        assertTrue(response.isPresent());
        assertEquals(15, response.get().getNumber());
    }

    @Test
    void shouldReturnByNameWithDelay() throws ExecutionException, InterruptedException {
        final var pokemon = PokemonRepository.findByNameWithDelay("Beedrill");

        Awaitility.await().atLeast(2000, TimeUnit.MILLISECONDS).until(pokemon::isDone);

        final var response = pokemon.get();
        assertTrue(response.isPresent());
        assertEquals(15, response.get().getNumber());
    }

    /**
     * Future_ThenAccept
     * */
    @Test
    void shouldReturnByNameAssertThroughAccept() {
        final var pokemon = PokemonRepository.findByName("Beedrill")
                .thenAccept((p) -> {
                    assertTrue(p.isPresent());
                    assertEquals(15, p.get().getNumber());
                });

        Awaitility.await().atMost(1100, TimeUnit.MILLISECONDS).until(pokemon::isDone);
    }

    @Test
    void shouldReturnEvolutionsByName() throws ExecutionException, InterruptedException {
        final var evolutions = PokemonRepository.findEvolutionsByName("Pidgey");

        Awaitility.await().atMost(1100, TimeUnit.MILLISECONDS).until(evolutions::isDone);
        final var response = evolutions.get();
        assertEquals(List.of("Pidgeotto", "Pidgeot"), response);
    }

}