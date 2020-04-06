package com.qualiantech.sharepoint.Controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;

import com.qualiantech.sharepoint.SharePointOperationsImpl;
import com.qualiantech.sharepoint.SharepointOperations;
import com.qualiantech.sharepoint.constant.SharepointConstant;

/**
 * Servlet implementation class DownloadFile
 */
public class DownloadFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadFile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 SharepointOperations operations = new SharePointOperationsImpl();
		 String URL = request.getParameter("URL");
		 String fileName = request.getParameter("name");
		 int randomInteger = (int) (100 * Math.random());
		 String sucess_message = "false";
		 String[] parts = fileName.split("\\.(?=[^\\.]+$)");
			
         
		 String fileName_before = parts[0].concat("(" +String.valueOf(randomInteger)+ ")");
		 String fileName_after = parts[1];
		 
		 sucess_message=  operations.copyFile("/Users/qualian/Downloads", URL.replaceAll(" ", "%20")+"/$value",
				  fileName_before.concat("." +fileName_after));
			response.setContentType("text/json");
	        response.setCharacterEncoding("UTF-8");
	        response.setHeader("Cache-Control", "no-cache");
	        response.getWriter().write(sucess_message.toString());

		 
		     
	}

}
