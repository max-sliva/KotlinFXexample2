package controller

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.input.MouseEvent


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

    @FXML
    private fun handleButtonOnPress(event: ActionEvent){
        println("Hello!")
    }

    @FXML //обязательное указание
    private fun handleCheckChange(event: ActionEvent) { // import javafx.event.ActionEvent
        val temp: CheckBox =  event.source as CheckBox
        println("check =${temp.isSelected}")
    }

    @FXML
    private fun handleRadioButton(event: ActionEvent) {
//получаем ссылку на объект, вызвавший событие
        val selRadio = event.source as RadioButton
        //очищаем установленные стили (на сцене и на главной панели)
        selRadio.scene.stylesheets.clear()
        selRadio.scene.root.stylesheets.clear()
        when (selRadio.text) {
            "коричневый" -> //берем родительскую сцену (основу окна в JavaFX) и добавляем в ее список стилей новый из файла
                selRadio.scene.stylesheets.add(javaClass.getResource("/application1.css").toExternalForm())
            "синий" -> selRadio.scene.stylesheets.add(javaClass.getResource("/application2.css").toExternalForm())
        }
    }

    fun initialize() {
        println("Controller working")
        listView.items.addAll("1", "2")
        button1.setOnAction {
            textArea.appendText("Button clicked!")
        }
    }
}