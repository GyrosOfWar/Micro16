package micro16;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;

/**
 * User: Martin
 * Date: 25.02.14
 * Time: 18:25
 */

class Measurer {
    public static void measure(Runnable r) {
        long t0 = System.nanoTime();
        r.run();
        long t1 = System.nanoTime();
        System.out.println("Elasped time: " + (t1 - t0) / (1000.0 * 1000.0) + " ms");
    }

    public static <T, R> R measure(Function<T, R> func, T parameter) {
        long t0 = System.nanoTime();
        R result = func.apply(parameter);
        long t1 = System.nanoTime();
        System.out.println("Elasped time: " + (t1 - t0) / (1000.0 * 1000.0) + " ms");
        return result;
    }

    public static <T> T measure(BinaryOperator<T> func, T s, T t) {
        long t0 = System.nanoTime();
        T result = func.apply(s, t);
        long t1 = System.nanoTime();
        System.out.println("Elasped time: " + (t1 - t0) / (1000.0 * 1000.0) + " ms");
        return result;
    }


}

public class Stopwatch {
    private long start;
    private long end;

    private List<Duration> times;

    public Stopwatch() {
        start = end = 0;
        times = new ArrayList<>();
    }

    public static Stopwatch startNew() {
        Stopwatch s = new Stopwatch();
        s.start();
        return s;
    }

    public void start() {
        start = System.nanoTime();
    }

    public void stop() {
        end = System.nanoTime();
        times.add(Duration.ofNanos(end - start));
    }

    public void reset() {
        start = end = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Duration d : times) {
            sb.append(String.format("%s ms\n", d.toNanos() / (1000.0 * 1000.0)));
        }
        Duration avg = times.stream().reduce(Duration.ZERO, Duration::plus).dividedBy(times.size() > 1 ? times.size() : 1);
        sb.append(String.format("Average time: %s ms", avg.toNanos() / (1000.0 * 1000.0)));

        return sb.toString();
    }

    public Optional<Duration> getLastTime() {
        if (times.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(times.get(times.size() - 1));
    }
}


