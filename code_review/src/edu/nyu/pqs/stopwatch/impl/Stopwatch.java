package edu.nyu.pqs.stopwatch.impl;

import edu.nyu.pqs.stopwatch.api.IStopwatch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A thread-safe stop watch.
 */
public class Stopwatch implements IStopwatch {

  private final String id;

  private final List<Long> times =
      Collections.synchronizedList(new ArrayList<Long>());

  /**
   * The time of the last start or lap call.
   */
  private long lastActionTime;

  /**
   * The stopwatch is always in one of these states.
   */
  private static enum State {
    RUNNING, STOPPED
  }

  private final Object stateLock = new Object();

  private State state = State.STOPPED;

  /**
   * This constructor is called by the factory.
   *
   * @param id a unique id for this stopwatch.
   */
  Stopwatch(String id) {
    if (id == null || id.length() == 0) {
      throw new IllegalArgumentException("id is null or empty: " + id);
    }
    this.id = id;
  }

  private static long time() {
    return System.nanoTime();
  }

  /**
   * Returns the Id of this stopwatch
   *
   * @return the Id of this stopwatch.  Will never be empty or null.
   */
  @Override
  public String getId() {
    return id;
  }

  /**
   * Starts the stopwatch.
   *
   * @throws IllegalStateException if called when the stopwatch is already
   *                               running
   */
  @Override
  public void start() {
    synchronized (stateLock) {
      if (state == State.RUNNING) {
        throw new IllegalStateException("the stopwatch is already running");
      }

      if (times.isEmpty()) {
        lastActionTime = time();
      } else {
        // If we are restarting a previously paused lap, we add the length of
        // that 'unfinished' lap onto the next lap and remove it from our
        // records.
        final int lastIndex = times.size() - 1;
        final Long lastLap = times.get(lastIndex);
        times.remove(lastIndex);

        lastActionTime = time() - lastLap;
      }

      state = State.RUNNING;
    }
  }

  /**
   * Stores the time elapsed since the last time lap() was called
   * or since start() was called if this is the first lap.
   *
   * @throws IllegalStateException if called when the stopwatch isn't running
   */
  @Override
  public void lap() {
    synchronized (stateLock) {
      if (state == State.STOPPED) {
        throw new IllegalStateException("the stopwatch isn't running");
      }

      long t = time();
      times.add((t - lastActionTime) / 1000000);
      lastActionTime = t;
    }
  }

  /**
   * Stops the stopwatch (and records one final lap).
   *
   * @throws IllegalStateException if called when the stopwatch isn't running
   */
  @Override
  public void stop() {
    synchronized (stateLock) {
      if (state == State.STOPPED) {
        throw new IllegalStateException("the stopwatch isn't running");
      }

      long t = time();
      times.add((t - lastActionTime) / 1000000);
      state = State.STOPPED;
    }
  }

  /**
   * Resets the stopwatch.  If the stopwatch is running, this method stops the
   * watch and resets it.  This also clears all recorded laps.
   */
  @Override
  public void reset() {
    synchronized (stateLock) {
      state = State.STOPPED;
      times.clear();
    }
  }

  /**
   * Returns a list of lap times (in milliseconds).  This method can be called
   * at any time and will not throw an exception.
   *
   * @return a list of recorded lap times or an empty list if no times are
   * recorded.
   */
  @Override
  public List<Long> getLapTimes() {
    return new CopyOnWriteArrayList<Long>(times);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return "Timer{" + id + "}";
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return 17 * id.hashCode();
  }
}
