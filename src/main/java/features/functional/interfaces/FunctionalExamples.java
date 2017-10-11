package features.functional.interfaces;

public class FunctionalExamples {

    @FunctionalInterface
    interface MyFunctionalInterface<R, F, S, T> {
        R doSomething(F first, S second, T third);
    }

    public static void main(String[] args) {
        MyFunctionalInterface funcInt = (first, second, third) ->
                first + " " + second + " " + third;

        System.out.println(funcInt.doSomething("A", "B", "C"));
    }
}
