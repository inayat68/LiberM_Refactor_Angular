package com.nib.asm.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NAsmExecStats {

  private static final Logger logger = LoggerFactory.getLogger(NAsmExecStats.class);

  private static Map<String, NAsmExecStatsClassDetails> execStatsClasses = null;
  private static long startTime = 0;
  private static long executionTime = 0;

  static {
    //startTime = System.currentTimeMillis();
    startTime = System.nanoTime();
    execStatsClasses = new TreeMap<>();
  }

  public static void end() {
    //long execTime = System.currentTimeMillis() - startTime;
    long execTime = System.nanoTime() - startTime;
    executionTime = execTime;
  }

  public static void initStep() {
    execStatsClasses = new TreeMap<>();
  }

  public static void statsStartMethod(Class<?> class_, String methodName) {
    NAsmExecStatsClassDetails exs;
    String name = getClassName(class_);
    if (execStatsClasses.containsKey(name)) {
      exs = execStatsClasses.get(name);
    } else {
      exs = new NAsmExecStatsClassDetails(name);
      execStatsClasses.put(name, exs);
    }
    exs.start(methodName);
  }

  /**
   * Capture runtime execution end time
   * 
   * @param class_
   * @param methodName
   */
  public static void statsEndMethod(Class<?> class_, String methodName) {
    NAsmExecStatsClassDetails exs;
    String name = getClassName(class_);
    if (execStatsClasses.containsKey(name)) {
      exs = execStatsClasses.get(name);
    } else {
      return;
    }
    exs.end(methodName);
  }

  /**
   * @return the executionTime
   */
  public static long getExecutionTime() {
    end();
    return executionTime;
  }

  /**
   * @return the execStatsClasses
   */
  public static Map<String, NAsmExecStatsClassDetails> getExecStatsClasses() {
    return execStatsClasses;
  }

  /**
   * 
   * @param nSeconds
   * @return
   */
  public static String convertExecutionTime(long nSeconds) {
    long h = TimeUnit.HOURS.convert(nSeconds, TimeUnit.NANOSECONDS);
    long m = TimeUnit.MINUTES.convert(nSeconds, TimeUnit.NANOSECONDS) - TimeUnit.MINUTES.convert(h, TimeUnit.HOURS);
    long s = TimeUnit.SECONDS.convert(nSeconds, TimeUnit.NANOSECONDS) - TimeUnit.SECONDS.convert(m, TimeUnit.MINUTES);
    long n = TimeUnit.MILLISECONDS.convert(nSeconds, TimeUnit.NANOSECONDS) - TimeUnit.MILLISECONDS.convert(s, TimeUnit.SECONDS);
    if (h > 0) {
      return String.format("%02d hours %02d minutes %02d seconds", h, m, s);
    }
    if (m > 0) {
      return String.format("%02d minutes %02d seconds", m, s);
    }
    return String.format("%02d seconds %03d mseconds", s, n);
  }

  public static void printStatsStep() {
    Iterator<String> it = execStatsClasses.keySet().iterator();
    while (it.hasNext()) {
      String class_ = it.next();
      NAsmExecStatsClassDetails scd = execStatsClasses.get(class_);
      Iterator<String> it2 = scd.getExecStatsMethodDetails().keySet().iterator();
      while (it2.hasNext()) {
        String method_ = it2.next();
        NAsmExecStatsMethodDetails smd = scd.getExecStatsMethodDetails().get(method_);
        //At least 1 mseconds
        if (smd.getExecutionTime() > 1000000) {
          String line = "[STATS] - " + class_ + "," + method_ + "," + smd.getExecTimes() + "," + smd.getExecutionTime() + "," + convertExecutionTime(smd.getExecutionTime());
          logger.info(line);
        }
      }
    }
    printStatsFinal();
  }

  public static void printStatsFinal() {
    logger.info("[STATS] - NAsmEnvironment execution time: " + NAsmExecStats.convertExecutionTime(NAsmExecStats.getExecutionTime()));
  }


  private static String getClassName(Class<?> class_) {
    String name = class_.getSimpleName();
    if (!class_.getName().startsWith("com.nim") && !class_.getSimpleName().startsWith("NAsm")) {
      if (class_.getSuperclass() != null) {
        name = class_.getSuperclass().getSimpleName();
      }else {
        logger.info("");
      }
    }
    return name;
  }
  
}
