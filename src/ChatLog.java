import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ChatLog extends ScrollPane {
	
	public ChatLog() {
		super();
		
		// Set anchors for chat log
		AnchorPane.setTopAnchor(this, 10.0);
		AnchorPane.setBottomAnchor(this, 60.0);
		AnchorPane.setLeftAnchor(this, 10.0);
		AnchorPane.setRightAnchor(this, 10.0);
		
		setVvalue(1.0);
		setFitToWidth(true);
		setHbarPolicy(ScrollBarPolicy.NEVER);
		setPrefSize(getWidth(), getHeight());
	}
	
	public void setConversation(Conversation conversation) {
		Message[] messages = conversation.getMessages();
		VBox chatLog = new VBox();
		chatLog.setStyle("-fx-background-color: white");
		for (int i = 0; i < messages.length; i++) {
			Label lb_message = new Label(messages[i].getUser().getName());
			lb_message.setStyle("-fx-text-fill: dimgrey; -fx-font-weight: bold;");
			
			ImageView iv_message = new ImageView(messages[i].getUser().getDisplayPicture());
			iv_message.setPreserveRatio(true);
			iv_message.setFitHeight(32.0);
			
			FlowPane fp_message = new FlowPane(new Text(messages[i].getMessage()));
			fp_message.setMinHeight(30);
			fp_message.setPadding(new Insets(10.0, 10.0, 10.0, 10.0));
			fp_message.setStyle("-fx-background-color: whitesmoke; -fx-background-radius: 5 5 5 5;");
			
			BorderPane bp_message = new BorderPane();
			bp_message.setTop(lb_message);
			bp_message.setLeft(iv_message);
			bp_message.setCenter(fp_message);
			bp_message.setPadding(new Insets(7.5, 10.0, 7.5, 10.0));
			BorderPane.setMargin(iv_message, new Insets(0.0, 10.0, 0.0, 0.0));
			BorderPane.setMargin(lb_message, new Insets(0.0, 0.0, 10.0, 0.0));
			
			chatLog.getChildren().add(bp_message);
		}
		setContent(chatLog);
	}
}
