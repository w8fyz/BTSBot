package fr.fyz.bts.spotify.auth;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.time.Instant;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import fr.fyz.bts.spotify.CheckTask;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class WebRequestHandler extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		resp.setStatus(HttpServletResponse.SC_OK);
		PrintWriter out = resp.getWriter();
		try {
			System.out.println("ENCODING : "+req.getCharacterEncoding()+"\n");
			String authCode = req.getParameter("code");
			System.out.println("[AUTHCODE]: \n\n"+authCode);
			AuthorizationCode.refreshWith(authCode);
			
			out.print("Successfully logged !");
			
			new Timer().schedule(new CheckTask(), Date.from(Instant.now()), TimeUnit.SECONDS.toMillis(50));
		} catch (Exception e) {
			e.printStackTrace();
			out.print("Erreur : "+e.getMessage());
		}

	}



}
