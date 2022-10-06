package fr.fyz.bts.verification;



import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import fr.fyz.bts.Main;

public class EmailManager {

	public static void sendEmail(String to, String token) throws UnsupportedEncodingException {

		String host = "smtp-relay.sendinblue.com";
		final String user = "bts.verification@fyz.center";

		String verificationLink = VerificationManager.getLink(token);
		
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "587");
	
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("hello@fyz.center", Main.getCrendentials().emailPassword);
			}
		});
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user, "Discord BTS SIO"));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject("Vérification de l'adresse mail");
			message.setDataHandler(new DataHandler(
					new HTMLDataSource(getSampleMail(verificationLink))));
			Transport.send(message);

			
			
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}
	
	private static String getSampleMail(String link) {
		return "<!DOCTYPE html>\r\n"
				+ "<html>\r\n"
				+ "<head>\r\n"
				+ "\r\n"
				+ "  <meta charset=\"utf-8\">\r\n"
				+ "  <meta http-equiv=\"x-ua-compatible\" content=\"ie=edge\">\r\n"
				+ "  <title>Confirmation d'email</title>\r\n"
				+ "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n"
				+ "  <style type=\"text/css\">\r\n"
				+ "  @media screen {\r\n"
				+ "    @font-face {\r\n"
				+ "      font-family: 'Source Sans Pro';\r\n"
				+ "      font-style: normal;\r\n"
				+ "      font-weight: 400;\r\n"
				+ "      src: local('Source Sans Pro Regular'), local('SourceSansPro-Regular'), url(https://fonts.gstatic.com/s/sourcesanspro/v10/ODelI1aHBYDBqgeIAH2zlBM0YzuT7MdOe03otPbuUS0.woff) format('woff');\r\n"
				+ "    }\r\n"
				+ "\r\n"
				+ "    @font-face {\r\n"
				+ "      font-family: 'Source Sans Pro';\r\n"
				+ "      font-style: normal;\r\n"
				+ "      font-weight: 700;\r\n"
				+ "      src: local('Source Sans Pro Bold'), local('SourceSansPro-Bold'), url(https://fonts.gstatic.com/s/sourcesanspro/v10/toadOcfmlt9b38dHJxOBGFkQc6VGVFSmCnC_l7QZG60.woff) format('woff');\r\n"
				+ "    }\r\n"
				+ "  }\r\n"
				+ "\r\n"
				+ "  body,\r\n"
				+ "  table,\r\n"
				+ "  td,\r\n"
				+ "  a {\r\n"
				+ "    -ms-text-size-adjust: 100%; /* 1 */\r\n"
				+ "    -webkit-text-size-adjust: 100%; /* 2 */\r\n"
				+ "  }\r\n"
				+ "  table,\r\n"
				+ "  td {\r\n"
				+ "    mso-table-rspace: 0pt;\r\n"
				+ "    mso-table-lspace: 0pt;\r\n"
				+ "  }\r\n"
				+ "\r\n"
				+ "  a[x-apple-data-detectors] {\r\n"
				+ "    font-family: inherit !important;\r\n"
				+ "    font-size: inherit !important;\r\n"
				+ "    font-weight: inherit !important;\r\n"
				+ "    line-height: inherit !important;\r\n"
				+ "    color: inherit !important;\r\n"
				+ "    text-decoration: none !important;\r\n"
				+ "  }\r\n"
				+ "\r\n"
				+ "\r\n"
				+ "  div[style*=\"margin: 16px 0;\"] {\r\n"
				+ "    margin: 0 !important;\r\n"
				+ "  }\r\n"
				+ "\r\n"
				+ "  body {\r\n"
				+ "    width: 100% !important;\r\n"
				+ "    height: 100% !important;\r\n"
				+ "    padding: 0 !important;\r\n"
				+ "    margin: 0 !important;\r\n"
				+ "  }\r\n"
				+ "\r\n"
				+ "  table {\r\n"
				+ "    border-collapse: collapse !important;\r\n"
				+ "  }\r\n"
				+ "\r\n"
				+ "  a {\r\n"
				+ "    color: #1a82e2;\r\n"
				+ "  }\r\n"
				+ "\r\n"
				+ "  img {\r\n"
				+ "    height: auto;\r\n"
				+ "    line-height: 100%;\r\n"
				+ "    text-decoration: none;\r\n"
				+ "    border: 0;\r\n"
				+ "    outline: none;\r\n"
				+ "  }\r\n"
				+ "  </style>\r\n"
				+ "\r\n"
				+ "</head>\r\n"
				+ "<body style=\"background-color: #e9ecef;\">\r\n"
				+ "\r\n"
				+ "  <!-- start preheader -->\r\n"
				+ "  <div class=\"preheader\" style=\"display: none; max-width: 0; max-height: 0; overflow: hidden; font-size: 1px; line-height: 1px; color: #fff; opacity: 0;\">\r\n"
				+ "  Wooooosh ! Pour confirmer ta demande de vérification sur le Discord des BTS SIO de Efrei, suis les étapes simple de ce mail !\r\n"
				+ " </div>\r\n"
				+ "  <!-- end preheader -->\r\n"
				+ "\r\n"
				+ "  <!-- start body -->\r\n"
				+ "  <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\r\n"
				+ "\r\n"
				+ "    <!-- start hero -->\r\n"
				+ "    <tr>\r\n"
				+ "      <td align=\"center\" bgcolor=\"#e9ecef\">\r\n"
				+ "        <!--[if (gte mso 9)|(IE)]>\r\n"
				+ "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\r\n"
				+ "        <tr>\r\n"
				+ "        <td align=\"center\" valign=\"top\" width=\"600\">\r\n"
				+ "        <![endif]-->\r\n"
				+ "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\r\n"
				+ "          <tr>\r\n"
				+ "            <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 36px 24px 0; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; border-top: 3px solid #d4dadf;\">\r\n"
				+ "              <h1 style=\"margin: 0; font-size: 32px; font-weight: 700; letter-spacing: -1px; line-height: 48px;\">Vérification d'email</h1>\r\n"
				+ "            </td>\r\n"
				+ "          </tr>\r\n"
				+ "        </table>\r\n"
				+ "        <!--[if (gte mso 9)|(IE)]>\r\n"
				+ "        </td>\r\n"
				+ "        </tr>\r\n"
				+ "        </table>\r\n"
				+ "        <![endif]-->\r\n"
				+ "      </td>\r\n"
				+ "    </tr>\r\n"
				+ "    <!-- end hero -->\r\n"
				+ "\r\n"
				+ "    <!-- start copy block -->\r\n"
				+ "    <tr>\r\n"
				+ "      <td align=\"center\" bgcolor=\"#e9ecef\">\r\n"
				+ "        <!--[if (gte mso 9)|(IE)]>\r\n"
				+ "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\r\n"
				+ "        <tr>\r\n"
				+ "        <td align=\"center\" valign=\"top\" width=\"600\">\r\n"
				+ "        <![endif]-->\r\n"
				+ "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\r\n"
				+ "\r\n"
				+ "          <!-- start copy -->\r\n"
				+ "          <tr>\r\n"
				+ "            <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px;\">\r\n"
				+ "              <p style=\"margin: 0;\">Plus qu'une étape pour confirmer ton adresse mail, il ne te reste plus qu'à cliquer sur le bouton si dessous.</p>\r\n"
				+ "            </td>\r\n"
				+ "          </tr>\r\n"
				+ "          <!-- end copy -->\r\n"
				+ "\r\n"
				+ "          <!-- start button -->\r\n"
				+ "          <tr>\r\n"
				+ "            <td align=\"left\" bgcolor=\"#ffffff\">\r\n"
				+ "              <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\r\n"
				+ "                <tr>\r\n"
				+ "                  <td align=\"center\" bgcolor=\"#ffffff\" style=\"padding: 12px;\">\r\n"
				+ "                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n"
				+ "                      <tr>\r\n"
				+ "                        <td align=\"center\" bgcolor=\"#1a82e2\" style=\"border-radius: 6px;\">\r\n"
				+ "                          <a href=\""+link+"\" target=\"_blank\" style=\"display: inline-block; padding: 16px 36px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; color: #ffffff; text-decoration: none; border-radius: 6px;\">Vérification</a>\r\n"
				+ "                        </td>\r\n"
				+ "                      </tr>\r\n"
				+ "                    </table>\r\n"
				+ "                  </td>\r\n"
				+ "                </tr>\r\n"
				+ "              </table>\r\n"
				+ "            </td>\r\n"
				+ "          </tr>\r\n"
				+ "          <!-- end button -->\r\n"
				+ "\r\n"
				+ "          <!-- start copy -->\r\n"
				+ "          <tr>\r\n"
				+ "            <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px;\">\r\n"
				+ "              <p style=\"margin: 0;\">Si ça n'a pas marché, tu peux copier coller le lien suivant dans ton navigateur :</p>\r\n"
				+ "              <p style=\"margin: 0;\"><a href=\""+link+"\" target=\"_blank\">"+link+"</a></p>\r\n"
				+ "            </td>\r\n"
				+ "          </tr>\r\n"
				+ "          <!-- end copy -->\r\n"
				+ "\r\n"
				+ "          <!-- start copy -->\r\n"
				+ "          <tr>\r\n"
				+ "            <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px; border-bottom: 3px solid #d4dadf\">\r\n"
				+ "              <p style=\"margin: 0;\">Bienvenue sur le discord,<br> Les BTS SIO de Efrei</p>\r\n"
				+ "            </td>\r\n"
				+ "          </tr>\r\n"
				+ "          <!-- end copy -->\r\n"
				+ "\r\n"
				+ "        </table>\r\n"
				+ "        <!--[if (gte mso 9)|(IE)]>\r\n"
				+ "        </td>\r\n"
				+ "        </tr>\r\n"
				+ "        </table>\r\n"
				+ "        <![endif]-->\r\n"
				+ "      </td>\r\n"
				+ "    </tr>\r\n"
				+ "    <!-- end copy block -->\r\n"
				+ "\r\n"
				+ "    <!-- start footer -->\r\n"
				+ "    <tr>\r\n"
				+ "      <td align=\"center\" bgcolor=\"#e9ecef\" style=\"padding: 24px;\">\r\n"
				+ "        <!--[if (gte mso 9)|(IE)]>\r\n"
				+ "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\r\n"
				+ "        <tr>\r\n"
				+ "        <td align=\"center\" valign=\"top\" width=\"600\">\r\n"
				+ "        <![endif]-->\r\n"
				+ "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\r\n"
				+ "\r\n"
				+ "          <!-- start permission -->\r\n"
				+ "          <tr>\r\n"
				+ "            <td align=\"center\" bgcolor=\"#e9ecef\" style=\"padding: 12px 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 20px; color: #666;\">\r\n"
				+ "              <p style=\"margin: 0;\">Tu reçois ce mail car tu as demandé'e une vérification sur le discord des BTS SIO de Efrei, si c'est une erreur, n'hésite pas à contacter Fyz#0001 sur Discord pour le signaler ou simplement à ignorer ce mail.</p>\r\n"
				+ "            </td>\r\n"
				+ "          </tr>\r\n"
				+ "          <!-- end permission -->\r\n"
				+ "\r\n"
				+ "\r\n"
				+ "        </table>\r\n"
				+ "        <!--[if (gte mso 9)|(IE)]>\r\n"
				+ "        </td>\r\n"
				+ "        </tr>\r\n"
				+ "        </table>\r\n"
				+ "        <![endif]-->\r\n"
				+ "      </td>\r\n"
				+ "    </tr>\r\n"
				+ "    <!-- end footer -->\r\n"
				+ "\r\n"
				+ "  </table>\r\n"
				+ "  <!-- end body -->\r\n"
				+ "\r\n"
				+ "</body>\r\n"
				+ "</html>";
	}

}

class HTMLDataSource implements DataSource {

	private String html;

	public HTMLDataSource(String htmlString) {
		html = htmlString;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		if (html == null)
			throw new IOException("html message is null!");
		return new ByteArrayInputStream(html.getBytes());
	}

	@Override
	public OutputStream getOutputStream() throws IOException {
		throw new IOException("This DataHandler cannot write HTML");
	}

	@Override
	public String getContentType() {
		return "text/html";
	}

	@Override
	public String getName() {
		return "HTMLDataSource";
	}
}