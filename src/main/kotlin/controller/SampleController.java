package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class SampleController implements Initializable{
	@FXML 
	private Button button1;

	@FXML 
	private TextArea textArea;

	@FXML 
	private TextField textField;


	@FXML //обязательное указание
	private void handleButtonOnPress(ActionEvent event){ // import javafx.event.ActionEvent;
	      System.out.println("Hello");
//	      textArea.appendText("HelloWorld!!");
	}

	@FXML //обязательное указание
	private void handleButton(ActionEvent event){ // import javafx.event.ActionEvent;
	      System.out.println("Hello");
//	      textArea.appendText("HelloWorld!!");
	}

	@FXML 
	private void handleMouseEnter(MouseEvent event){ // import javafx.scene.input.MouseEvent;
	    textField.setText(event.getSource().toString());
	    System.out.println("Hello");
	}

	@FXML
	private void handleRound(MouseEvent event){
	   textArea.appendText("Hello!!");
	    System.out.println("Hello");
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		button1.setOnAction(new EventHandler<ActionEvent>() {

		    @Override
		    public void handle(ActionEvent t) {
		        textArea.appendText("HelloWorld!!");
		    }
		});
		
	}

}
