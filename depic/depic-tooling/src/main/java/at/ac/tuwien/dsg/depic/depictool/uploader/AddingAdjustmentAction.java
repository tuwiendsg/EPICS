/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.depictool.uploader;

import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.AdjustmentAction;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.AdjustmentCase;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.AnalyticTask;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.Artifact;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.MetricCondition;

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
@WebServlet(name = "AddingAdjustmentAction", urlPatterns = {"/AddingAdjustmentAction"})
public class AddingAdjustmentAction extends HttpServlet {

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
            out.println("<title>Servlet AddingAdjustmentAction</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddingAdjustmentAction at " + request.getContextPath() + "</h1>");
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

                int noOfPrerequisiteActions = 0;
                int noOfAdjustmentCases = 0;
                
                AdjustmentAction adjustmentAction = new AdjustmentAction();
                Artifact artifact = new Artifact();
                
                for (FileItem item : multiparts) {
                    if (!item.isFormField()) {
                       

                    } else {
                        
                        String fieldname = item.getFieldName();
                        String fieldvalue = item.getString();
                        
                        if (fieldname.equals("AdjustmentActionName")){
                            System.out.println(fieldvalue);
                            adjustmentAction.setActionName(fieldvalue);
                        }
 
                        if (fieldname.equals("AssociatedQoRMetric")){  
                            System.out.println(fieldvalue);
                            adjustmentAction.setAssociatedQoRMetric(fieldvalue);
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
                        
                        
                        
                        if (fieldname.equals("noOfPrerequisiteActions")){  
                            System.out.println("p:" + fieldvalue);
                            noOfPrerequisiteActions = Integer.parseInt(fieldvalue);
                        }
                        
                        if (fieldname.equals("noOfAdjustmentCases")){  
                            System.out.println("aa: " + fieldvalue);
                            noOfAdjustmentCases = Integer.parseInt(fieldvalue);
                        }
                        
                      
                        
                    }
                }
                
                
                
                List<String> listOfPrerequisiteActions = new ArrayList<String>();
                for (int i = 1; i<=noOfPrerequisiteActions; i++){
                    String preAction = getPrerequisiteAction(i, multiparts);
                    if (!preAction.equals("")){
                        listOfPrerequisiteActions.add(preAction);
                    }

                }
                
                
                List<AdjustmentCase> listOfAdjustmentCases = new ArrayList<AdjustmentCase>();
                for (int i=1; i<=noOfAdjustmentCases;i++){
         
                    MetricCondition estimatedResult = getEstimatedResultFrom(i, multiparts);
                    AnalyticTask analyticTask = getAnalyticTask(i, multiparts);
                    List<Parameter> listOfAdjustmentCaseParameters = getListOfAdjustmentCaseParameters(i, multiparts);
                    AdjustmentCase adjustmentCase = new AdjustmentCase(estimatedResult, analyticTask, listOfAdjustmentCaseParameters);
                    listOfAdjustmentCases.add(adjustmentCase);
              
                }
                
                
                
                
                adjustmentAction.setArtifact(artifact);
                adjustmentAction.setListOfPrerequisiteActionIDs(listOfPrerequisiteActions);
               adjustmentAction.setListOfAdjustmentCases(listOfAdjustmentCases);
  
                PrimitiveActionMetadataManager pamm = new PrimitiveActionMetadataManager(
                        getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
                pamm.storeAdjustmentAction(adjustmentAction);
                
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
    private String getPrerequisiteAction(int prerequisiteActionIndex, List<FileItem> multiparts){
        
        String prerequisiteAction="";
        
        for (FileItem item : multiparts) {
                    if (!item.isFormField()) {
                       

                    } else {
                        
                        String fieldname = item.getFieldName();
                        String fieldvalue = item.getString();
                                           
                        if (fieldname.equals("prerequisiteActionName"+prerequisiteActionIndex)){

                            System.out.println("param: " +fieldvalue);
                            prerequisiteAction = fieldvalue;
                                                        
                        }
                                          
                   }
                }
        
        return prerequisiteAction;
    }
    
    
    private MetricCondition getEstimatedResultFrom(int adjustmentCaseIndex, List<FileItem> multiparts){
        
        double estimatedResultFrom = 0;
        double estimatedResultTo =0;
        for (FileItem item : multiparts) {
                    if (!item.isFormField()) {
                       

                    } else {
                        
                        String fieldname = item.getFieldName();
                        String fieldvalue = item.getString();
                                           
                        if (fieldname.equals("es_from"+adjustmentCaseIndex)){

                            System.out.println("param: " +fieldvalue);
                            estimatedResultFrom = Double.parseDouble(fieldvalue);
                                                        
                        }
                        
                        if (fieldname.equals("es_to"+adjustmentCaseIndex)){
                            System.out.println("param: " +fieldvalue);
                            estimatedResultTo = Double.parseDouble(fieldvalue);
                        }
                        
                        
                        
                       
                    }
                }
        
        MetricCondition estimatedResult = new MetricCondition("", "", estimatedResultFrom, estimatedResultTo);
        
        
        
        
        return estimatedResult;
        
    }
    
    
    private AnalyticTask getAnalyticTask(int adjustmentCaseIndex, List<FileItem> multiparts){
        
        String analyticTaskName = "";
        
        int noOfAnalyticsTaskParameters = 0;
      
        
        for (FileItem item : multiparts) {
                    if (!item.isFormField()) {
                       

                    } else {
                        
                        String fieldname = item.getFieldName();
                        String fieldvalue = item.getString();
                                           
                        if (fieldname.equals("taskName"+adjustmentCaseIndex)){

                            System.out.println("param: " +fieldvalue);
                            analyticTaskName = fieldvalue;
                                                        
                        }
                        
                        
                        if (fieldname.equals("noOfParameters_analyticTask"+adjustmentCaseIndex)){

                            System.out.println("param: " +fieldvalue);
                            noOfAnalyticsTaskParameters = Integer.parseInt(fieldvalue);
                            
                                                        
                        }
                        
                    
                    }
                }
       
        List<Parameter> listOfParameters = new ArrayList<Parameter>();
        for (int i=1;i<=noOfAnalyticsTaskParameters;i++){
            Parameter parameter = getParameterAnalytic(adjustmentCaseIndex, i, multiparts);
            if (parameter!=null){
                listOfParameters.add(parameter);
            }
        }
        
        AnalyticTask analyticTask = new AnalyticTask(analyticTaskName, listOfParameters);
        
        return analyticTask;
        
    }
    
    private List<Parameter> getListOfAdjustmentCaseParameters(int adjustmentCaseIndex, List<FileItem> multiparts){
        int noOfAdjustmentCaseParameters = 0;
        
        
        for (FileItem item : multiparts) {
                    if (!item.isFormField()) {
                       

                    } else {
                        
                        String fieldname = item.getFieldName();
                        String fieldvalue = item.getString();
                      
                        
                        if (fieldname.equals("noOfParameters_adjustmentCase"+adjustmentCaseIndex)){

                            System.out.println("param: " +fieldvalue);
                            noOfAdjustmentCaseParameters = Integer.parseInt(fieldvalue);
                            
                                                        
                        }
                    }
                }
        
        
   
        List<Parameter> listOfParameters = new ArrayList<Parameter>();
        for (int i=1;i<=noOfAdjustmentCaseParameters;i++){
            Parameter parameter = getParameterAdjustmentCase(adjustmentCaseIndex, i, multiparts);
            if (parameter!=null){
                listOfParameters.add(parameter);
            }
        }
        
        
        return listOfParameters;
        
    }
    private Parameter getParameterAdjustmentCase(int adjustmentCaseIndex, int parameterIndex, List<FileItem> multiparts){
        
        
        String paramName = "";
        String paramType = "";
        String paramValue = "";
          for (FileItem item : multiparts) {
                    if (!item.isFormField()) {
                       

                    } else {
                        
                        String fieldname = item.getFieldName();
                        String fieldvalue = item.getString();
                                           
                        if (fieldname.equals("ParameterName_case"+adjustmentCaseIndex+"_"+parameterIndex)){

                            System.out.println("param: " +fieldvalue);
                            paramName = fieldvalue;
                                                        
                        }
                        
                        if (fieldname.equals("Type_case"+adjustmentCaseIndex+"_"+parameterIndex)){
                            System.out.println("param: " +fieldvalue);
                            paramType = fieldvalue;
                        }
                        
                        if (fieldname.equals("ParameterValue_case"+adjustmentCaseIndex+"_"+parameterIndex)){
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
    
    private Parameter getParameterAnalytic(int adjustmentCaseIndex, int parameterIndex, List<FileItem> multiparts){
        
        
        String paramName = "";
        String paramType = "";
        String paramValue = "";
          for (FileItem item : multiparts) {
                    if (!item.isFormField()) {
                       

                    } else {
                        
                        String fieldname = item.getFieldName();
                        String fieldvalue = item.getString();
                                           
                        if (fieldname.equals("ParameterName_analytic"+adjustmentCaseIndex+"_"+parameterIndex)){

                            System.out.println("param: " +fieldvalue);
                            paramName = fieldvalue;
                                                        
                        }
                        
                        if (fieldname.equals("Type_analytic"+adjustmentCaseIndex+"_"+parameterIndex)){
                            System.out.println("param: " +fieldvalue);
                            paramType = fieldvalue;
                        }
                        
                        if (fieldname.equals("ParameterValue_analytic"+adjustmentCaseIndex+"_"+parameterIndex)){
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
