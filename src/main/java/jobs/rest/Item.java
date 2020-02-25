package jobs.rest;

/*
 * Item class stores individual record until it's stored to excel file
 * */
class Item {

  private String zipCode;
  private String recordID;
  private String streetName;
  private String status;

  Item(String zipCode, String recordID, String streetName, String status) {
    this.zipCode = zipCode;
    this.recordID = recordID;
    this.streetName = streetName;
    this.status = status;
  }

  String getZipCode() {
    return zipCode;
  }

  String getRecordID() {
    return recordID;
  }

  String getStreetName() {
    return streetName;
  }

  String getStatus() {
    return status;
  }
}
