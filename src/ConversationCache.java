import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public abstract class ConversationCache {
	private static ArrayList<Conversation> conversations = new ArrayList<Conversation>();
	
	private static Semaphore semaphore = new Semaphore(1);
	private static volatile int conversationCount;
	private static volatile int messageCount;
	private static volatile String message;
	
	private static volatile boolean initialized;
	
	private static class ConversationCacheThread implements Runnable {
		
		private ConversationCacheThread() {
			try {
				semaphore.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			final int NAME = 0, MESSAGE = 1;
			conversationCount = -1;
			ClientController.getConversationCount(UserCache.getCurrentUser());
			
			long startTime = System.currentTimeMillis();
			
			while(conversationCount == -1) {
				if (System.currentTimeMillis() - startTime > 3000) 
					throw new RuntimeException("ConversationCacheThread timedout.");
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			Conversation[] conversationBuffer = new Conversation[conversationCount];
			for (int i = 0; i < conversationBuffer.length; i++) {
				conversationBuffer[i] = new Conversation(i);
				
				messageCount = -1;
				ClientController.getMessageCount(UserCache.getCurrentUser(), i);
				
				startTime = System.currentTimeMillis();
				while(messageCount == -1) {
					if (System.currentTimeMillis() - startTime > 3000) 
						throw new RuntimeException("ConversationCacheThread timedout.");
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				for (int j = 0; j < messageCount; j++) {
					
					message = null;
					ClientController.getMessage(i, j);
					
					startTime = System.currentTimeMillis();
					while(message == null) {
						if (System.currentTimeMillis() - startTime > 3000) 
							throw new RuntimeException("ConversationCacheThread timedout.");
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					
					String[] splitMessage = message.split("\n");
					conversationBuffer[i].addMessage(UserCache.getUser(splitMessage[NAME]), splitMessage[MESSAGE]);
				}
				
			}
			
			ConversationCache.clearConversations();
			for (Conversation toAdd: conversationBuffer)
				ConversationCache.addConversation(toAdd);
			
			if (!initialized)
				initialized = true;
			
			semaphore.release();
		}
		
	}
	
	public static boolean isInitialized() {
		return initialized;
	}
	
	public static void updateConversations() {
		ConversationCacheThread thread = new ConversationCacheThread();
		thread.run();
	}
	
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
