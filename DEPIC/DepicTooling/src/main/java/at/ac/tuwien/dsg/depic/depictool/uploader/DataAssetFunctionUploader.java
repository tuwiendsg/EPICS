/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.depictool.uploader;

import at.ac.tuwien.dsg.depic.common.entity.dataanalyticsfunction.DataAnalyticsFunction;
import at.ac.tuwien.dsg.depic.common.entity.dataanalyticsfunction.EDaaSType;
import at.ac.tuwien.dsg.depic.common.entity.process.MetricProcess;
import at.ac.tuwien.dsg.depic.common.entity.qor.QoRModel;
import at.ac.tuwien.dsg.depic.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.depic.depictool.elstore.DataAssetStore;
import at.ac.tuwien.dsg.depic.depictool.elstore.ElasticityProcessStore;
import at.ac.tuwien.dsg.depic.depictool.generator.Generator;
import at.ac.tuwien.dsg.depic.depictool.parser.ElasticityProcessesParser;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Jun
 */
@WebServlet(name = "DataAssetFunctionUploader", urlPatterns = {"/DataAssetFunctionUploader"})
public class DataAssetFunctionUploader extends HttpServlet {

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
            out.println("<title>Servlet DataAssetFunctionUploader</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DataAssetFunctionUploader at " + request.getContextPath() + "</h1>");
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
        //process only if its multipart content
        
        String eDaaSName = "";
        String dataAssetID = "";
        String dawXML = "";
        
        
        if (ServletFileUpload.isMultipartContent(request)) {
            try {
                List<FileItem> multiparts = new ServletFileUpload(
                        new DiskFileItemFactory()).parseRequest(request);

                for (FileItem item : multiparts) {
                    if (!item.isFormField()) {
                        String name = new File(item.getName()).getName();
                       // item.write( new File(UPLOAD_DIRECTORY + File.separator + name));

                                                InputStream filecontent = item.getInputStream();

                        StringWriter writer = new StringWriter();
                        IOUtils.copy(filecontent, writer, "UTF-8");
                        String str = writer.toString();

                       
                        String log = "item: " + item.getFieldName() + " - file name: " + name + " - content: " + str;

                        if (item.getFieldName().equals("dataAssetFunction")) {
 
                            dawXML = str;
                        }
                        
                      
                        
                        Logger.getLogger(DataAssetFunctionUploader.class.getName()).log(Level.INFO, log);

                    } else {
                        
                        String fieldname = item.getFieldName();
                        String fieldvalue = item.getString();
                        if (fieldname.equals("edaasName")){
                            eDaaSName = fieldvalue;
                        }
                        
                        if (fieldname.equals("dataAssetID")){
                            dataAssetID = fieldvalue;
                        }
                        
                        
                        String log = "field: " + fieldname + " - value: " + fieldvalue;
                        Logger.getLogger(DataAssetFunctionUploader.class.getName()).log(Level.INFO, log);
                        
                    }
                }

                //File uploaded successfully
                request.setAttribute("message", "File Uploaded Successfully");
            } catch (Exception ex) {
                request.setAttribute("message", "File Upload Failed due to " + ex);
            }

        } else {
            request.setAttribute("message",
                    "Sorry this Servlet only handles file upload request");
        }
        
        DataAnalyticsFunction daf = new DataAnalyticsFunction(dataAssetID, "", dawXML);
        
        String dafXML="";
        try {
            dafXML = JAXBUtils.marshal(daf, DataAnalyticsFunction.class);
        } catch (JAXBException ex) {
            Logger.getLogger(DataAssetFunctionUploader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ElasticityProcessStore elasticityProcessStore = new ElasticityProcessStore();
        elasticityProcessStore.storeDataAssetFunction(eDaaSName, dataAssetID, dafXML, "0");
        
        DataAssetStore das = new DataAssetStore();
        String noOfPartition = das.requestToGetDataAsset(dataAssetID);
        
        elasticityProcessStore.updateDataAssetFunction(eDaaSName, dataAssetID, dafXML, noOfPartition);
        
        
        
       
        request.getRequestDispatcher("/daf_manager.jsp?edaasname="+eDaaSName).forward(request, response);
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
