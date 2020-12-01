import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ChatInput extends TextField {
	private int conversationIndex;
	public ChatInput() {
		super();
		// Set anchors for chat input
		AnchorPane.setBottomAnchor(this, 10.0);
		AnchorPane.setLeftAnchor(this, 10.0);
		AnchorPane.setRightAnchor(this, 10.0);
				
		// Set height
		this.setPrefHeight(40);
		
		// Set action
		this.setOnAction(e -> sendMessage());
		
		conversationIndex = -1;
	}
	
	private void sendMessage() {
		if (conversationIndex != -1 && !getText().isBlank()) {
			ClientController.makeMessage(UserCache.getCurrentUser(), conversationIndex, getText());
			clear();
		}
	}
	
	public void setConversationIndex(int index) {
		conversationIndex = index;
	}
}
