import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Semaphore;

import javafx.scene.image.Image;

public abstract class UserCache {
	private static String currentUser;
	private static ArrayList<User> userList = new ArrayList<User>();
	
	private static Semaphore semaphore = new Semaphore(1);
	private static volatile int userCount;
	private static volatile String userInfo;
	private static volatile byte[] userDisplayPicture;
	
	private static class UserCacheThread implements Runnable {
		private UserCacheThread() {
			try {
				semaphore.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			final int USERNAME = 0, NAME = 1, PROFILE = 2, STATUS = 3, DEPARTMENT = 4, EMAIL = 5;
			initializeVariables();
			
			// Request ClientController to update userCount.
			ClientController.getUserCount();
			// Start our timeout timer.
			long startTime = System.currentTimeMillis();
			
			// Wait for userCount to be updated but ClientController.
			while (userCount == -1) {
				// Timeout after 10 seconds.
				if (System.currentTimeMillis() - startTime > 3000) 
					throw new RuntimeException("UserCacheThread timedout.");
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			// Initialize user buffer.
			User[] toAdd = new User[userCount];
			
			// Get user info of all users, according to userCount.
			for (int i = 0; i < userCount; i++) {
				// Request ClientController to update userInfo.
				ClientController.getUserInfo(i);
				
				// Start our timeout timer.
				startTime = System.currentTimeMillis();
				
				// Wait for userInfo to be updated by ClientController.
				while (userInfo == null) {
					if (System.currentTimeMillis() - startTime > 3000) 
						throw new RuntimeException("UserCacheThread timedout.");
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				// Split user info into separate strings.
				String[] splitUserInfo = userInfo.split("\n");
				userInfo = null;
				
				// Request ClientController to update userDisplayPicture.
				/*ClientController.getUserDisplayPicture(i);
				
				// Start our timeout timer.
				startTime = System.currentTimeMillis();
				
				// Wait for userDisplayPicture to be updated by ClientController.
				while (userDisplayPicture == null) {
					if (System.currentTimeMillis() - startTime > 3000) 
						throw new RuntimeException("UserCacheThread timedout.");
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				// Create image from binary array.
				Image picture = new Image(new ByteArrayInputStream(userDisplayPicture));*/
				
				// Stub
				Image picture = new Image("user_placeholder.png");
				
				// Add user to buffer.
				toAdd[i] = new User(
						splitUserInfo[USERNAME],
						splitUserInfo[NAME],
						splitUserInfo[PROFILE],
						splitUserInfo[STATUS],
						splitUserInfo[DEPARTMENT],
						splitUserInfo[EMAIL],
						picture);
			}
			// Clear user list.
			UserCache.clearUsers();
			// Transfer users from buffer to user cache.
			for(User user: toAdd) {
				System.out.println("Adding " + user.getName());
				userList.add(user);
			}
			
			// Reset our variables for next time.
			initializeVariables();
			
			// Release lock
			semaphore.release();
			
			// Update selection
			GlobalPane.updateSelection();
		}
		
	}
	
	private static void initializeVariables() {
		userCount = -1;
		userInfo = null;
		userDisplayPicture = null;
	}
	
	public static void updateUserList() {
		UserCacheThread thread = new UserCacheThread();
		thread.run();
	}
	
	public static void setUserCount(int userCount) {
		System.out.println("Hi, there are " + userCount + " users.");
		UserCache.userCount = userCount;
	}

	public static void setUserInfo(String userInfo) {
		System.out.println(userInfo);
		UserCache.userInfo = userInfo;
	}

	public static void setUserDisplayPicture(byte[] userDisplayPicture) {
		UserCache.userDisplayPicture = userDisplayPicture;
	}

	public static void setCurrentUser(String username) {
		currentUser = username;
	}
	
	public static String getCurrentUser() {
		return currentUser;
	}
	
	public static boolean isCurrentUser(User user) {
		return currentUser.equals(user.getUsername());
	}
	
	public static boolean isLoggedIn() {
		return currentUser.isBlank();
	}
	
	public static void addUser(String username, String name, String message, String status, String department, String email, Image displayPicture) {
		userList.add(new User(username, name, message, status, department, email, displayPicture));
	}
	
	public static void clearUsers() {
		userList.clear();
	}
	
	public static User getUser(String name) {
		User toReturn = null;
		for(User user: userList)
			if (user.getName().equals(name)) toReturn = user;
		
		return toReturn;
	}
	
	public static User[] getUserList() {
		Collections.sort(userList);
		User[] toReturn = new User[userList.size()];
		for (int i = 0; i < toReturn.length; i++) toReturn[i] = userList.get(i);
		
		return toReturn;
	}
	
	public static boolean isEmpty() {
		return userList.size() == 0;
	}
}
