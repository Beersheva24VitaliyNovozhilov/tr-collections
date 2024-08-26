package io.p4r53c.telran.util;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Random;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

@State(Scope.Thread)
public class CollectionsPerformanceTest {

    private static final int N_ELEMENTS = 1_000_000;
    private Random random = new Random();
    protected Collection<Integer> collection;

    @Setup(Level.Trial)
    public void setup() {
        collection = new ArrayList<>();
        for (int i = 0; i < N_ELEMENTS; i++) {
            collection.add(random.nextInt());
        }
    }
    @Disabled("Not for regular test cases")
    @Test
    void runBenchmarks() throws Exception {
        Options options = new OptionsBuilder()
                .include(this.getClass().getName() + ".*")
                .mode(Mode.AverageTime)
                .warmupTime(TimeValue.seconds(1))
                .warmupIterations(6)
                .threads(1)
                .measurementIterations(6)
                .forks(1)
                .shouldFailOnError(true)
                .shouldDoGC(true)
                
                .build();

        new Runner(options).run();
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void testRemoveIf() {
        collection.removeIf(n -> n % 2 == 0);
        collection.removeIf(n -> n % 2 == 0);
        collection.stream().allMatch(n -> n % 2 != 0);
    }
}
