package features.http;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;


public class HttpExamplesTest {

    private HttpExamples httpExamples;

    @BeforeEach
    void setUp() {
        httpExamples = new HttpExamples();
    }

    @Test
    public void testGet() {
        final HttpExamples.Post expected = new HttpExamples.Post(1L, 1L,
                "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
                "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto");

        final Optional<HttpExamples.Post> response = httpExamples.doGet();
        assertTrue(response.isPresent());
        assertEquals(expected, response.get());
    }

    @Test
    public void testPost() {
        final HttpExamples.Post post = new HttpExamples.Post(2L, "one title", "one body");
        final Optional<HttpExamples.Post> response = httpExamples.doPost(post);
        assertTrue(response.isPresent());
        assertEquals(Long.valueOf(101L), response.get().getId());
    }
}
