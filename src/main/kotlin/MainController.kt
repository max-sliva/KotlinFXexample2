package controller

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.input.MouseEvent
import javafx.scene.control.ListView as ListView

class MainController {

    @FXML
    lateinit var button1: Button

    @FXML
    lateinit var textField: TextField

    @FXML
    lateinit var textArea: TextArea

    @FXML
    lateinit var listView: ListView<String>

    @FXML
    private fun handleMouseEnter(event: MouseEvent) { // import javafx.scene.input.MouseEvent;
        textField.text = event.source.toString()
    }

    @FXML
    private fun handle(event: MouseEvent) {
        textArea.appendText("Hello!!")
    }

    @FXML //обязательное указание
    private fun handleCheckChange(event: ActionEvent) { // import javafx.event.ActionEvent
        val temp: CheckBox =  event.source as CheckBox
        println("check =${temp.isSelected}")
    }

    fun initialize() {
        println("Controller working")
        listView.items.addAll("1", "2")
        button1.setOnAction {
            println("Button clicked!")
        }
    }
}