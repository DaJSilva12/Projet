package servlet;

import java.io.IOException;
import static java.lang.System.console;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;
import model.DAO;
import model.DataSourceFactory;
/**
 * Le contrôleur de l'application
 * @author rbastide
 */
@WebServlet(name = "AuthentificationUtilisateur", urlPatterns = {"/AuthentificationUtilisateur"})
public class AuthentificationUtilisateur extends HttpServlet {

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */

         protected void processRequest(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		// Quelle action a servi à appeler la servlet ? (Ajouter, Supprimer ou aucune = afficher)
                
		// Quelle action a appelé cette servlet ?
		String action = request.getParameter("action");
		if (null != action) {
			switch (action) {
				case "login":
					checkLogin(request);
					break;
				case "logout":
					doLogout(request);
					break;
			}
		}

		// Est-ce que l'utilisateur est connecté ?
		// On cherche l'attribut userName dans la session
		String login = getInitParameter("login");
		String userName = findUserInSession(request);
		String jspView;
		if (null == userName) { // L'utilisateur n'est pas connecté
			// On choisit la page de login
			jspView = "VerifUtilisateur.jsp";

		} else {
                        if(userName.equals(login)){
                            jspView = "AjaxGoogleChart.html";

                        }
                        else{
                            jspView = "CommandeClient";

                        }
                         // L'utilisateur est connecté
			// On choisit la page d'affichage
		}
		// On va vers la page choisie
		request.getRequestDispatcher(jspView).forward(request, response);

	
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
	private void checkLogin(HttpServletRequest request) {
		// Les paramètres transmis dans la requête
		String loginParam = request.getParameter("loginParam");
		String passwordParam = request.getParameter("passwordParam");

		// Le login/password défini dans web.xml
		String login = getInitParameter("login");
		String password = getInitParameter("password");
		String userName = getInitParameter("userName");
                
                DAO dao = new DAO(DataSourceFactory.getDataSource());
                
               
                
             try {
                 if (dao.idCustomer(Integer.parseInt(passwordParam),loginParam)) {
                     // On a trouvé la combinaison login / password
                     // On stocke l'information dans la session
                     HttpSession session = request.getSession(true); // démarre la session
                     session.setAttribute("userName", passwordParam);
                 }
                 else{
                 if(passwordParam.equals(password) && loginParam.equals(login)) {
                     HttpSession session = request.getSession(true); // démarre la session
                     session.setAttribute("userName", login);
                 }
                 else { // On positionne un message d'erreur pour l'afficher dans la JSP
                     request.setAttribute("errorMessage", "Login/Password incorrect");
                 }
                     
                 }
             } catch (SQLException ex) {
                 Logger.getLogger(AuthentificationUtilisateur.class.getName()).log(Level.SEVERE, null, ex);
             }
	}

	private void doLogout(HttpServletRequest request) {
		// On termine la session
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
	}

	private String findUserInSession(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		return (session == null) ? null : (String) session.getAttribute("userName");
	}
}
