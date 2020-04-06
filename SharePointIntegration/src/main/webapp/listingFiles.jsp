<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% List<String> fileList =  (request.getAttribute("files")==null?null:( List<String>)  request.getAttribute("files"));%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<table>
<%if(fileList.size()>0){ for(String file: fileList) {%>

   
	 <tr>
          <td> <a href="ListFilesFromDirectory?directory=<%=file%>"> <%=file%></a></td>
            <td>
        </tr>
        <%}} %> 
        
        
     
</table>
</body>
</html>



