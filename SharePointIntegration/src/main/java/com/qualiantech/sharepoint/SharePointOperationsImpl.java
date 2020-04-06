package com.qualiantech.sharepoint;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;

import com.qualiantech.sharepoint.connections.InitializeSharePointProperty;
import com.qualiantech.sharepoint.connections.SharePointConnections;
import com.qualiantech.sharepoint.constant.SharepointConstant;

public class SharePointOperationsImpl implements SharepointOperations {

  Properties property = new Properties();

  public SharePointOperationsImpl() {
    InitializeSharePointProperty intialize = new InitializeSharePointProperty();
    this.property = intialize.getProperty();
  }
  
  public JSONObject getDirectoryList(String siteName) {
	  
	    JSONObject parentJson = new JSONObject();
	    JSONArray jsonArray = new JSONArray();
	    
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	    SimpleDateFormat output = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	

	    try {
	      System.out.println("directoryName"+siteName);
	      String folderName = siteName.replaceAll("\\s", "%20").concat("/");
	      System.out.println("folderName"+folderName);
	      Pair<String, String> token = SharePointConnections.login(property.getProperty("Username"),
	          property.getProperty("password"), SharepointConstant.DOMAIN_NAME);

	      if (token != null) {

	        String jsonString = get(token, SharepointConstant.DOMAIN_NAME,
	            SharepointConstant.DIRECORY_URL);
	        System.out.println("jsonString"+jsonString);
	        if (jsonString != null) {

		          JSONObject json = new JSONObject(jsonString);
		          String fileName = "", filemodified = "", fileuri="";
		          

		          if(json.getJSONObject("d").getJSONObject("Files").optJSONArray("results")!=null) {
		          for (int i = 0; i < json.getJSONObject("d").getJSONObject("Files").optJSONArray("results").length(); i++) {
		        	  
			         JSONObject childJson = new JSONObject();

		        	filemodified = json.getJSONObject("d").getJSONObject("Files").getJSONArray("results").getJSONObject(i)
		            		 .getString("TimeLastModified");
		            fileName = json.getJSONObject("d").getJSONObject("Files").getJSONArray("results").getJSONObject(i)
		                .getString("Name");
		            fileuri= json.getJSONObject("d").getJSONObject("Files").getJSONArray("results").getJSONObject(i)
	              		  .getJSONObject("__metadata").getString("uri");
		            
		            Date d = sdf.parse(filemodified);
		    	    String formattedTime = output.format(d);
		            
		            childJson.put("uri", fileuri);
		            childJson.put("name", fileName);
		            childJson.put("modified", formattedTime);
		            childJson.put("isFolder", false);

		            jsonArray.put(childJson);
		          }
		          }
		          
		          if(json.getJSONObject("d").getJSONObject("Folders").optJSONArray("results")!=null) {
		          
		          for (int i = 0; i < json.getJSONObject("d").getJSONObject("Folders").getJSONArray("results").length(); i++) {
			        	 JSONObject childJson = new JSONObject();

		        	  filemodified = json.getJSONObject("d").getJSONObject("Folders").getJSONArray("results").getJSONObject(i)
		                 .getString("TimeLastModified");
		              fileName = json.getJSONObject("d").getJSONObject("Folders").getJSONArray("results").getJSONObject(i)
		                  .getString("Name");
		              fileuri= json.getJSONObject("d").getJSONObject("Folders").getJSONArray("results").getJSONObject(i)
		                		  .getJSONObject("__metadata").getString("uri");
		                  
		             
		              
			            Date d = sdf.parse(filemodified);
			    	    String formattedTime = output.format(d);
			            
			            childJson.put("uri", fileuri);
			            childJson.put("name", fileName);
			            childJson.put("modified", formattedTime);
			            childJson.put("isFolder", true);
			            jsonArray.put(childJson);

		            }
		          }
		          
		          parentJson.put("data", jsonArray);
		          parentJson.put("homeurl", "https://qualiantech.sharepoint.com/sites/example1//_api/Web/GetFolderByServerRelativeUrl('')");

		        
	        }
	      } else {
	        System.err.println("Login failed");
	      }
	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }

	    return parentJson;
	  }

