package features.functional.interfaces;

import java.util.function.LongBinaryOperator;
import java.util.stream.LongStream;

public class FunctionalExamples {

    @FunctionalInterface
    interface MyFunctionalInterface<R, F, S, T> {
        R doSomething(F first, S second, T third);
    }

    /**
     * Works only on Java 9
     * */
    interface Numbers {

        default long add(long... numbers) {
            return execute((a, b) -> a+b, numbers);
        }

        default long multiply(long... numbers) {
            return execute((a, b) -> a*b, numbers);
        }

        private long execute(LongBinaryOperator operator, long... numbers) {
            return LongStream.of(numbers)
                    .reduce(operator)
                    .orElse(0);
        }
    }

    static class NumberExtends implements Numbers {
    }

    public static void main(String[] args) {
        MyFunctionalInterface funcInt = (first, second, third) ->
                first + " " + second + " " + third;

        System.out.println(funcInt.doSomething("A", "B", "C"));

        NumberExtends anExtends = new NumberExtends();
        System.out.println(anExtends.add(1, 1));
    }
}
