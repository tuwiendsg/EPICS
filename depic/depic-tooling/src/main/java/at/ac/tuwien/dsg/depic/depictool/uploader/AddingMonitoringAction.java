/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.depictool.uploader;

import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.Artifact;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.MonitoringAction;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.Parameter;

import at.ac.tuwien.dsg.depic.common.utils.Configuration;
import at.ac.tuwien.dsg.depic.repository.PrimitiveActionMetadataManager;

import java.io.IOException;

import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


/**
 *
 * @author Jun
 */
@WebServlet(name = "AddingMonitoringAction", urlPatterns = {"AddingMonitoringAction"})
public class AddingMonitoringAction extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddingMonitoringAction</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddingMonitoringAction at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
      
        
        if (ServletFileUpload.isMultipartContent(request)) {
            try {
                List<FileItem> multiparts = new ServletFileUpload(
                        new DiskFileItemFactory()).parseRequest(request);

                int noOfParameters = 0;
                
                MonitoringAction monitoringAction = new MonitoringAction();
                Artifact artifact = new Artifact();
                
                for (FileItem item : multiparts) {
                    if (!item.isFormField()) {
                       

                    } else {
                        
                        String fieldname = item.getFieldName();
                        String fieldvalue = item.getString();
                        
                        if (fieldname.equals("MonitoringActionName")){
                            System.out.println(fieldvalue);
                            monitoringAction.setMonitoringActionName(fieldvalue);
                            monitoringAction.setMonitorActionID(fieldvalue);
                        }
                        
                        
                        
                        if (fieldname.equals("AssociatedQoRMetric")){  
                            System.out.println(fieldvalue);
                            monitoringAction.setAssociatedQoRMetric(fieldvalue);
                        }
                        
                        if (fieldname.equals("ArtifactID")){  
                            System.out.println(fieldvalue);
                            artifact.setName(fieldvalue);
                        }
                        
                        if (fieldname.equals("ArtifactDescription")){  
                            System.out.println(fieldvalue);
                            artifact.setDescription(fieldvalue);
                           
                        }
                        
                        if (fieldname.equals("Location")){  
                            System.out.println(fieldvalue);
                            artifact.setLocation(fieldvalue);
                        }
                        
                        if (fieldname.equals("Type")){  
                            System.out.println(fieldvalue);
                            artifact.setType(fieldvalue);
                        }
                        
                        if (fieldname.equals("RESTfulAPI")){  
                            System.out.println(fieldvalue);
                            artifact.setRestfulAPI(fieldvalue);
                        }
                        
                        if (fieldname.equals("HttpMethod")){  
                            System.out.println(fieldvalue);
                            artifact.setHttpMethod(fieldvalue);
                        }
                        
                        
                        
                        if (fieldname.equals("noOfParameters")){  
                            System.out.println(fieldvalue);
                            noOfParameters = Integer.parseInt(fieldvalue);
                        }
                        
                      
                        
                    }
                }
                List<Parameter> listOfParameters = new ArrayList<Parameter>();
                for (int i=1;i<=noOfParameters;i++){
                    Parameter parameter = getParameter(i,multiparts);
                    listOfParameters.add(parameter);
                }
                
                monitoringAction.setArtifact(artifact);
                monitoringAction.setListOfParameters(listOfParameters);
                
                PrimitiveActionMetadataManager pamm = new PrimitiveActionMetadataManager();
                pamm.storeMonitoringAction(monitoringAction);
                
                request.setAttribute("message", "File Uploaded Successfully");
            } catch (Exception ex) {
                request.setAttribute("message", "File Upload Failed due to " + ex);
            }

        } else {
            request.setAttribute("message",
                    "Sorry this Servlet only handles file upload request");
        }
        
        request.getRequestDispatcher("/pam.jsp").forward(request, response);
    }
    
    
    private Parameter getParameter(int parameterIndex, List<FileItem> multiparts){
        
        
        String paramName = "";
        String paramType = "";
        String paramValue = "";
          for (FileItem item : multiparts) {
                    if (!item.isFormField()) {
                       

                    } else {
                        
                        String fieldname = item.getFieldName();
                        String fieldvalue = item.getString();
                        
                       
                        
                        if (fieldname.equals("ParameterName"+parameterIndex)){

                            System.out.println("param: " +fieldvalue);
                            paramName = fieldvalue;
                                                        
                        }
                        
                        if (fieldname.equals("Type"+parameterIndex)){
                            System.out.println("param: " +fieldvalue);
                            paramType = fieldvalue;
                        }
                        
                        if (fieldname.equals("ParameterValue"+parameterIndex)){
                            System.out.println("param: " +fieldvalue);
                            paramValue = fieldvalue;
                        }
                        
                       
                    }
                }
        Parameter parameter = null;
          if (!paramName.equals("")){
              parameter = new Parameter(paramName, paramType, paramValue);
          }
          
        return parameter;
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
