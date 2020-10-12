package com.foo.common.base.utils;

import com.foo.common.base.laboratory.Trader;
import com.foo.common.base.laboratory.Transaction;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Java8 Stream examples for CollectingDateWithStream as Chapter6 only.
 */
public class CollectingDateWithStreamHelper {

    Logger logger = LoggerFactory.getLogger(CollectingDateWithStreamHelper.class);
    Trader raoul = new Trader("Raoul", "Cambridge");
    Trader mario = new Trader("Mario", "Milan");
    Trader alan = new Trader("Alan", "Cambridge");
    Trader brian = new Trader("Brian", "Cambridge");
    List<Transaction> transactions = Arrays.asList(new Transaction(brian, 2011, 300),
            new Transaction(raoul, 2012, 1000),
            new Transaction(raoul, 2011, 400),
            new Transaction(mario, 2012, 710), new Transaction(mario, 2012, 700), new Transaction(alan, 2012, 950)
    );

    @Test
    public void groupByName() {
        transactions.stream()
                .sorted((that1, that2) -> ComparisonChain.start().compare(that1.getTrader().getName(), that2.getTrader().getName(), Ordering.natural().reverse()).result())
                .collect(Collectors.groupingBy(r -> r.getTrader().getName())).forEach((a, b) -> {
            logger.info("key:{},value:{}", a, b);
        });
    }

    @Test
    public void reducingAndSummary() {
        logger.info("counting result:{}", transactions.stream().collect(Collectors.counting()));
        Comparator<Transaction> transactionComparator = (a, b) -> ComparisonChain.start().compare(a.getTrader().getName(), b.getTrader().getName()).result();
        Optional<Transaction> transaction = transactions.stream().collect(Collectors.maxBy(transactionComparator));
        logger.info("maxBy result:{}", transaction);

        logger.info("sum result:{}", transactions.stream().collect(Collectors.summingInt(t -> t.getValue())));

        logger.info("averagingInt result:{}", transactions.stream().collect(Collectors.averagingInt(t -> t.getValue())));

        logger.info("summarizingInt result:{}", transactions.stream().collect(Collectors.summarizingInt(t -> t.getValue())));

        logger.info("reducing result:{}", transactions.stream().collect(Collectors.reducing(0, t -> t.getValue(), Integer::sum)));
    }

    @Test
    public void generalization() {
        String result = transactions.stream()
                .map(a -> a.getTrader().getName())
                .collect(Collectors.reducing((s1, s2) -> s1 + s2)).orElse("");
        logger.info("{}", result);

        result = transactions.stream()
                .map(a -> a.getTrader().getName())
                .collect(Collectors.joining(","));
        logger.info("{}", result);
    }

    @Test
    public void groupby() {
        Map<String, List<Transaction>> result = transactions.stream().collect(Collectors.groupingBy((t) -> {
            int balance = t.getValue();
            if (balance < 500) {
                return "low";
            } else if (balance >= 500 && balance < 800) {
                return "middle";
            } else {
                return "high";
            }
        }));
        logger.info("{}", result);

        Map<?, ?> object = transactions.stream()
                .collect(
                        Collectors.groupingBy(t -> t.getTrader().getCity(),
                                Collectors.groupingBy(t -> t.getYear(), Collectors.summingInt(Transaction::getValue))));
        logger.info("{}", object);

        object = transactions.stream()
                .collect(Collectors.groupingBy(t -> t.getTrader().getCity(), Collectors.toCollection(HashSet::new)));

        logger.info("{}", object);

    }


}
