import java.util.ArrayList;

public class Conversation {
	private ArrayList<User> participants;
	private ArrayList<Message> conversation;
	private int index;
	
	public Conversation(int index) {
		conversation = new ArrayList<Message>();
		participants = new ArrayList<User>();
		this.index = index;
	}
	
	public void addMessage(User user, String message) {
		System.out.println("Message: " + message);
		System.out.println("From: " + user.getName());
		
		if (!participants.contains(user))participants.add(user);
		conversation.add(new Message(user, message));
	}
	
	public Message[] getMessages() {
		Message[] toReturn = new Message[conversation.size()];
		for (int i = 0; i < toReturn.length; i++) toReturn[i] = conversation.get(i);
		
		return toReturn;
	}
	
	public User[] getParticipants() {
		User[] toReturn = new User[participants.size()];
		for (int i = 0; i < toReturn.length; i++) toReturn[i] = participants.get(i);
		
		return toReturn;
	}
	
	public int getIndex() {
		return index;
	}
}
