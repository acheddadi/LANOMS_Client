import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ChatInput extends TextField {
	
	public ChatInput() {
		super();
		
		// Set anchors for chat input
		AnchorPane.setBottomAnchor(this, 10.0);
		AnchorPane.setLeftAnchor(this, 10.0);
		AnchorPane.setRightAnchor(this, 10.0);
				
		// Set height
		this.setPrefHeight(40);
	}
}
