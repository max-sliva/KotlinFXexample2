package controller

import Account
import getUsers
import javafx.fxml.FXML
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.cell.PropertyValueFactory

class AccountController {
    @FXML
    lateinit var accountTable: TableView<Account>
    @FXML lateinit var loginCol: TableColumn<Account, String>
    @FXML lateinit var fioCol: TableColumn<Account, String>
    @FXML lateinit var statusCol: TableColumn<Account, String>
    @FXML lateinit var passCol: TableColumn<Account, String>
    fun initialize() {
        println("Account table!")
//связываем колонки таблицы с соответствующими полями класса Account
        loginCol.cellValueFactory = PropertyValueFactory("login")
        fioCol.cellValueFactory = PropertyValueFactory("fio")
        statusCol.cellValueFactory = PropertyValueFactory("status")
        passCol.cellValueFactory = PropertyValueFactory("pass")
        accountTable.items = getUsers() //загружаем все из data в таблицу
    }
}