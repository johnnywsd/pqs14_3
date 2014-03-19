package edu.nyu.pqs.stopwatch.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.nyu.pqs.stopwatch.api.IStopwatch;

/**
 * The StopwatchFactory is a thread-safe factory class for IStopwatch objects.
 * It maintains references to all created IStopwatch objects and provides a
 * convenient method for getting a list of those objects.
 */
public class StopwatchFactory {

  // It doesn't make sense to use a concurrent version because we have to lock
  // it in some consistent (defined by client) state for getStopwatches().
  private static final Map<String, IStopwatch> instances =
      Collections.synchronizedMap(new HashMap<String, IStopwatch>());

  /**
   * Creates and returns a new IStopwatch object
   *
   * @param id The identifier of the new object
   * @return The new IStopwatch object
   * @throws IllegalArgumentException if <code>id</code> is empty, null, or
   *                                  already taken.
   */
  public static IStopwatch getStopwatch(String id) {
    // Stopwatch constructor guards against empty or null ids.
    IStopwatch stopwatch = new Stopwatch(id);

    if (instances.put(id, stopwatch) != null) {
      throw new IllegalArgumentException("id already exists: " + id);
    }

    return stopwatch;
  }

  /**
   * Returns a list of all created stopwatches
   *
   * @return a List of all creates IStopwatch objects.  Returns an empty
   * list if no IStopwatches have been created.
   */
  public static List<IStopwatch> getStopwatches() {
    return new ArrayList<IStopwatch>(instances.values());
  }
}