  public List<String> getFilesInDirectory(String directoryName) {

    List<String> fileNameList = new ArrayList<String>();

    try {
      System.out.println("directoryName"+directoryName);
      String folderName = directoryName.replaceAll("\\s", "%20");
      System.out.println("folderName"+folderName);
      Pair<String, String> token = SharePointConnections.login(property.getProperty("Username"),
          property.getProperty("password"), SharepointConstant.DOMAIN_NAME);

      if (token != null) {

        String jsonString = get(token, SharepointConstant.DOMAIN_NAME,
            String.format(SharepointConstant.GET_URL, folderName));
        System.out.println("jsonString"+jsonString);
        if (jsonString != null) {
          JSONObject json = new JSONObject(jsonString);
          String fileName = "", fileRelPath = "";

          for (int i = 0; i < json.getJSONObject("d").getJSONObject("Files").getJSONArray("results").length(); i++) {
            fileRelPath = json.getJSONObject("d").getJSONObject("Files").getJSONArray("results").getJSONObject(i)
                .getString("ServerRelativeUrl"); 
            fileName = json.getJSONObject("d").getJSONObject("Files").getJSONArray("results").getJSONObject(i)
                .getString("Name").concat("_f");
            fileNameList.add(fileName);
          }
          for (int i = 0; i < json.getJSONObject("d").getJSONObject("Folders").getJSONArray("results").length(); i++) {
              fileRelPath = json.getJSONObject("d").getJSONObject("Folders").getJSONArray("results").getJSONObject(i)
                  .getString("ServerRelativeUrl");
              fileName = json.getJSONObject("d").getJSONObject("Folders").getJSONArray("results").getJSONObject(i)
                  .getString("Name").concat("_fo");;
              fileNameList.add(fileName);
            }
        }
      } else {
        System.err.println("Login failed");
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    return fileNameList;
  }

  public String copyFile(String pathtosave, String url,
      String newfilename) {
	  String success_Message = "false";
    try {
      Pair<String, String> token = SharePointConnections.login(property.getProperty("Username"),
          property.getProperty("password"), SharepointConstant.DOMAIN_NAME);

      if (token != null) {
        InputStream file =SharePointConnections.getFile(
            token, SharepointConstant.DOMAIN_NAME, url,
            newfilename, pathtosave);
        if(file!=null)
        {
        	success_Message ="true";
        }

      } else {
        System.err.println("Login failed");
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
     return success_Message;
  }

  public void uploadFile(String localFilePath, String localName, String uploadDirectoryName,
      String uploadFileName) {

    try {
      Pair<String, String> token = SharePointConnections.login(property.getProperty("Username"),
          property.getProperty("password"), SharepointConstant.DOMAIN_NAME);

      if (token != null) {
        String jsonString = upload(token, SharepointConstant.DOMAIN_NAME, "_api/contextinfo", null,
            null);
        System.out.println("jsonString" + jsonString);
        JSONObject json = new JSONObject(jsonString);
        String formDigestValue = json.getJSONObject("d").getJSONObject("GetContextWebInformation")
            .getString("FormDigestValue");

        upload(token, SharepointConstant.DOMAIN_NAME, String.format(SharepointConstant.UPLOAD_URL,
            uploadDirectoryName.replaceAll("\\s", "%20"), uploadFileName.replaceAll("\\s", "%20")),
            new File(localFilePath + localName), formDigestValue);

      } else {
        System.err.println("Login failed");
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }

  }

  public String deleteFile(String url) {

	  String delete_Message=null;
    try {
      Pair<String, String> token = SharePointConnections.login(property.getProperty("Username"),
          property.getProperty("password"), SharepointConstant.DOMAIN_NAME);

      if (token != null) {

        String jsonString = upload(token, SharepointConstant.DOMAIN_NAME, "_api/contextinfo", null,
            null);

        System.out.println("jsonString" + jsonString);
        JSONObject json = new JSONObject(jsonString);
        String formDigestValue = json.getJSONObject("d").getJSONObject("GetContextWebInformation")
            .getString("FormDigestValue");
       
         delete_Message=  SharePointConnections.delete(
            token, SharepointConstant.DOMAIN_NAME,url,
            formDigestValue);

      } else {
        System.err.println("Login failed");
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return delete_Message;

  }

  public static String get(Pair<String, String> token, String url) {
    CloseableHttpClient httpClient = HttpClients.createDefault();
    try {
      HttpGet getRequest = new HttpGet(url);
      getRequest.addHeader("Cookie", token.getLeft() + ";" + token.getRight());
      getRequest.addHeader("accept", "application/json;odata=verbose");
      HttpResponse response = httpClient.execute(getRequest);

      if (response.getStatusLine().getStatusCode() == 200) {
        return IOUtils.toString(response.getEntity().getContent(), "utf-8");
      } else {
        System.err.println(
            "Failed : HTTP error code : " + response.getStatusLine().getStatusCode() + ", " + url);
        return null;
      }
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        httpClient.close();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
    return null;
  }

  public static String get(Pair<String, String> token, String domain, String path) {
    System.out.print("path" +path);
    return get(token,
        "https://" + domain + ".sharepoint.com/" + SharepointConstant.SITE_NAME + "/" + path);
  }
  
  public static String getUrl(Pair<String, String> token, String url) {
	    return get(token,url);
	  }

  public static String upload(Pair<String, String> token, String domain, String path, File data,
      String formDigestValue) {
    return upload(token, domain, path, data, formDigestValue, false);
  }

  public static String upload(Pair<String, String> token, String domain, String path, File data,
      String formDigestValue, boolean isXHttpMerge) {
    System.out.println("ur" + "https://" + domain + ".sharepoint.com/"
        + SharepointConstant.SITE_NAME + "/" + path);
    CloseableHttpClient httpClient = HttpClients.createDefault();
    try {
      HttpPost postRequest = new HttpPost(

          "https://" + domain + ".sharepoint.com/" + SharepointConstant.SITE_NAME + "/" + path);
      System.out.println("postRequest" + postRequest);
      postRequest.addHeader("Cookie", token.getLeft() + ";" + token.getRight());
      postRequest.addHeader("accept", "application/json;odata=verbose");
      postRequest.setHeader("Content-Type", "c");
      postRequest.addHeader("X-RequestDigest", formDigestValue);
      postRequest.addHeader("IF-MATCH", "*");

      if (isXHttpMerge) {
        postRequest.addHeader("X-HTTP-Method", "MERGE");
      }

      if (data != null) {

        @SuppressWarnings("deprecation")
        FileEntity reqEntity = new FileEntity(data, "ContentType.APPLICATION_OCTET_STREAM");
        postRequest.setEntity(reqEntity);
      }

      HttpResponse response = httpClient.execute(postRequest);

      if (response.getStatusLine().getStatusCode() != 200
          && response.getStatusLine().getStatusCode() != 204) {
        System.out.println(
            "Failed : HTTP error code : " + response.getStatusLine().getStatusCode() + ", " + path);
      }
      if (response.getEntity() == null || response.getEntity().getContent() == null) {
        return null;
      } else {
        return IOUtils.toString(response.getEntity().getContent(), "utf-8");
      }
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        httpClient.close();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
    return null;
  }

  public static String delete(Pair<String, String> token, String domain, String path,
      String formDigestValue) {
    CloseableHttpClient httpClient = HttpClients.createDefault();
    try {
      HttpDelete deleteRequest = new HttpDelete(
          "https://" + domain + ".sharepoint.com/" + SharepointConstant.SITE_NAME + "/" + path);
      deleteRequest.addHeader("Cookie", token.getLeft() + ";" + token.getRight());
      deleteRequest.addHeader("accept", "application/json;odata=verbose");
      deleteRequest.addHeader("content-type", "application/json;odata=verbose");
      deleteRequest.addHeader("X-RequestDigest", formDigestValue);
      deleteRequest.addHeader("IF-MATCH", "*");
      HttpResponse response = httpClient.execute(deleteRequest);
      if (response.getStatusLine().getStatusCode() != 200
          && response.getStatusLine().getStatusCode() != 204) {
        System.out
            .println("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
        // logger.error("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
      }
      if (response.getEntity() == null || response.getEntity().getContent() == null) {
        return null;
      } else {
        return IOUtils.toString(response.getEntity().getContent(), "utf-8");
      }
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        httpClient.close();
      } catch (IOException ex) {
        ex.printStackTrace();
        // Logger.getLogger(SPOnline.class).error(ex);
      }
    }

    return null;
  }
  
  public JSONObject getDirectoryListFilesFolders(String url) {
	  
	    JSONObject parentJson = new JSONObject();
	    JSONArray jsonArray = new JSONArray();
	    
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	    SimpleDateFormat output = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");


	    try {

	      Pair<String, String> token = SharePointConnections.login(property.getProperty("Username"),
	          property.getProperty("password"), SharepointConstant.DOMAIN_NAME);

	      if (token != null) {

	        String jsonString = get(token, url);
	        System.out.println("jsonString"+jsonString);
	        if (jsonString != null) {
	          JSONObject json = new JSONObject(jsonString);
	          String fileName = "", filemodified = "", fileuri="";
	          

	          if(json.getJSONObject("d").getJSONObject("Files").optJSONArray("results")!=null) {
	          for (int i = 0; i < json.getJSONObject("d").getJSONObject("Files").getJSONArray("results").length(); i++) {
	        	  
		         JSONObject childJson = new JSONObject();

	        	filemodified = json.getJSONObject("d").getJSONObject("Files").getJSONArray("results").getJSONObject(i)
	            		 .getString("TimeLastModified");
	            fileName = json.getJSONObject("d").getJSONObject("Files").getJSONArray("results").getJSONObject(i)
	                .getString("Name");
	            fileuri= json.getJSONObject("d").getJSONObject("Files").getJSONArray("results").getJSONObject(i)
              		  .getJSONObject("__metadata").getString("uri");
	            
	            Date d = sdf.parse(filemodified);
	    	    String formattedTime = output.format(d);
	            
	            childJson.put("uri", fileuri);
	            childJson.put("name", fileName);
	            childJson.put("modified", formattedTime);
	            childJson.put("isFolder", false);

	            jsonArray.put(childJson);
	          }}
	          
	          if(json.getJSONObject("d").getJSONObject("Folders").optJSONArray("results")!=null) {
	          
	          for (int i = 0; i < json.getJSONObject("d").getJSONObject("Folders").getJSONArray("results").length(); i++) {
		        	 JSONObject childJson = new JSONObject();

	        	  filemodified = json.getJSONObject("d").getJSONObject("Folders").getJSONArray("results").getJSONObject(i)
	                 .getString("TimeLastModified");
	              fileName = json.getJSONObject("d").getJSONObject("Folders").getJSONArray("results").getJSONObject(i)
	                  .getString("Name");
	              fileuri= json.getJSONObject("d").getJSONObject("Folders").getJSONArray("results").getJSONObject(i)
	                		  .getJSONObject("__metadata").getString("uri");
	                  
	              
	              
		            Date d = sdf.parse(filemodified);
		    	    String formattedTime = output.format(d);
		            
		            childJson.put("uri", fileuri);
		            childJson.put("name", fileName);
		            childJson.put("modified", formattedTime);
		            childJson.put("isFolder", true);

		            jsonArray.put(childJson);

	            }
	          }
	          
	          parentJson.put("data", jsonArray);
	        }
	      } else {
	        System.err.println("Login failed");
	      }
	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }

	    return parentJson;
	  }
}
