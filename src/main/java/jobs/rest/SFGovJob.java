package jobs.rest;

import java.util.TimerTask;

public class SFGovJob extends TimerTask {

  /*
   * Basic job configuration
   * */
  private void jobBuilder() {
    try {
      JSONProcessor.process();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void run() {
    jobBuilder();
  }
}
