package com.qualiantech.sharepoint;

import java.util.List;

import org.json.JSONObject;

public interface SharepointOperations {

  /**
   * This method is used to get the files from the given directory of sharepoint
   * 
   * @param directoryName
   * @return list of files in the given directory
   */

  public List<String> getFilesInDirectory(String directoryName);

  /**
   * This method is used to copy the files from sharepoint in the particular directory
   * 
   * @param pathtosave
   * @param directoryName
   * @param fileName
   * @param new
   *          filename
 * @return 
   */

  public String copyFile(String pathtosave, String directoryName, 
      String newfileName);

  /**
   * This method is used to upload the file in the given directory of SharePoint
   * 
   * @param file
   * @param directoryName
   */
  public void uploadFile(String localfilepath, String localfilename, String uploadDirectoryName,
      String uploadfilename);

  /**
   * This method is used to delete the files from the sharepoint
  /**
   * @param url
 * @return 
   */

  public String deleteFile(String url);
  /**
   * 
   * @param siteName
   */
  public JSONObject getDirectoryList(String siteName);
  
  /**
   * 
   * @param URL
   */
  public JSONObject getDirectoryListFilesFolders(String url);
  
  
  

}
