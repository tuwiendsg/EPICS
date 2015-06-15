/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.depictool.uploader;


import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.AnalyticTask;

import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.MetricCondition;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.Parameter;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.ResourceControlAction;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.ResourceControlCase;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.ResourceControlStrategy;
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
@WebServlet(name = "AddingResourceControlAction", urlPatterns = {"/AddingResourceControlAction"})
public class AddingResourceControlAction extends HttpServlet {

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
            out.println("<title>Servlet AddingResourceControlAction</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddingResourceControlAction at " + request.getContextPath() + "</h1>");
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

        
                int noOfResourceControlCases = 0;
                
                ResourceControlAction resourceControlAction = new ResourceControlAction();
         
                for (FileItem item : multiparts) {
                    if (!item.isFormField()) {
                       

                    } else {
                        
                        String fieldname = item.getFieldName();
                        String fieldvalue = item.getString();
                        
                        if (fieldname.equals("ResourceControlActionName")){
                            System.out.println(fieldvalue);
                            resourceControlAction.setActionName(fieldvalue);
                        }
 
                        if (fieldname.equals("AssociatedQoRMetric")){  
                            System.out.println(fieldvalue);
                            resourceControlAction.setAssociatedQoRMetric(fieldvalue);
                        }

                        if (fieldname.equals("noOfResourceControlCases")){  
                            System.out.println("noOfResoureControlCases: " + fieldvalue);
                            noOfResourceControlCases = Integer.parseInt(fieldvalue);
                        }
                        
                      
                        
                    }
                }
                
                
              
                List<ResourceControlCase> listOfResourceControlCases = new ArrayList<ResourceControlCase>();
  
                for (int i=1; i<=noOfResourceControlCases;i++){
         
                    MetricCondition estimatedResult = getEstimatedResult(i, multiparts);
                    AnalyticTask analyticTask = getAnalyticTask(i, multiparts);
                    
                    List<ResourceControlStrategy> listOfResourceControlStrategies = getListOfResourceControlStrategies(i, multiparts);
                    ResourceControlCase resourceControlCase = new ResourceControlCase(estimatedResult, analyticTask, listOfResourceControlStrategies);
                    listOfResourceControlCases.add(resourceControlCase);
              
                }
                
                resourceControlAction.setListOfResourceControlCases(listOfResourceControlCases);
                
                
               
                PrimitiveActionMetadataManager pamm = new PrimitiveActionMetadataManager();
                pamm.storeResourceControlAction(resourceControlAction);
                
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
    
    private List<ResourceControlStrategy> getListOfResourceControlStrategies(int adjustmentCaseIndex, List<FileItem> multiparts){
        int noOfResourceControlStrategies = 0;
        
        
        for (FileItem item : multiparts) {
                    if (!item.isFormField()) {
                       

                    } else {
                        
                        String fieldname = item.getFieldName();
                        String fieldvalue = item.getString();
                      
                        
                        if (fieldname.equals("noOfResourceControlStrategies"+adjustmentCaseIndex)){

                            System.out.println("param: " +fieldvalue);
                            noOfResourceControlStrategies = Integer.parseInt(fieldvalue);
                            
                                                        
                        }
                    }
                }
        
   
        List<ResourceControlStrategy> listOfResourceControlStrategies = new ArrayList<ResourceControlStrategy>();
        for (int i=1;i<=noOfResourceControlStrategies;i++){
            ResourceControlStrategy resourceControlStrategy = getResourceControlStrategy(String.valueOf(i), adjustmentCaseIndex, multiparts);
            if (resourceControlStrategy!=null){
                listOfResourceControlStrategies.add(resourceControlStrategy);
            }
        }
        
        
        return listOfResourceControlStrategies;
    }
    
    
    private ResourceControlStrategy getResourceControlStrategy(String resourceControlStrategyIndex,int adjustmentCaseIndex, List<FileItem> multiparts){
         
        String controlMetric = "";
        String primitiveAction = "";
        double es_from_in = 0;
        double es_to_in = 0;
        double es_from_out = 0;
        double es_to_out = 0;
        
        
        
          for (FileItem item : multiparts) {
                    if (!item.isFormField()) {
                       

                    } else {
                        
                        String fieldname = item.getFieldName();
                        String fieldvalue = item.getString();
                                           
                        if (fieldname.equals("controlMetric"+adjustmentCaseIndex+"_"+resourceControlStrategyIndex)){

                            System.out.println("param: " +fieldvalue);
                            controlMetric = fieldvalue;
                                                        
                        }
                        
                        if (fieldname.equals("primitiveAction"+adjustmentCaseIndex+"_"+resourceControlStrategyIndex)){
                            System.out.println("param: " +fieldvalue);
                            primitiveAction = fieldvalue;
                        }
                        
                        if (fieldname.equals("es_from_in"+adjustmentCaseIndex+"_"+resourceControlStrategyIndex)){
                            System.out.println("param: " +fieldvalue);
                            es_from_in = Double.parseDouble(fieldvalue);
                        }
                        
                        if (fieldname.equals("es_to_in"+adjustmentCaseIndex+"_"+resourceControlStrategyIndex)){
                            System.out.println("param: " +fieldvalue);
                            es_to_in = Double.parseDouble(fieldvalue);
                        }
                        
                        if (fieldname.equals("es_from_out"+adjustmentCaseIndex+"_"+resourceControlStrategyIndex)){
                            System.out.println("param: " +fieldvalue);
                            es_from_out = Double.parseDouble(fieldvalue);
                        }
                        
                        if (fieldname.equals("es_to_out"+adjustmentCaseIndex+"_"+resourceControlStrategyIndex)){
                            System.out.println("param: " +fieldvalue);
                            es_to_out = Double.parseDouble(fieldvalue);
                        }
                        
                       
                    }
                }
        ResourceControlStrategy resourceControlStrategy = null;
          if (!controlMetric.equals("")){
              resourceControlStrategy = new ResourceControlStrategy(new MetricCondition("", "", es_from_in, es_to_in), new MetricCondition("", "", es_from_out, es_to_out), controlMetric, primitiveAction);
          }
          
        return resourceControlStrategy;
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
            Parameter parameter = getParameter(adjustmentCaseIndex, i, multiparts);
            if (parameter!=null){
                listOfParameters.add(parameter);
            }
        }
        
        AnalyticTask analyticTask = new AnalyticTask(analyticTaskName, listOfParameters);
        
        return analyticTask;
        
    }
 private Parameter getParameter(int resourceControlCaseIndex, int parameterIndex, List<FileItem> multiparts){
        
        
        String paramName = "";
        String paramType = "";
        String paramValue = "";
          for (FileItem item : multiparts) {
                    if (!item.isFormField()) {
                       

                    } else {
                        
                        String fieldname = item.getFieldName();
                        String fieldvalue = item.getString();
                                           
                        if (fieldname.equals("ParameterName"+resourceControlCaseIndex+"_"+parameterIndex)){

                            System.out.println("param: " +fieldvalue);
                            paramName = fieldvalue;
                                                        
                        }
                        
                        if (fieldname.equals("Type"+resourceControlCaseIndex+"_"+parameterIndex)){
                            System.out.println("param: " +fieldvalue);
                            paramType = fieldvalue;
                        }
                        
                        if (fieldname.equals("ParameterValue"+resourceControlCaseIndex+"_"+parameterIndex)){
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
    
    private MetricCondition getEstimatedResult(int adjustmentCaseIndex, List<FileItem> multiparts){
        
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
