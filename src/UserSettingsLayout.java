import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class UserSettingsLayout extends VBox {
	
	public UserSettingsLayout(double padding) {
		super(padding);
		// Set anchors for blank page
		AnchorPane.setTopAnchor(this, 10.0);
		AnchorPane.setBottomAnchor(this, 10.0);
		AnchorPane.setLeftAnchor(this, 10.0);
		AnchorPane.setRightAnchor(this, 10.0);
				
		// Set title
		Label lb_title = new Label("Ali Cheddadi\n");
		lb_title.setStyle("-fx-font-size: 18px");
		
		// Set image
		ImageView iv_displayPicture = new ImageView("user_placeholder.png");
		
		// Set button
		Button bt_changeDisplay = new Button("Edit display picture");
		
		// Set status
		ComboBox<String> cb_status = new ComboBox<String>();
		cb_status.getItems().addAll("Available", "Away", "Busy", "Out for lunch");
		cb_status.getSelectionModel().selectFirst();
		HBox hb_status = new HBox(5.0);
		hb_status.setAlignment(Pos.CENTER);
		hb_status.getChildren().addAll(new Label("Online status: "), cb_status);
		
		// Set message
		VBox vb_message = new VBox(5.0);
		vb_message.setMaxWidth(300.0);
		
		Label lb_message = new Label("Short message: ");
		
		TextArea ta_message = new TextArea();
		ta_message.setWrapText(true);
		ta_message.setPrefHeight(60.0);
		vb_message.getChildren().addAll(lb_message, ta_message);
				
		// Set children
		setStyle("-fx-background-color: white");
		setAlignment(Pos.CENTER);
		setPadding(new Insets(10.0, 10.0, 10.0, 10.0));
		getChildren().addAll(lb_title, iv_displayPicture, bt_changeDisplay, hb_status, vb_message);
	}
}
