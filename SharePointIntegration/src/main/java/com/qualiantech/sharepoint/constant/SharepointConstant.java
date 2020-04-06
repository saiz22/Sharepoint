package com.qualiantech.sharepoint.constant;

public class SharepointConstant {

  public static String DOMAIN_NAME = "qualiantech";
  public static String SITE_NAME = "sites/example1";
  
  public static String MICROSOFT_URL = "https://login.microsoftonline.com/extSTS.srf";
  public static String CONTEXT_URL = "https://qualiantech.sharepoint.com/_api/contextinfo";
  public static String GET_URL = "_api/web/GetFolderByServerRelativeUrl('%s')/?$expand=Folders,Files";
  public static String COPY_URL = "_api/Web/GetFileByServerRelativePath(decodedurl='/sites/example1/%s/%s')/$value";
  public static String DELETE_URL = "_api/web/GetFileByServerRelativePath(decodedurl='/sites/example1/%s/%s')";
  public static String UPLOAD_URL = "/_api/web/GetFolderByServerRelativeUrl('%s')/Files/add(url='%s',overwrite=true)";
  public static String DIRECORY_URL = "/_api/Web/GetFolderByServerRelativeUrl('')/?$expand=Folders,Files";

}
