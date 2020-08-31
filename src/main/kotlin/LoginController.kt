package controller

import isUser
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import javafx.scene.layout.VBox
import javafx.stage.Stage
import passForUser
import kotlin.math.log


class LoginController {

    @FXML lateinit var vBox: VBox
    @FXML lateinit var loginField: TextField
    @FXML lateinit var passField: PasswordField

    @FXML
    private fun handleButtonOnPress(event: ActionEvent) {
        println("Ok login")
        val stage = Stage() //создаем окно, в него загрузим нужный fxml-файл
        if (loginField.text == "Admin") //если юзер - Админ
            if (passForUser(loginField.text,passField.text)){ //и пароль правильный
                println("Pass for Admin is valid!")
//то загружаем форму с админским содержимым
                stage.scene = Scene(FXMLLoader.load<Parent?>(Main.javaClass.getResource("main.fxml")))
                stage.show() //выводим созданное окно на экран
                val window: Stage = vBox.scene.window as Stage //получаем ссылку на главное окно
                window.close() //и закрываем его
            } else println("Pass for Admin is NOT valid!") //сообщение о неправильном пароле для админа
        else if (isUser(loginField.text)){ //иначе если пользователь существует
            if (passForUser(loginField.text,passField.text)){ //и пароль для него правильный
                println("Pass for ${loginField.text} is valid!")
//то загружаем форму с пользовательским содержимым
                stage.scene = Scene(FXMLLoader.load<Parent?>(Main.javaClass.getResource("user.fxml")))
                stage.show() //выводим созданное окно на экран
                val window = vBox.scene.window as Stage //получаем ссылку на главное окно
                window.close() //и закрываем его
            } else println("Pass for ${loginField.text} is NOT valid!")
        }
        else println("No such user!!!")  //сообщение о неправильном пользователе
    }

    fun initialize() {
        println("LoginController")
    }
}