
package com.qualiantech.sharepoint.main;

import com.qualiantech.sharepoint.SharePointOperationsImpl;
import com.qualiantech.sharepoint.SharepointOperations;

public class DeleteFileImplementation {

  public static void main(String[] args) {

    try {

      SharepointOperations operations = new SharePointOperationsImpl();
      operations.deleteFile("/Shared Documents", "TestSPonline.java");

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}
