import java.io.IOException;

public abstract class ClientController {

  public static void getUserCount() {
    try {
      SocketClient.send("USER_COUNT", "");
    } 
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static String getUserInfo(int i) {
    try {
      SocketClient.send("USER_INFO", i +"");
    } 
    catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static byte[] getUserDisplayPicture(int i) {
    return null;
  }

  public static void getConversationCount(int user) {
    try {
      SocketClient.send("CONV_COUNT", user +"");
    } 
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void getMessageCount(int i, int j) {
    try {
      SocketClient.send("MSG_COUNT", i +"#"+j);
    } 
    catch (IOException e) {
      e.printStackTrace();
    }
    
    // return 0;
  }

  public static String getMessage(int i, int j) {
    // TODO Auto-generated method stub
    return null;
  }

  private static void setUserCount(int count){
    UserCache.setUserCount(count);
  }

  private static void setUserInfo(String data){
    UserCache.setUserInfo(data);
  }
  
	// Authentication stub
	public static int authenticateUser(String username, String password) {
		// TODO Auto-generated method stub
		return !username.isBlank() && !password.isBlank() ? 3 : 1;
	}
	
	public static void login(String userName, String password) {
	      try {
	          SocketClient.send("USER_AUTH", userName +"\n"+password);
	        } 
	        catch (IOException e) {
	          e.printStackTrace();
	        }
	  }

  public static void makeMessage(){
    MakeMessage message = new MakeMessage();
    message.setUsername("ali");
    message.setMessgae("HI This is demo message");
    message.setId(0);
    try {
      SocketClient.send("MAKE_MESSAGE", Utility.fromMessage(message));
    } 
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void handleMessage(SocketMessage data) {
    if (data.getKey() != null) {
      switch (data.getKey()) {
        case "USER_COUNT":
          setUserCount(Utility.convertToInt(data.getData()));
          break;
        case "USER_INFO":
           setUserInfo(data.getData());
          break;
        case "DISPLAY_PICTURE":
        	UserCache.setUserDisplayPicture(data.getByteData());
        	break;
	case "USER_AUTH":
            LoginPane.setLoginCode(Utility.convertToInt(data.getData()));
            break;
        default:
          System.out.println("Key: " + data.getKey() + " Value: " + data.getData());
          return;

      }
    }
  }

  public static void onDisconnect() {
    System.out.println("Server Disconnected");

  }

  public static void onException(Exception e) {
    e.printStackTrace();

  }

}
