/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Simple servlet that serves images.
 * @author xbatko
 */
public class ImagesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private File directory;

    @Override
    public void init() throws ServletException {
        String param = getInitParameter("directory");
        if (param == null || param.isEmpty())
            throw new ServletException("Parameter 'directory' required");
        directory = new File(param);
        if (!directory.exists() || !directory.canRead())
            throw new ServletException("Cannot read directory: " + directory.getAbsolutePath());
    }

   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        File file = new File(directory, request.getPathInfo());
        if (!file.canRead()) {
            response.sendError(404, "Cannot read file: " + file.getAbsolutePath());
            return;
        }

        response.setContentType("image/jpeg");
        OutputStream out = response.getOutputStream();
        try {
            FileInputStream in = new FileInputStream(file);
            try {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) > 0)
                    out.write(buffer, 0, bytesRead);
            } finally {
                in.close();
            }
        } finally { 
            out.close();
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
