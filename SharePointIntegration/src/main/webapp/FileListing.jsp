<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% List<String> fileList =  (request.getAttribute("files")==null?null:( List<String>)  request.getAttribute("files"));%>
<% String directoryName      =  (request.getAttribute("directoryName")==null?null:( String)  request.getAttribute("directoryName"));%>
<html>
<head>
<style>
.nav {
    border: 2px solid white;
}

.nav td { position: relative; }

.nav table,
.nav ul { 
    display: none;
    position: absolute;
    list-style: none;
    margin: 0;
    padding: 0;
    width: 300px;
    left: 0;
}

.nav td:hover table { display: table; /* or display: block; */ }
.nav td:hover ul { display: block; }
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<table>
<%if(fileList.size()>0){ 
	for(String file: fileList) {%>
     <%String a[] = file.split("_");
         file = a[0];
         String b = a[1];
     System.out.println("hfasdhgas" + file);
     System.out.println("hg" + b);
     %>
        <tr>
      	 <td> <a href="ListFilesFromDirectory?directory=<%=(request.getAttribute("directoryName")==null?null:( String)  request.getAttribute("directoryName")) %>&folder=<%=file %>"> <%=file%></a></td>  
             <td>
           </tr>
         
       
        
         
        <%}
	} %>
</table>



 <table class="nav">
 <tr>
 <%if(fileList.size()>0){ 
	for(String file: fileList) {%>
        
    <%String a[] = file.split("_");
         file = a[0];
         String diff = a[1];
    
     %>
      	 <td>  <%=file%>
      	              <ul>
      	        <li><a href="ListFilesFromDirectory?directory=<%=(request.getAttribute("directoryName")==null?null:( String)  request.getAttribute("directoryName")) %>&folder=<%=file %>">Open</a></li>
                <li><a href="DeleteFiles?directory=<%=(request.getAttribute("directoryName")==null?null:( String)  request.getAttribute("directoryName")) %>&folder=<%=file %>"onclick="return checkDelete()">Delete</a></li>
            </ul>
            
            </td>
           <td></td><td></td>
         
       
        
         
        <%}
	} %>
</tr>
    
</table>
 
 




</body>
<script type="text/javascript">

function checkDelete(){
	if(confirm('Are you sure to delete')){
    }
	else
		{
    return false;
		}
   
}
</script>

</html>



