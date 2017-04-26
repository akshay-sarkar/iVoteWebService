package uta.myWebService;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
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
	public void sendEmailForgotPassword(String emailId, String Password, String category){
		
		new Thread(new Runnable() {
		    private String myParam;
		    private String password;
		    private String category;
		    public Runnable init(String myParamEmail, String Password, String category) {
		        this.myParam = myParamEmail;
		        this.password = Password;
		        this.category = category;
		        return this;
		    }
		    @Override
		    public void run() {
		        System.out.println("This is called from another thread.");
		        System.out.println(this.myParam + "  "+ this.password + "  "+ category);
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

		           if(category.equalsIgnoreCase("forgotPassword")){
		        	   // Set Subject: header field
			           message.setSubject("Your Credentials for the accessing the iVOte App.");
			           // Now set the actual message
			           message.setText("Email Id:"+ this.myParam +" and Password:"+this.password);

		           }else{
		        	   // Set Subject: header field
			           message.setSubject("Candidate Removed");
			           // Now set the actual message
			           message.setText("You have been removed from "+this.password);
		           }
		           
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
		}.init(emailId, Password, category)).start();
	}
	
	/* Called during Student and Admin login */
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/login")
	public String login(@QueryParam("emailId") String emailId, @QueryParam("pwd") String pwd){
		System.out.println("Tes..t Reached Here.. email= "+ emailId +" pwd="+pwd);
		
		String query_activePoll = "SELECT idPoll, pollName FROM ivote.poll where isActive='true'";
		ResultSet rs_query_activePoll = null;
		
		String query_lastActivePoll = "SELECT idPoll, pollName FROM ivote.poll where isResultNotified='false' and isActive='false'";
		ResultSet rs_query_lastActivePoll = null;
		
		try {
			conn = dbConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select utaID,isAdmin from students where emailID='"+emailId+"' and pwd='"+pwd+"' and isVerified = 'true'");
			if(rs.next()){
				String 	utaID=rs.getString("utaID");
				// sendNotification("Welcome Notfication !!"); /* unblock this for sending notification */
				if(rs.getString("isAdmin").equalsIgnoreCase("true")){
					return "Admin";
				}
				/* Get Active Poll OR Retrieve last Active Poll - isResultNotified*/
				rs_query_activePoll = stmt.executeQuery(query_activePoll);
				if(rs_query_activePoll.next()){
					return "ActivePoll"+columentSeperator+rs_query_activePoll.getInt("idPoll")+
							columentSeperator+rs_query_activePoll.getString("pollName")+ columentSeperator+ utaID;
				}
				//retrieve last active result
				rs_query_lastActivePoll = stmt.executeQuery(query_lastActivePoll);
				if(rs_query_lastActivePoll.next()){
					return "ResultPoll"+columentSeperator+rs_query_lastActivePoll.getInt("idPoll")+
							columentSeperator+rs_query_lastActivePoll.getString("pollName")+ columentSeperator+ utaID;
				}
			}else{
				return "No Login";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "No Login";
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "No Login";
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
		return "Not Registered";
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
				sendEmailForgotPassword(rs.getString("emailID"), rs.getString("pwd"), null);
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
		try {
			conn = dbConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT poll.idPoll,  poll.pollName,"
					+ " poll.pollStartDate, poll.pollEndDate,"
					+ " poll.isActive, poll.isResultNotified  FROM ivote.poll");
			// iterate through the java result  set
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
	
	/* Candidate Management - Display */
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/displayCandidate")
	public String displayCandidate(@QueryParam("pollID") String  pollID){
		System.out.println("Reached Here.. displayCand  " + pollID);
		try {
			conn = dbConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM ivote.candidates where votePostionID="+pollID);
	      while (rs.next())
	      {
	    	  int id = rs.getInt("id");
		        String firstName = rs.getString("candidateFname");
		        String lastname = rs.getString("candidateLname");
		        String candidateEmailId = rs.getString("candidateEmailId");
		        String candidateDOB = rs.getString("candidateDOB");
	    		String candidateGender = rs.getString("candidateGender");
				String candidateCourse = rs.getString("candidateCourse");
				String candidateQualities = rs.getString("candidateQualities");
				String candidateInterests = rs.getString("candidateInterests");
				String candidatesStudentOrganization = rs.getString("candidatesStudentOrganization");
				String candidateCommunityServiceHours = rs.getString("candidateCommunityServiceHours");
		       
		        data+= id + columentSeperator + firstName+ columentSeperator + lastname + columentSeperator+ candidateEmailId
		        		+ columentSeperator + candidateDOB + columentSeperator + candidateGender + columentSeperator + candidateCourse+
		        		columentSeperator+ candidateQualities+ columentSeperator+ candidateInterests+ columentSeperator +
		        		candidatesStudentOrganization+ columentSeperator + candidateCommunityServiceHours +lineSeperator;	
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
	public String activatePoll(@QueryParam("pollId") String pollId){
		String query = "UPDATE ivote.poll SET isActive='true' WHERE idPoll = ?";
		
		try {
			conn = dbConnection();
			prepStmt = conn.prepareStatement(query);
			prepStmt.setInt(1, Integer.parseInt(pollId));
			int isUpdated = prepStmt.executeUpdate();
			if(isUpdated > 0 ){
				
				/* TODO: Since Poll is Activated Notification needs to be send to the Registered Student  */
				return "Poll Activated";
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
	public String deactivatePoll(@QueryParam("pollId") String pollId){
		String query = "UPDATE ivote.poll SET isActive = 'false' WHERE idPoll = ?";
		
		try {
			conn = dbConnection();
			prepStmt = conn.prepareStatement(query);
			prepStmt.setInt(1, Integer.parseInt(pollId));
			int isUpdated = prepStmt.executeUpdate();
			if(isUpdated > 0 ){
				
				/* TODO: Since Poll is Activated Notification needs to be send to the Registered Student  */
				return "Poll Dectivated";
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
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/surveyData")
	public String surveyData(@QueryParam("studentOrganization") String studentOrganization,
			@QueryParam("communityHour") String communityHour,
			@QueryParam("department") String department,
			@QueryParam("qualities") String qualities,
			@QueryParam("interest") String interest){
		System.out.println("Here .. "+qualities + "  "+interest);
		/* TODO: Return Candidates here */

		
		String query = "SELECT * FROM ivote.candidates where votePostionID = (SELECT idPoll FROM ivote.poll where isActive='true')";
		try {
			conn = dbConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
	      while (rs.next())
	      {
	        int id = rs.getInt("id");
	        String firstName = rs.getString("candidateFname");
	        String lastname = rs.getString("candidateLname");
	        String candidateEmailId = rs.getString("candidateEmailId");
	        String candidateDOB = rs.getString("candidateDOB");
    		String candidateGender = rs.getString("candidateGender");
			String candidateCourse = rs.getString("candidateCourse");
			String candidateQualities = rs.getString("candidateQualities");
			String candidateInterests = rs.getString("candidateInterests");
			String candidatesStudentOrganization = rs.getString("candidatesStudentOrganization");
			String candidateCommunityServiceHours = rs.getString("candidateCommunityServiceHours");
	       
	        data+= id + columentSeperator + firstName+ columentSeperator + lastname + columentSeperator+ candidateEmailId
	        		+ columentSeperator + candidateDOB + columentSeperator + candidateGender + columentSeperator + candidateCourse+
	        		columentSeperator+ candidateQualities+ columentSeperator+ candidateInterests+ columentSeperator +
	        		candidatesStudentOrganization+ columentSeperator + candidateCommunityServiceHours +lineSeperator; 
	        		
	        
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
	
	/* TODO: Store Vote Casting API   - castVote(utaID, CandidateIds) */
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/castVote")
	public String castVote(@QueryParam("utaID") String utaID, @QueryParam("candidateIds") String CandidateIds){
		CandidateIds = CandidateIds.substring(1, CandidateIds.length() - 1);
		System.out.println("utaID : "+utaID+ " CandidateIds -> "+CandidateIds);
		int isCreated = 0;
		// get Active Poll And then push data in "votecasted" table
		String query = "SELECT idPoll FROM ivote.poll where isActive='true'";
		String query_castVote = "INSERT INTO ivote.votecasted (utaID, PollId, PollOptionId) "
				+ "VALUES(?,?,?)";
		try {
			conn = dbConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
	      while (rs.next())
	      {
	    	  int id = rs.getInt("idPoll");
	    	  if(CandidateIds.contains(",")){ 
	    		  String[] str = CandidateIds.split(",");
	    		  for(int i=0; i<str.length;i++){
	    			prepStmt = conn.prepareStatement(query_castVote);
	  		        prepStmt.setInt(1, Integer.parseInt(utaID));
	  				prepStmt.setInt(2, id);
	  				prepStmt.setInt(3, Integer.parseInt(str[i].trim()));
	  				isCreated = prepStmt.executeUpdate();
	    		  }
	    	  }else{
	  		        prepStmt = conn.prepareStatement(query_castVote);
	  		        prepStmt.setInt(1, Integer.parseInt(utaID));
	  				prepStmt.setInt(2, id);
	  				prepStmt.setInt(3, Integer.parseInt(CandidateIds));
	  				isCreated = prepStmt.executeUpdate();
	    	  }
			if(isCreated > 0 ){
				return "Vote Casted";
			}
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
		return "UnSuccessfull";
	}
	/* TODO: Is Vote Already Casted */
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/isAlreadyVoted")
	public String isAlreadyVoted(@QueryParam("utaID") String utaID, @QueryParam("pollId") String pollId){
		String query = "SELECT utaID, PollId, PollOptionId FROM ivote.votecasted "
				+ "where PollId="+Integer.parseInt(pollId)+" and utaID="+Integer.parseInt(utaID);
		try {
			conn = dbConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
	      if (rs.next())
	      {
	        return "Vote Already Casted";
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
		return "Vote Not Casted";
	}
	
	/* TODO: View Result */
	@GET
	@Path("/viewResult")
	@Produces(MediaType.TEXT_PLAIN)
	public String viewResult() {
		List<String> winners = new ArrayList<String>();
		String w1 = "First";
		String w2 = "Second";
		winners.add(w1);
		winners.add(w2);
		System.out.println(winners.toString());
		return winners.toString();

	}

	/* TODO: Candidate Management  - Add */
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/addCandidate")
	public String addCandidate(@QueryParam("votePostionID") String votePostionID,
			@QueryParam("candidateFname") String candidateFname,
			@QueryParam("candidateLname") String candidateLname,
			@QueryParam("candidateEmailId") String candidateEmailId,
			@QueryParam("candidateDOB") String candidateDOB,
			@QueryParam("candidateGender") String candidateGender,
			@QueryParam("candidateCourse") String candidateCourse,
			@QueryParam("candidateQualities") String candidateQualities,
			@QueryParam("candidateInterests") String candidateInterests,
			@QueryParam("candidatesStudentOrganization") String candidatesStudentOrganization,
			@QueryParam("candidateCommunityServiceHours") String candidateCommunityServiceHours){
		String query = "INSERT INTO ivote.candidates ( votePostionID, "
				+ "candidateFname, candidateLname, candidateEmailId, candidateDOB,"
				+ " candidateGender, candidateCourse, candidateQualities, candidateInterests,"
				+ " candidatesStudentOrganization, candidateCommunityServiceHours)"
				+ " VALUES ( ?, ? ,? ,? ,? ,? ,?, ?, ?, ?, ?)";
		try {
			conn = dbConnection();
			prepStmt = conn.prepareStatement(query);
			prepStmt.setInt(1, Integer.parseInt(votePostionID));
			prepStmt.setString(2, candidateFname);
			prepStmt.setString(3, candidateLname);
			prepStmt.setString(4, candidateEmailId);
			prepStmt.setString(5, candidateDOB);
			prepStmt.setString(6, candidateGender);
			prepStmt.setString(7, candidateCourse);
			prepStmt.setString(8, candidateQualities);
			prepStmt.setString(9, candidateInterests);
			prepStmt.setString(10, candidatesStudentOrganization);
			prepStmt.setString(11, candidateCommunityServiceHours);
			int isCreated = prepStmt.executeUpdate();
			if(isCreated > 0 ){
				return "Candidate Added";
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
	
	/* TODO: Candidate Management  - Delete */
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/deleteCandidate")
	public String deleteCandidate(@QueryParam("candidateId") String candidateId){
		
		String query =  "SELECT candidates.candidateEmailId, poll.pollName FROM candidates join poll on candidates.votePostionID=poll.idPoll and id=?";
		String deleteQuery =  "DELETE FROM ivote.candidates WHERE id=?";
		try {
			conn = dbConnection();
			rs = stmt.executeQuery(query);
			// iterate through the java result  set
		      while (rs.next())
		      {
		    	/* TODO: Since Poll is deleted Notification send for cancellation to candidate */
				prepStmt = conn.prepareStatement(deleteQuery);
				prepStmt.setInt(1, Integer.parseInt(candidateId));
				int isDeleted = prepStmt.executeUpdate();
				if(isDeleted > 0 ){
					sendEmailForgotPassword(rs.getString("candidateEmailId"), rs.getString("pollName"), null);
					return "Poll Deleted";
				}  
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
	
	/* TODO: Candidate Management  - Edit */
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/editCandidate")
	public String editCandidate(@QueryParam("candidateID") String candidateID,
			@QueryParam("candidateFname") String candidateFname,
			@QueryParam("candidateLname") String candidateLname,
			@QueryParam("candidateEmailId") String candidateEmailId,
			@QueryParam("candidateDOB") String candidateDOB,
			@QueryParam("candidateGender") String candidateGender,
			@QueryParam("candidateCourse") String candidateCourse,
			@QueryParam("candidateQualities") String candidateQualities,
			@QueryParam("candidateInterests") String candidateInterests,
			@QueryParam("candidatesStudentOrganization") String candidatesStudentOrganization,
			@QueryParam("candidateCommunityServiceHours") String candidateCommunityServiceHours){
		String query = "UPDATE ivote.candidates SET candidateFname = ?,"
				+ " candidateLname = ?, candidateEmailId = ?, candidateDOB = ?,"
				+ " candidateGender = ?, candidateCourse = ?, candidateQualities = ?,"
				+ " candidateInterests = ?, candidatesStudentOrganization = ?, candidateCommunityServiceHours = ?"
				+ " WHERE id = ?;";
		try {
			conn = dbConnection();
			prepStmt = conn.prepareStatement(query);

			prepStmt.setString(1, candidateFname);
			prepStmt.setString(2, candidateLname);
			prepStmt.setString(3, candidateEmailId);
			prepStmt.setString(4, candidateDOB);
			prepStmt.setString(5, candidateGender);
			prepStmt.setString(6, candidateCourse);
			prepStmt.setString(7, candidateQualities);
			prepStmt.setString(8, candidateInterests);
			prepStmt.setString(9, candidatesStudentOrganization);
			prepStmt.setString(10, candidateCommunityServiceHours);
			prepStmt.setInt(11, Integer.parseInt(candidateID));
			int isUpdated = prepStmt.executeUpdate();
			if(isUpdated > 0 ){
				return "Candidate Updated";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Not Updated";
		} finally {
			try {
				prepStmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "Not Updated";		
	}
	
}
