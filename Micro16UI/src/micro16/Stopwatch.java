package micro16;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Martin
 * Date: 25.02.14
 * Time: 18:25
 */

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

        for(Duration d: times) {
            sb.append(d.toNanos() / (1000.0 * 1000.0)).append(" ms\n");
        }

        return sb.toString();
    }

    public Duration getLastTime() {
        return times.get(times.size()-1);
    }
}
