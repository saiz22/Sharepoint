package com.qualiantech.sharepoint.main;

import java.util.List;

import com.qualiantech.sharepoint.SharePointOperationsImpl;
import com.qualiantech.sharepoint.SharepointOperations;
import com.qualiantech.sharepoint.constant.SharepointConstant;

public class ListImplementation {

  public static void main(String[] args) {

    try {

      SharepointOperations operations = new SharePointOperationsImpl();
      List<String> fileList = operations.getFilesInDirectory("Shared Documents");

      for (String name : fileList) {
        System.out.print("FileName----" + name);
      }

    } catch (Exception e) {

    }

  }

}
