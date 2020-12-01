import java.util.ArrayList;

public abstract class ConversationCache {
	private static ArrayList<Conversation> conversations = new ArrayList<Conversation>();
	
	public static void addConversation(Conversation conversation) {
		conversations.add(conversation);
	}
	
	public static void addMessage(int conversationIndex, User user, String message) {
		if (conversationIndex > conversations.size() || conversationIndex < 0)
			throw new RuntimeException("Invalid conversation index");
		
		conversations.get(conversationIndex).addMessage(user, message);
	}
	
	public static void clearConversations() {
		conversations.clear();
	}
	
	public static Conversation[] getConversations() {
		Conversation[] toReturn = new Conversation[conversations.size()];
		for (int i = 0; i < toReturn.length; i++) toReturn[i] = conversations.get(i);
		
		return toReturn;
	}
	
	public static boolean isEmpty() {
		return conversations.size() == 0;
	}
}
