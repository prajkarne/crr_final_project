package com.growfin.ticketingSystem.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	SendGrid sendGridClient;

	/**
	 * Send a text-based email using SendGrid.
	 *
	 * @param from    The sender's email address.
	 * @param to      The recipient's email address.
	 * @param subject The subject of the email.
	 * @param body    The body content of the email.
	 * @return The SendGrid API response for the email sending request.
	 */
	@Override
	public void sendTextEmail(String from, String to, String subject, String body) {
		// Create a content object with plain text body
		Content content = new Content("text/plain", body);

		// Send the email using SendGrid
		sendEmail(from, to, subject, content);
	}

	/**
	 * Send an email using SendGrid.
	 *
	 * @param from    The sender's email address.
	 * @param to      The recipient's email address.
	 * @param subject The subject of the email.
	 * @param content The content of the email.
	 * @return The SendGrid API response for the email sending request.
	 */
	private Response sendEmail(String from, String to, String subject, Content content) {
		// Create a Mail object with sender, subject, recipient, and content
		Mail mail = new Mail(new Email(from), subject, new Email(to), content);

		// Set the reply-to address
		mail.setReplyTo(new Email("yogesh@sinecycle.com"));

		// Create a SendGrid request and send the email
		Request request = new Request();
		Response response = null;
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			response = this.sendGridClient.api(request);
		} catch (IOException ex) {
			// Handle IOException, if any
		}

		return response;
	}
}
