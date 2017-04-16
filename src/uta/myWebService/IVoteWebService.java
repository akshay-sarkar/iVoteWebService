package uta.myWebService;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonObject;

import uta.FCM.FCMHelper;

@Path("/testWS")
public class IVoteWebService {
	Connection conn = null;
	ResultSet rs = null;
    Statement stmt = null;
    java.sql.PreparedStatement prepStmt = null;
    String data="";
    String lineSeperator="#&#";
    String columentSeperator = "@&@";
    
	/* DB Connection */
	public Connection dbConnection(){
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ivote", "root", "admin");
			return conn;
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;		
	}
	/* FCM Service - https://github.com/MOSDEV82/fcmhelper */
	public void sendNotification(String Message){
		
		String someValue = "Just a demo, really...";
		new Thread(new Runnable() {
		    private String myParam;
		    public Runnable init(String myParam) {
		        this.myParam = myParam;
		        return this;
		    }
		    @Override
		    public void run() {
		        System.out.println("This is called from another thread.");
		        System.out.println(this.myParam);
				JsonObject notificationObject = new JsonObject();
				notificationObject.addProperty("body", "iVote");
				notificationObject.addProperty("title", myParam);
				FCMHelper fcm = FCMHelper.getInstance();
				try {
					String str = fcm.sendNotification(FCMHelper.TYPE_TO, "<DEVICE_TOKEN HERE>", notificationObject);
					System.out.println(str);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		}.init(someValue)).start();
	}
	/* Send Email  - ForgotPasssword*/
	public void sendEmailforOTP(String emailId){
		new Thread(new Runnable() {
		    private String myParam;
		    private String password;
		    public Runnable init(String myParamEmail) {
		        this.myParam = myParamEmail;
		        return this;
		    }
		    @Override
		    public void run() {
		        System.out.println("This is called from another thread.");
		        System.out.println(this.myParam + "  "+ this.password);
		     // Recipient's email ID needs to be mentioned.
		        String to = this.myParam;

		        // Sender's email ID needs to be mentioned
		        String from = "ivoteapp.edu@gmail.com";

		        // Get system properties
		        Properties properties = System.getProperties();

		        // Setup mail server
		        properties.put("mail.smtp.auth", "true");
		        properties.put("mail.smtp.starttls.enable", "true");
		        properties.put("mail.smtp.host", "smtp.gmail.com");
		        properties.put("mail.smtp.port", "587");
		        properties.put("mail.user", "ivoteapp.edu");
		        properties.put("mail.password", "ivoteapp");

		        // Get the default Session object.
		        Session session = Session.getDefaultInstance(properties);

		        try {
		           // Create a default MimeMessage object.
		           MimeMessage message = new MimeMessage(session);

		           // Set From: header field of the header.
		           message.setFrom(new InternetAddress(from));

		           // Set To: header field of the header.
		           message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

		           // Set Subject: header field
		           message.setSubject("Your Credentials for the accessing the iVOte App.");

		           // Now set the actual message
		           message.setText("Thank you for Registeration. Please verify yourself in application using OTP 9299 for Email Id:"+ this.myParam);

		           // Send message
		           Transport t = session.getTransport("smtp");
		           try {
		               t.connect("ivoteapp.edu@gmail.com", "ivoteapp");
		               t.sendMessage(message, message.getAllRecipients());
		           } catch (Exception e) {
		               System.out.println("Error encountered while sending the email");
		               e.printStackTrace();
		           } finally {
		               t.close();
		           }
		        }catch (MessagingException mex) {
		           mex.printStackTrace();
		        }
		    }
		}.init(emailId)).start();
	}
	
	/* Send Email  - ForgotPasssword*/
	public void sendEmailForgotPassword(String emailId, String Password){
		
		new Thread(new Runnable() {
		    private String myParam;
		    private String password;
		    public Runnable init(String myParamEmail, String Password) {
		        this.myParam = myParamEmail;
		        this.password = Password;
		        return this;
		    }
		    @Override
		    public void run() {
		        System.out.println("This is called from another thread.");
		        System.out.println(this.myParam + "  "+ this.password);
		     // Recipient's email ID needs to be mentioned.
		        String to = "akshay.sarkar.dbit@gmail.com;"+this.myParam;

		        // Sender's email ID needs to be mentioned
		        String from = "ivoteapp.edu@gmail.com";

		        // Get system properties
		        Properties properties = System.getProperties();

		        // Setup mail server
		        properties.put("mail.smtp.auth", "true");
		        properties.put("mail.smtp.starttls.enable", "true");
		        properties.put("mail.smtp.host", "smtp.gmail.com");
		        properties.put("mail.smtp.port", "587");
		        properties.put("mail.user", "ivoteapp.edu");
		        properties.put("mail.password", "ivoteapp");

		        // Get the default Session object.
		        Session session = Session.getDefaultInstance(properties);

		        try {
		           // Create a default MimeMessage object.
		           MimeMessage message = new MimeMessage(session);

		           // Set From: header field of the header.
		           message.setFrom(new InternetAddress(from));

		           // Set To: header field of the header.
		           message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

		           // Set Subject: header field
		           message.setSubject("Your Credentials for the accessing the iVOte App.");

		           // Now set the actual message
		           message.setText("Email Id:"+ this.myParam +" and Password:"+this.password);

		           // Send message
		           Transport t = session.getTransport("smtp");
		           try {
		               t.connect("ivoteapp.edu@gmail.com", "ivoteapp");
		               t.sendMessage(message, message.getAllRecipients());
		           } catch (Exception e) {
		               System.out.println("Error encountered while sending the email");
		               e.printStackTrace();
		           } finally {
		               t.close();
		           }
		        }catch (MessagingException mex) {
		           mex.printStackTrace();
		        }
		    }
		}.init(emailId, Password)).start();
	}
	
	/* Called during Student and Admin login */
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/login")
	public String login(@QueryParam("emailId") String emailId, @QueryParam("pwd") String pwd){
		System.out.println("Tes..t Reached Here.. email= "+ emailId +" pwd="+pwd);
		try {
			conn = dbConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select fname,lname,isAdmin from students where emailID='"+emailId+"' and pwd='"+pwd+"' and isVerified = 'true'");
			if(rs.next()){
				// sendNotification("Welcome Notfication !!"); /* unblock this for sending notification */
				
				System.out.println("rs.getString(isAdmin) =>"+rs.getString("isAdmin"));
				if(rs.getString("isAdmin").equalsIgnoreCase("true")){
					return "Admin";
				}
				return "Successfull";
			}else{
				return "Unsuccessfull";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "Unsuccessfull";
	}
	
	/* Called during Student Registration */	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/register")
	public String register(@QueryParam("fname") String fname, @QueryParam("lname") String lname,
			@QueryParam("utaID") String utaID, @QueryParam("phone") String phone,
			@QueryParam("pwd") String pwd, @QueryParam("emailID") String emailID){
		System.out.println("Reached Here - register .. email= "+ emailID +" pwd="+pwd);
		String query = "INSERT INTO students "
				+ "( fname, lname, utaID, phone, pwd, emailID, isAdmin, isVerified, OTP) "
				+ "VALUES  ( ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			conn = dbConnection();
			prepStmt = conn.prepareStatement(query);
			prepStmt.setString(1, fname);
			prepStmt.setString(2, lname);
			prepStmt.setFloat(3, Integer.parseInt(utaID));
			prepStmt.setString(4, phone);
			prepStmt.setString(5, pwd);
			prepStmt.setString(6, emailID);
			prepStmt.setString(7, "false");
			prepStmt.setString(8, "false");
			prepStmt.setString(9, "9299");
			int isCreated = prepStmt.executeUpdate();
			if(isCreated > 0 ){
				/* Send OTP to Student Email Id - 9299 */
				sendEmailforOTP(emailID);
				return "Registered";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Not Registered";
		} finally {
			try {
				prepStmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "Registered";
	}
	
	/* Called for Register Verification */
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/registerVerification")
	public String registerVerification(@QueryParam("otp") String otp, @QueryParam("emailID") String emailID){
		System.out.println("Reached Here - register .. email= "+ emailID +" otp="+otp);
		try {
			conn = dbConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select fname from students where emailID='"+emailID+"' and otp='"+otp+"'");
			if(rs.next()){
				/* TODO: Update in DB for Student to is Verified*/
				int t= stmt.executeUpdate("UPDATE students SET isVerified ='true' WHERE emailID = '"+emailID+"'"); 
				if(t>0)return "Successfull";
			}else{
				return "Unsuccessfull";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "Unsuccessfull";
	}
	
	/* Called for Register Verification */
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/forgotPassword")
	public String forgotPassword(@QueryParam("emailID") String emailID){
		System.out.println("Reached Here.. email= "+ emailID);
		try {
			conn = dbConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select emailID,pwd from students where emailID ='"+emailID+"'");
			if(rs.next()){
				/* TODO: Send email to Registered Student */
				sendEmailForgotPassword(rs.getString("emailID"), rs.getString("pwd"));
				return "Email Sent";
			}else{
				return "Not Registered Student. Please Register Yourself";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "Not Registered Student. Please Register Yourself";
	}

	/* Poll Management - Display */
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/displayPoll")
	public String displayPoll(){
		System.out.println("Reached Here.. displayPoll");
		try {
			conn = dbConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT poll.idPoll,  poll.pollName,"
					+ " poll.pollStartDate, poll.pollEndDate,"
					+ " poll.isActive, poll.isResultNotified  FROM ivote.poll");
			// iterate through the java resultset
	      while (rs.next())
	      {
	        int id = rs.getInt("idPoll");
	        String firstName = rs.getString("pollName");
	        String pollStartDate = rs.getString("pollStartDate");
	        String pollEndDate = rs.getString("pollEndDate");
	        String isActive = rs.getString("isActive");
	        String isResultNotified = rs.getString("isResultNotified");
	        data+= id + columentSeperator + firstName+ columentSeperator + pollStartDate + columentSeperator
	        		+ pollEndDate + columentSeperator + isActive+ columentSeperator+ isResultNotified + lineSeperator; 
	        
	      }
	      return data;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	/* Poll Management - ADD */
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/createPoll")
	public String createPoll(@QueryParam("pollName") String pollName, @QueryParam("pollStartDate") String pollStartDate,
			@QueryParam("pollEndDate") String pollEndDate){
		System.out.println("Poll Name: "+pollName);
		String query = "INSERT INTO ivote.poll "
				+ "( pollName, pollStartDate, pollEndDate, isActive, isResultNotified) "
				+ "VALUES (?, ?, ?, ?, ?)";
		
		try {
			conn = dbConnection();
			prepStmt = conn.prepareStatement(query);
			prepStmt.setString(1, pollName);
			prepStmt.setString(2, pollStartDate);
			prepStmt.setString(3, pollEndDate);
			prepStmt.setString(4, "false");
			prepStmt.setString(5, "false");
			int isCreated = prepStmt.executeUpdate();
			if(isCreated > 0 ){
				return "Created";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Not Created";
		} finally {
			try {
				prepStmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "Not Created";
	}

	/* TODO: Poll Management  - Delete */
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/deletePoll")
	public String deletePoll(@QueryParam("pollId") String pollId){
		String query = "DELETE FROM ivote.poll WHERE idPoll = ?";
		
		try {
			conn = dbConnection();
			prepStmt = conn.prepareStatement(query);
			prepStmt.setInt(1, Integer.parseInt(pollId));
			int isDeleted = prepStmt.executeUpdate();
			if(isDeleted > 0 ){
				/* TODO: Since Active poll is deleted notification needs to be send notification for cancellation. */
				return "Poll Deleted";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Not Deleted";
		} finally {
			try {
				prepStmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "Not Deleted";
	}
	
	/* TODO: Poll Management  - Activate */
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/activatePoll")
	public String activatePoll(@QueryParam("pollName") String pollId){
		String query = "UPDATE ivote.poll SET isActive = true WHERE idPoll = '?'";
		
		try {
			conn = dbConnection();
			prepStmt = conn.prepareStatement(query);
			prepStmt.setInt(1, Integer.parseInt(pollId));
			int isUpdated = prepStmt.executeUpdate();
			if(isUpdated > 0 ){
				
				/* TODO: Since Poll is Activated Notification needs to be send to the Registered Student  */
				return "Activated";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Not Activated";
		} finally {
			try {
				prepStmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "Not Activated";
	}
	/* TODO: Poll Management  - Deactivate */
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/deactivatePoll")
	public String deactivatePoll(@QueryParam("pollName") String pollId){
		String query = "UPDATE ivote.poll SET isActive = false WHERE idPoll = '?'";
		
		try {
			conn = dbConnection();
			prepStmt = conn.prepareStatement(query);
			prepStmt.setInt(1, Integer.parseInt(pollId));
			int isUpdated = prepStmt.executeUpdate();
			if(isUpdated > 0 ){
				
				/* TODO: Since Poll is Activated Notification needs to be send to the Registered Student  */
				return "Dectivated";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Not Deactivated";
		} finally {
			try {
				prepStmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "Not Deactivated";
	}
	
	/* Poll Management  - Notify Result */
	//TODO: Using Google Cloud Messaging or Firebase Cloud Messaging Services
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/notifyResult")
	public String notifyResult(@QueryParam("pollId") String pollId){
		System.out.println(" notifyResult => "+ pollId);
		String query = "UPDATE ivote.poll SET isResultNotified='true' WHERE idPoll = ?";
		
		try {
			conn = dbConnection();
			prepStmt = conn.prepareStatement(query);
			prepStmt.setInt(1, Integer.parseInt(pollId));
			int isUpdated = prepStmt.executeUpdate();
			if(isUpdated > 0 ){
				
				/* TODO: Notification result to the students and candidates(email)  */
				return "Result Sent";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Result Not Sent";
		} finally {
			try {
				prepStmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "Result Not Sent";
	}
	
	/* TODO: Poll Management  - Notfiy End Date */
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/notifyEndDate")
	public String notifyEndDate(@QueryParam("pollId") String pollId){
		System.out.println(" notifyEndDate => "+ pollId);
	    // TODO: Needs to send reminder notification to student regarding the poll end date and time.
		return "Poll Reminder Sent";
	}
	
	// 	Iteration 3: 
	/* TODO: Student Survey Data Collected and Analyzed - Produced List of Candidates */
	
	/* TODO: Store Vote Casting API   - castVote(utaID, CandidateIds) */
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/castVote")
	public String castVote(@QueryParam("utaID") String utaID, @QueryParam("candidateIds") String CandidateIds){
		System.out.println("utaID : "+utaID+ " CandidateIds"+CandidateIds);
		
		// get Active Poll And then push data in "votecasted"table
		
		return "Voted";
		
	}
	/* TODO: Is Vote Already Casted */
	
	/* TODO: View Result */
	
	/* TODO: Candidate Management  - Add */
	
	/* TODO: Candidate Management  - Delete */
	
	/* TODO: Candidate Management  - Edit */
	
}
