/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depictool.uploader;

import at.ac.tuwien.dsg.common.entity.process.MetricProcess;
import at.ac.tuwien.dsg.common.entity.qor.QoRModel;
import at.ac.tuwien.dsg.depictool.elstore.ElasticityProcessStore;
import at.ac.tuwien.dsg.depictool.generator.Generator;
import at.ac.tuwien.dsg.depictool.parser.ElasticityProcessesParser;
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
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Jun
 */
@WebServlet(name = "InputSpecificationUploader", urlPatterns = {"/InputSpecificationUploader"})
public class InputSpecificationUploader extends HttpServlet {

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
            out.println("<title>Servlet InputSpecificationUploader</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet InputSpecificationUploader at " + request.getContextPath() + "</h1>");
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
        String qor = "";
        String elasticityProcesses = "";
        
        
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

                        if (item.getFieldName().equals("qor")) {
 
                            qor = str;
                        }
                        
                        if (item.getFieldName().equals("ep")) {
                            elasticityProcesses = str;
                        }
                        
                        
                        Logger.getLogger(InputSpecificationUploader.class.getName()).log(Level.INFO, log);

                    } else {
                        
                        String fieldname = item.getFieldName();
                        String fieldvalue = item.getString();
                        if (fieldname.equals("edaas")){
                            eDaaSName = fieldvalue;
                        }
                        
                        
                        String log = "field: " + fieldname + " - value: " + fieldvalue;
                        Logger.getLogger(InputSpecificationUploader.class.getName()).log(Level.INFO, log);
                        
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
        
        
        String log = "edaas: " + eDaaSName + "\n qor: " + qor + "\n ep: " + elasticityProcesses;
        Logger.getLogger(InputSpecificationUploader.class.getName()).log(Level.INFO, log);
        
        ElasticityProcessStore elasticityProcessStore = new ElasticityProcessStore();
        elasticityProcessStore.storeQoRAndElasticityProcesses(eDaaSName, qor, elasticityProcesses);
  
        
        ElasticityProcessesParser elasticityProcessesParser = new ElasticityProcessesParser();
        
        QoRModel qorModel = elasticityProcessesParser.parseQoRModel(qor);
        MetricProcess metricProcess = elasticityProcessesParser.parseElasticityProcesses(elasticityProcesses);
        
        Generator generator = new Generator(eDaaSName, qorModel, metricProcess);
        generator.startGenerator();
       

        request.getRequestDispatcher("/index.jsp").forward(request, response);
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
