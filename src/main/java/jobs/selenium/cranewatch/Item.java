package jobs.selenium.cranewatch;

class Item {
  private String location;
  private String latitude;
  private String projectName;
  private String description;
  private String owner;
  private String cost;
  private String status;
  private String estimatedCompletion;
  private String accessURL;

  Item(
      String location,
      String latitude,
      String projectName,
      String description,
      String owner,
      String cost,
      String status,
      String estimatedCompletion,
      String accessURL) {
    this.location = location;
    this.latitude = latitude;
    this.projectName = projectName;
    this.description = description;
    this.owner = owner;
    this.cost = cost;
    this.status = status;
    this.estimatedCompletion = estimatedCompletion;
    this.accessURL = accessURL;
  }

  public String getLocation() {
    return location;
  }

  public String getLatitude() {
    return latitude;
  }

  public String getProjectName() {
    return projectName;
  }

  public String getDescription() {
    return description;
  }

  public String getOwner() {
    return owner;
  }

  public String getCost() {
    return cost;
  }

  public String getStatus() {
    return status;
  }

  public String getEstimatedCompletion() {
    return estimatedCompletion;
  }

  public String getAccessURL() {
    return accessURL;
  }
}
