/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.DAO;
import model.DataSourceFactory;

/**
 *
 * @author nbrugie
 */
@WebServlet(name = "CommandeClient", urlPatterns = {"/CommandeClient"})
public class CommandeClient extends HttpServlet {

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
        String action = request.getParameter("action");
        action = (action == null) ? "" : action;

        try {
            
            DAO dao = new DAO(DataSourceFactory.getDataSource());
            HttpSession session = request.getSession(false);
            String idString = (String) session.getAttribute("userName");
            int id = Integer.parseInt(idString);
            List Product = dao.Product();
            List CommandeClient = dao.prixCommande(id);
            request.setAttribute("ListeProduit", Product);
            request.setAttribute("commandeClient", CommandeClient);
            
            String idProduct = request.getParameter("id");
            String Description = request.getParameter("description");
            String Champ = request.getParameter("quantite");
            String modifQuantite =request.getParameter("quantiteModif");
            String orderNum =request.getParameter("orderNum");

            switch (action) {
                case "DELETE": // Requête d'ajout (vient du formulaire de saisie)
                        dao.deleteCommande(Integer.parseInt(orderNum));
                        List CommandeClientDel = dao.prixCommande(id);
                        request.setAttribute("commandeClient", CommandeClientDel);
                break;
                case "AJOUTER": // Requête de suppression (vient du lien hypertexte)
                    Date date = new Date(); 
                    SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
                    String dat = format.format(date);
                    long max = dao.MaxOrdernum()+1;
                    dao.addCommande(max, id, Integer.parseInt(idProduct), dat, Integer.parseInt(Champ),Description);
                    List CommandeClientadd = dao.prixCommande(id);
                    request.setAttribute("commandeClient", CommandeClientadd);
                break;
                case "MODIFIER":
                     dao.modifierCommande(Integer.parseInt(orderNum), Integer.parseInt(modifQuantite));
                     List CommandeClientMod = dao.prixCommande(id);
                     request.setAttribute("commandeClient", CommandeClientMod);
                break;


            }
            
        }catch(Exception e){
            e.printStackTrace();
        }finally {

    }
    request.getRequestDispatcher("Commande.jsp").forward(request, response);
   
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
        processRequest(request, response);
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
