package edu.nyu.pqs.stopwatch.demo;

import edu.nyu.pqs.stopwatch.api.IStopwatch;
import edu.nyu.pqs.stopwatch.impl.StopwatchFactory;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StopwatchDemo {
  public static void main(String[] args) throws InterruptedException {

    ExecutorService threads = Executors.newCachedThreadPool();

    final int N = 10;

    final CountDownLatch ready = new CountDownLatch(N);
    final CountDownLatch start = new CountDownLatch(1);

    final Random random = new Random();

    IStopwatch stopwatch1 = StopwatchFactory.getStopwatch("first-stopwatch");
    stopwatch1.start();
    for (int i = 0; i < N; ++i) {
      try {
        Thread.sleep((long) (random.nextFloat() * 800 + 200));
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }

      if (random.nextFloat() < 0.5f) {
        System.out.println("lapping");
        stopwatch1.lap();
      } else {
        System.out.println("restarting");
        stopwatch1.stop();
        try {
          Thread.sleep((long) (random.nextFloat() * 800 + 200));
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
        stopwatch1.start();
      }
    }
    stopwatch1.stop();
    System.out.println(stopwatch1.getLapTimes());

    final IStopwatch stopwatch = StopwatchFactory.getStopwatch("Stopwatch");

    for (int i = 0; i < N; ++i) {
      threads.execute(new Runnable() {
        @Override
        public void run() {
          ready.countDown();
          try {
            start.await();
          } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
          }

          for (int i = 0; i < 10; ++i) {
            try {
              Thread.sleep((long) (random.nextFloat() * 800 + 200));
            } catch (InterruptedException e) {
              Thread.currentThread().interrupt();
            }
            stopwatch.lap();

            if (random.nextFloat() < 0.1) {
              System.out.println("thread " + Thread.currentThread().getId() +
                  ": " + stopwatch.getLapTimes());
            }
          }

          System.out.println("thread " + Thread.currentThread().getId() +
              " done: " + stopwatch.getLapTimes());

        }
      });
    }

    ready.await();
    System.out.println("starting");
    stopwatch.start();
    start.countDown();

    threads.shutdown();
  }
}
