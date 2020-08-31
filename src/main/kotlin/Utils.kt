import ch.qos.logback.classic.Level
import ch.qos.logback.classic.LoggerContext
import com.mongodb.MongoClient
import com.mongodb.client.MongoCollection
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.control.cell.PropertyValueFactory
import org.bson.Document
import org.bson.types.ObjectId
import org.slf4j.LoggerFactory
import java.math.BigInteger
import java.security.MessageDigest

fun main(){
    print("Input string: ")
    val str = readLine()
    val strMD5 = str?.md5()
    println("md5 = $strMD5")
}

fun getUsers(): ObservableList<Account>? {
    var accountsList = ArrayList<Account>()
    var userCollection = getCollectionFromDB("User") //выбираем коллекцию
    val usersCount = userCollection.countDocuments() //кол-во документов
    println("usersCount = $usersCount") //вывод в консоль
    var users = ArrayList<Document>() //массив для документов из БД
    val iter = userCollection.find() //получаем итератор из коллекции
    iter.into(users) //из итератора в массив
    println("users = $users") //вывод в консоль

    users.forEach {
        println("user = ${it.values}")
        val tempObj = Account(it["login"].toString(), it["fio"].toString(), it["status"].toString(), it["pass"].toString())
        accountsList.add(tempObj)
    }
    val data = FXCollections.observableArrayList(accountsList)
    return data
}

fun addUser(user: Account){
    var newUser = Document("_id", ObjectId())
    newUser.append("login",user.login)
            .append("fio",user.fio)
            .append("status", user.status)
            .append("pass",user.pass)
    var userCollection = getCollectionFromDB("User") //выбираем коллекцию
    userCollection.insertOne(newUser)
}

fun String.md5(): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
}

fun getCollectionFromDB(col: String): MongoCollection<Document> {
    val loggerContext = LoggerFactory.getILoggerFactory() as LoggerContext
    val rootLogger = loggerContext.getLogger("org.mongodb.driver")
    rootLogger.level = Level.OFF //выключаем лишние сообщения от MongoDB
    val mongoUrl = "localhost" //адрес локального сервера MongoDB
    val mongoClient = MongoClient(mongoUrl, 27017) //клиент MongoDB
    val mongoDatabase = mongoClient.getDatabase("Portal") //выбираем БД
    var collection = mongoDatabase.getCollection(col) //выбираем коллекцию
    return collection
}

fun isUser(user: String): Boolean {
    var userValid = false
    val  userCollection= getCollectionFromDB("User")
    val docs: ArrayList<Document> = ArrayList<Document>()
    val iter = userCollection.find()
    docs.clear()
    iter.into(docs);
    val findLogin = docs.filter { it["login"] == user }
    if (findLogin.isNotEmpty()) userValid = true
    return userValid
}

fun passForUser(user: String, pass: String): Boolean{
    var passValid = false
    val  userCollection= getCollectionFromDB("User")
    val docs: ArrayList<Document> = ArrayList<Document>()
    val iter = userCollection.find()
    docs.clear()
    iter.into(docs);
    val findLogin = docs.filter { it["login"] == user }
    if (findLogin.isNotEmpty()) {
        println("!Match! Login = ${findLogin.first()["login"]}")
        val user = findLogin.first()
        if (user["pass"] == pass?.md5()) {
            passValid = true
        }
    }
    return passValid
}