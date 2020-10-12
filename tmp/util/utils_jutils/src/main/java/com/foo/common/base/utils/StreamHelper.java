package com.foo.common.base.utils;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Java8 Stream examples for chapter5 only.
 */
public class StreamHelper {

    Logger logger = LoggerFactory.getLogger(StreamHelper.class);
    private List<FooMan> fooMans;
    private Stream<FooMan> fooMansStream;

    private class FooMan implements Comparable<FooMan> {

        public double getWeight() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }

        private double weight;

        private FooMan(double weight) {
            this.weight = weight;
        }

        boolean isOverWeight() {
            return weight > 100;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this).add("weight", weight).toString();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof FooMan) {
                FooMan that = (FooMan) obj;
                return Objects.equal(this.weight, that.weight);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(weight);
        }

        @Override
        public int compareTo(FooMan that) {
            return ComparisonChain.start().compare(this.weight, that.weight).result();
        }
    }

    private List<FooMan> getTestRandomFooMans() {
        List<FooMan> myList = Lists.newArrayList();
        for (int i = 0; i < 20; i++) {
            myList.add(new FooMan(RandomUtils.nextInt(80, 120)));
        }
        return myList;
    }


    private List<FooMan> getTestDuplicateFooMans() {
        if (fooMans != null) {
            return fooMans;
        }
        fooMans = Lists.newArrayList();
        fooMans.add(new FooMan(110));
        fooMans.add(new FooMan(120));
        fooMans.add(new FooMan(130));
        fooMans.add(new FooMan(110));
        fooMans.add(new FooMan(115));
        fooMans.add(new FooMan(117));
        fooMans.add(new FooMan(116));
        fooMans.add(new FooMan(120));
        fooMans.add(new FooMan(90));
        fooMans.add(new FooMan(90));
        return fooMans;
    }

    private List<FooMan> getTestEmptyFooMans() {
        return Lists.newArrayList();
    }

    private Stream<FooMan> getTestDuplicateFooMansStream() {
        return getTestDuplicateFooMans().stream();
    }


    @Test
    public void printStream() {
        getTestRandomFooMans().forEach(System.out::println);
    }

    @Test
    public void filterStream() {
        List<FooMan> result = getTestDuplicateFooMans().stream().filter(FooMan::isOverWeight).distinct().skip(2).limit(2).collect(Collectors.toList());
        result.forEach((s) -> logger.info("{}", s));
    }

    @Test
    public void mapStream() {
        List<FooMan> result = getTestDuplicateFooMans().stream().filter(FooMan::isOverWeight).map((t) -> {
            logger.info("step here.");
            return new FooMan(t.weight + 100);
        }).collect(Collectors.toList());

        result.forEach((s) -> logger.info("{}", s));
    }

    @Test
    public void flatMapStream() {
        List<String> source = Lists.newArrayList("hello", "world");

        Set<Character> mySet = Sets.newTreeSet();
        for (String s : source) {
            for (char a : s.toCharArray()) {
                mySet.add(a);
            }
        }
        logger.info("result is:{}", mySet);

        List<String> result = source.stream().map((t) -> t.split("")).flatMap(Arrays::stream).distinct().sorted().collect(Collectors.toList());

        logger.info("result is:{}", result);
    }


    @Test
    public void mappingExps() {
        List<Integer> numbers1 = Arrays.asList(1, 2, 3);
        List<Integer> numbers2 = Arrays.asList(3, 4);

        List<int[]> result = numbers1.stream().flatMap((a) -> {
            return numbers2.stream().map((b) -> {
                return new int[]{a, b};
            });
        }).filter((t) -> {
            return (t[0] + t[1]) % 3 == 0;
        }).collect(Collectors.toList());

        result.forEach((t) -> {
            logger.info("result is:{}", t);
        });


    }

    @Test
    public void findAndMatchExps() {

        final Stream<FooMan> myStream = getTestDuplicateFooMans().stream();

        logger.info("anyMatch:{}", getTestDuplicateFooMansStream().anyMatch(FooMan::isOverWeight));
        logger.info("allMatch:{}", getTestDuplicateFooMansStream().allMatch(FooMan::isOverWeight));
        logger.info("noneMatch:{}", getTestDuplicateFooMansStream().noneMatch(FooMan::isOverWeight));

        Optional<FooMan> findAny = getTestDuplicateFooMansStream().filter(FooMan::isOverWeight).findAny();
        logger.info("findAny:{}", findAny);

        Optional<FooMan> findFirst = getTestDuplicateFooMansStream().filter(FooMan::isOverWeight).findFirst();
        logger.info("findFirst:{}", findFirst);
    }

    @Test
    public void reduce() {
        FooMan initFooMan = new FooMan(200);
        FooMan reduceWithParams = getTestDuplicateFooMansStream().reduce(initFooMan, (a, b) -> new FooMan(Double.sum(a.weight, b.weight)));
        logger.info("reduceWithParams:{}", reduceWithParams);

        Optional<FooMan> reduceWithoutParams = getTestDuplicateFooMansStream().reduce((a, b) -> new FooMan(Double.sum(a.weight, b.weight)));
        logger.info("reduceWithoutParams:{}", reduceWithoutParams);

        int mapReduceSums = getTestDuplicateFooMansStream().mapToInt(r -> 1).reduce(0, Integer::sum);
        logger.info("mapReduceSums:{}", mapReduceSums);

        long mapReduceCount = getTestDuplicateFooMansStream().count();
        logger.info("mapReduceCount:{}", mapReduceCount);
    }

    @Test
    public void numericStream() {
        double sum = getTestDuplicateFooMansStream().mapToDouble(FooMan::getWeight).sum();
        DoubleStream doubleStream = getTestDuplicateFooMansStream().mapToDouble(FooMan::getWeight);
        Stream<Double> streamDouble = doubleStream.boxed();
        Optional<Double> maxOfStream = streamDouble.max(Double::compareTo);
        logger.info("max of stream is:{}", maxOfStream.get());

        OptionalDouble maxOfEmptyStream = getTestEmptyFooMans().stream().mapToDouble(FooMan::getWeight).max();
        logger.info("max of empty stream is:{}", maxOfEmptyStream.isPresent() ? maxOfEmptyStream.getAsDouble() : "no max value here.");
    }

    @Test
    public void intStream() {
        long count = IntStream.range(1, 100).filter(t -> t % 2 == 0).count();
        logger.info("result is:{}", count);
    }

    @Test
    public void buildStream() {
        Stream<String> stringStream = Stream.of("WorLd", "Hello", "WoRld");
        logger.info("result is:{}", stringStream.map(r -> StringUtils.capitalize(r.toLowerCase())).distinct().sorted(String::compareTo).collect(Collectors.joining(",")));

        logger.info("result is:{}", Arrays.stream(new int[]{1, 4, 6}).max().getAsInt());

        Stream.iterate(0, n -> n + 2)
                .limit(10).forEach(System.out::println);

        String fibonacciStr = Stream.iterate(new int[]{0, 1}, (t) -> {
            return new int[]{t[1], t[0] + t[1]};
        }).limit(20).map(t -> t[1] + "").collect(Collectors.joining(","));
        logger.info("fibonacciStr is:{}", fibonacciStr);

        Stream.generate(() -> FooUtils.generateUUID()).limit(5).forEach(System.out::println);


    }
}
