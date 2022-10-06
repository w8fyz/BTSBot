package fr.fyz.bts.verification;

import java.io.IOException;
import java.io.PrintWriter;

import fr.fyz.bts.Main;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class VerificationMailhandler extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		resp.setStatus(HttpServletResponse.SC_OK);
		PrintWriter out = resp.getWriter();
		try {
			String authCode = req.getParameter("code");
			String user = VerificationManager.verifyToken(authCode);
			if(user != null) {
				DiscordVerification.verify(user);
				out.print("<h1>Parfait ! Tu peux maintenant fermer cette page</h1>");
				Main.log(user + " is now verified");
				return;
			}
			out.print("<h1 style=\"color:red;\">Le code utilisé est invalide</h1>");
		} catch (Exception e) {
			Main.log("Le bot a rencontré une erreur : "+e.getMessage());
		}
	}

}
