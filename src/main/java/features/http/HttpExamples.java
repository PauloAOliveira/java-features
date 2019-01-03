package features.http;

import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;
import java.util.Optional;

/**
 * Works only on java 11
 * */
class HttpExamples {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/posts";
    private final HttpClient client;
    private final Gson gson;

    public static class Post {
        private Long userId;
        private Long id;
        private String title;
        private String body;

        Post(final Long userId, final String title, final String body) {
            this(userId, null, title, body);
        }

        Post(final Long userId, final Long id, final String title, final String body) {
            this.userId = Objects.requireNonNull(userId);
            this.id = id;
            this.title = Objects.requireNonNull(title);
            this.body = Objects.requireNonNull(body);
        }

        Long getUserId() {
            return userId;
        }

        Long getId() {
            return id;
        }

        String getTitle() {
            return title;
        }

        String getBody() {
            return body;
        }

        @Override
        public boolean equals(final Object o) {
            if(this == o) return true;
            if(!(o instanceof Post)) return false;

            final Post post = (Post) o;

            if(!getUserId().equals(post.getUserId())) return false;
            if(getId() != null ? !getId().equals(post.getId()) : post.getId() != null) return false;
            if(!getTitle().equals(post.getTitle())) return false;
            return getBody().equals(post.getBody());
        }

        @Override
        public int hashCode() {
            int result = getUserId().hashCode();
            result = 31 * result + (getId() != null ? getId().hashCode() : 0);
            result = 31 * result + getTitle().hashCode();
            result = 31 * result + getBody().hashCode();
            return result;
        }
    }

    HttpExamples() {
        client = HttpClient.newBuilder()
                .build();
        gson = new Gson();
    }

    Optional<Post> doGet() {
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/1"))
                .GET()
                .build();

        return sendRequest(request);
    }

    Optional<Post> doPost(final Post post) {
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(post)))
                .build();

        return sendRequest(request);
    }

    private Optional<Post> sendRequest(final HttpRequest request) {
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(this::postFromJson)
                .join();
    }

    private Optional<Post> postFromJson(final String body) {
        try {
            return Optional.of(gson.fromJson(body, Post.class));
        } catch(final Exception e) {
            return Optional.empty();
        }
    }
}
