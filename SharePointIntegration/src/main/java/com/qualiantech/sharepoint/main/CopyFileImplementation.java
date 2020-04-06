package com.qualiantech.sharepoint.main;

import com.qualiantech.sharepoint.SharePointOperationsImpl;
import com.qualiantech.sharepoint.SharepointOperations;

public class CopyFileImplementation {

  public static void main(String[] args) {

    try {

      SharepointOperations operations = new SharePointOperationsImpl();
      operations.copyFile("/Users/qualian/Downloads", "Shared Documents", "budgetRevisionIssue.sql",
          "DocumentSP1.sql");
      

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}
