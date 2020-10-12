package com.foo.common.base.utils;

import java.util.List;
import java.util.concurrent.Executors;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.foo.common.base.pojo.GuavaCallable;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

public class SvnHelper {

    public static final Logger logger = LoggerFactory
            .getLogger(SvnHelper.class);

    public static void main(String[] args) {
        logger.info("svn update task start at:{}",
                FooUtils.toDateFromYear2Second(new DateTime()));
        ListeningExecutorService service = MoreExecutors
                .listeningDecorator(Executors.newFixedThreadPool(10));

        String pathForUpdate = "D:\\zzNode\\itms3.0UI\\";
        String pathForUpdate2 = "D:\\zzNode\\itms3.0Core\\";

        List<GuavaCallable> myTaskList = Lists.newArrayList();
        myTaskList.add(new GuavaCallable(pathForUpdate));
        myTaskList.add(new GuavaCallable(pathForUpdate2));

        // For future
        List<ListenableFuture<GuavaCallable>> myList = Lists.newArrayList();

        for (GuavaCallable guavaCallable : myTaskList) {
            myList.add(service.submit(guavaCallable));
        }

        ListenableFuture<List<GuavaCallable>> successfulQueries = Futures
                .successfulAsList(myList);

        Futures.addCallback(successfulQueries,
                new FutureCallback<List<GuavaCallable>>() {

                    @Override
                    public void onSuccess(List<GuavaCallable> result) {
                        for (GuavaCallable listenableFuture : result) {
                            logger.info("listenableFuture:{}", listenableFuture);
                        }
                        logger.info("svn update task end at:{}", new DateTime());
                        System.exit(0);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                    }
                });

    }
}
