package features.base;

public class StringExamples {

    /**
     * Works only on java 11
     * */
    public static void main(String[] args) {
        blank();
        lines();
        repeat();
        striping();
    }

    private static void blank() {
        System.out.println("".isBlank());
        System.out.println(" ".isBlank());
    }

    private static void lines() {
        "line 1\nline 2\n".lines().forEach(System.out::println);
    }

    private static void repeat() {
        System.out.println("0".repeat(4));
    }

    private static void striping() {
        System.out.println(" Sentence with spaces ".strip());
        System.out.println(" Sentence with spaces ".stripLeading());
        System.out.println(" Sentence with spaces ".stripTrailing());
    }
}
