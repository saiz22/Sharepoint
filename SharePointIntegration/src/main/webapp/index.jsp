<html>
<body>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
<script type="text/javascript" src="js/jquery-3.4.1.min.js"></script>
<script src="https://kit.fontawesome.com/cdc0ab35ee.js" crossorigin="anonymous"></script>
<Style>
tbody {
    display:block;
    height:500px;
    overflow:auto;
}
thead, tbody tr {
    display:table;
    width:100%;
    table-layout:fixed;
}
thead {
    
}</Style>

<div class="container-fluid">

<div class="invisible"><p id='homeurl'></p></div>


<nav class="navbar navbar-light bg-light">
  <a class="navbar-brand" href="#">
    <img src="images/Qualian.jpeg" width="60" height="60" class="d-inline-block align-top" alt="">
  </a>
  <div class="mx-auto">
  <h5 class="display-5">SHAREPOINT INTEGRATION</h5>
</div>

</nav>
<div class="mt-4"></div> <div class="mt-4"></div> <div class="mt-4"></div>

<ul class="nav nav-tabs">
  <li class="nav-item">
    <a class="nav-link active" href="#" onclick="theFunction();">Folders/Files</a>
  </li>
</ul>

</div>


<div id='content' class="container-fluid">
</div>




<script type="text/javascript">
    function theFunction () {
    		$.ajax({
    			url : 'ListFiles',
    			data : {
    				
    			},
    			success : function(responseText) {
    				var tablehtml ='<table class="table" > <thead > <tr> <th scope="col">#</th><th scope="col">Name</th><th scope="col">Modified</th><th scope="col"></th><th scope="col"></th><th scope="col"></th></tr></thead><tbody>';
    				var data =responseText.data;
    				var url= responseText.homeurl;
    				for(i=0;i<data.length;i++){
    					tablehtml= tablehtml+ 
    					                  ' <tr> <th scope="row">'+(i+1)+'</th>';
    					                  if(!data[i].isFolder){
    					                	  tablehtml=tablehtml+  '<td > <Span><img src="images/FileIcon.png" width="20" height="20" class="d-inline-block align-top" alt="Img"><Span> &nbsp; '+data[i].name+'</td>'
    					                  }else{
    					                	  tablehtml=tablehtml+ ' <td> <Span><img src="images/Foldericon.png" width="20" height="20" class="d-inline-block align-top" alt="Img"><Span> &nbsp; <a  id="'+data[i].uri+'"href="#" onclick="getDirectoryFiles(this.id,false);">'+data[i].name+'</td>';
    					                  }
    					                  
    					                  tablehtml=tablehtml+  ' <td>'+data[i].modified+'</td>';
    					                  
    					                  if(data[i].isFolder){
    					                	  tablehtml=tablehtml+'<td align="center" > <button type="button" class="btn btn-secondary"><i class="fa fa-upload"></i></button></td>';
    					                  }
    					                  
    					                  tablehtml=tablehtml+' <td align="center">'+'<button type="button" class="btn btn-success" id='+data[i].uri+' name='+data[i].name+' onclick="getDownloadFile(this.id,this.name);"><i class="fas fa-download"></i></button></td>'+
    					                  ' <td align="center">'+'<button type="button" class="btn btn-danger" id='+data[i].uri+' onclick="getDeleteFiles(this.id);"><i class="fas fa-trash-alt"></i></button></td></tr>';
    				}
					  $('#content').html(tablehtml);      
					  $('#homeurl').text(url);                                   				
    			}
    		});


    	}
    
    
    function getDirectoryFiles(uri,isback) {
		$.ajax({
			url : 'ListFilesFromDirectory?URL='+uri+'&isback='+isback,
			data : {
				
			},
			success : function(responseText) {
				
				var islast=false;
				var backUrl= $('#homeurl').text();

				var backUrls= backUrl.split(",https://");
				var backUrlLength= backUrls.length;
				if(isback){
					if(backUrls.length<3){
						backUrl=uri;
						islast=true;
					}else{						
						backUrl = backUrls.length>0 ? backUrls[backUrlLength-3].includes('https://')?backUrls[backUrlLength-3]:('https://')+backUrls[backUrlLength-3]:backUrls[0];
					}

				}else{
					backUrl = backUrls.length>0 ? 'https://'+backUrls[backUrlLength-1]:backUrls[0];

				}
				
			
				
				if(!islast){
					var tablehtml ='<table class="table" > <thead > <tr> <th scope="col">#</th><th scope="col">Name</th><th scope="col">Modified</th><th scope="col"></th><th scope="col"></th><th scope="col"></th></tr></thead><tbody><tr> <td><Span><img src="images/BackIcon.png" width="30" height="20" class="d-inline-block align-top" alt="Img"><a id="'+backUrl+'"href="#" onclick="getDirectoryFiles(this.id,true);">Back</td><td></td><td></td><td></td> </tr>';
				}else{
					var tablehtml ='<table class="table" > <thead > <tr> <th scope="col">#</th><th scope="col">Name</th><th scope="col">Modified</th><th scope="col"></th><th scope="col"></th><th scope="col"></th></tr></thead><tbody>';

				}
				var data =responseText.data;
				var url= responseText.BackURL;

				for(i=0;i<data.length;i++){
					tablehtml= tablehtml+ 
	                  ' <tr> <th scope="row">'+(i+1)+'</th>';
	                  if(!data[i].isFolder){
	                	  tablehtml=tablehtml+  '<td> <Span><img src="images/FileIcon.png" width="20" height="20" class="d-inline-block align-top" alt="Img"><Span> &nbsp; '+data[i].name+'</td>'
	                  }else{
	                	  tablehtml=tablehtml+ ' <td> <Span><img src="images/Foldericon.png" width="20" height="20" class="d-inline-block align-top" alt="Img"><Span> &nbsp; <a  id="'+data[i].uri+'"href="#" onclick="getDirectoryFiles(this.id,false);">'+data[i].name+'</td>';
	                  }
	                  
	                  tablehtml=tablehtml+  ' <td>'+data[i].modified+'</td>';
	                  
	                  if(data[i].isFolder){
	                	  tablehtml=tablehtml+'<td align="center" > <button type="button" class="btn btn-secondary"><i class="fa fa-upload"></i></button></td>';
	                  }else{
	                	  tablehtml=tablehtml+ '<td ></td >';
	                  }
	                  tablehtml=tablehtml+' <td align="center">'+'<button type="button" class="btn btn-success" id='+data[i].uri+' name='+data[i].name+' onclick="getDownloadFile(this.id,this.name);"><i class="fas fa-download"></i></button></td>'+
	                  ' <td align="center">'+'<button type="button" class="btn btn-danger" id='+data[i].uri+' onclick="getDeleteFiles(this.id);"><i class="fas fa-trash-alt"></i></button></td></tr>';
}			
				  $('#content').html(tablehtml);  
				  
				  
				  if(!isback){
					  var urls = [];;
					  urls.push($('#homeurl').text());
					  urls.push(uri);
				   }else{
					   var urls = [];
					   
					   if(!islast){
					 
						for(j=0;j<backUrls.length-1;j++){
							if(!backUrls[j].includes("https://")){
								urls.push("https://"+backUrls[j]);
							}else{
								urls.push(backUrls[j]);
							}
							  
						}  
					   }else{
						   
						   urls.push(backUrl)
					   }
				  }
				  
				  $('#homeurl').text(urls);
				  
				  document.getElementById("homeurl").innerHTML = urls; 

				  
				 

			}
		});


	}
    
    
function getDeleteFiles(url) {
    	
    	if(confirm('Are you sure to delete')){
    		 $.ajax({
			url : 'DeleteFiles?URL='+url+'&backurl='+document.getElementById("homeurl").innerHTML,
			data : {
				
			},
    		 success : function(responseText) {
 			  if(responseText = "true")
 				{
 	 			alert("Deleted Successfully");
			     return true;
 				}

 			}
		}); 
      }
      	else
    		{
        return false;
    		}
	


	}
    
  function getDownloadFile(url,name) {
	 $.ajax({
			url : 'DownloadFile?URL='+url+'&backurl='+document.getElementById("homeurl").innerHTML+'&name='+name,
			data : {
				
			},
			 success : function(responseText) {
	 			  if(responseText = "true")
	 				{
	 	 			alert("Downloaded Successfully");
				     return true;
	 				}

	 			}
	 });
	
} 
     
    	
</script>


</body>
</html>
