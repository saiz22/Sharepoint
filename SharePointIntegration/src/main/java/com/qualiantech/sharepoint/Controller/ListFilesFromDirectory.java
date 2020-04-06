package com.qualiantech.sharepoint.Controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.qualiantech.sharepoint.SharePointOperationsImpl;
import com.qualiantech.sharepoint.SharepointOperations;
import com.qualiantech.sharepoint.constant.SharepointConstant;

/**
 * Servlet implementation class ListFilesFromDirectory
 */
public class ListFilesFromDirectory extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListFilesFromDirectory() {
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
		 String URL = request.getParameter("URL");
		 
		 SharepointOperations operations = new SharePointOperationsImpl();
	     JSONObject fileList = operations.getDirectoryListFilesFolders(URL.replaceAll(" ", "%20")+"/?$expand=Folders,Files");
	      
	     response.setContentType("text/json");
         response.setCharacterEncoding("UTF-8");
         response.setHeader("Cache-Control", "no-cache");
         response.getWriter().write(fileList.toString());
	}
		  
}
