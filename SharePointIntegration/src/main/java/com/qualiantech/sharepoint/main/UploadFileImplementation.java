package com.qualiantech.sharepoint.main;

import com.qualiantech.sharepoint.SharePointOperationsImpl;
import com.qualiantech.sharepoint.SharepointOperations;

public class UploadFileImplementation {

  public static void main(String[] args) {

    try {

      SharepointOperations operations = new SharePointOperationsImpl();
      operations.uploadFile("/Users/qualian/Downloads/", "TestSPOnline.java",
          "Shared Documents", "TestSPOnline.java");

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}
