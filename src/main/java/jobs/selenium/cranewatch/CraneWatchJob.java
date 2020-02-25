package jobs.selenium.cranewatch;

import java.util.TimerTask;

public class CraneWatchJob extends TimerTask {

  /*
   * Basic job configuration
   * */
  private void jobBuilder() {
    try {
      new PageProcessor();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void run() {
    jobBuilder();
  }
}
