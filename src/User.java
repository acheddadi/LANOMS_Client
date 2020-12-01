import javafx.scene.image.Image;

public class User implements Comparable<User> {
		private String username;
		private String name;
		private String message;
		private String status;
		private String department;
		private String email;
		private Image displayPicture;
		
		public User(String username, String name, String message, String status, String department, String email, Image displayPicture) {
			this.username = username;
			this.name = name;
			this.message = message;
			this.status = status;
			this.department = department;
			this.email = email;
			this.displayPicture = displayPicture;
		}
		
		public String getUsername() {
			return username;
		}
		
		public String getName() {
			return name;
		}
		
		public String getMessage() {
			return message;
		}
		
		public String getStatus() {
			return status;
		}
		
		public String getDepartment() {
			return department;
		}
		
		public String getEmail() {
			return email;
		}
		
		public Image getDisplayPicture() {
			return displayPicture;
		}

		@Override
		public int compareTo(User obj) {
			return name.compareTo(obj.name);
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof User)
				return ((User)obj).name.equals(name);
			
			return false;
		}
	}