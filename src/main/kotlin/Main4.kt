import javafx.application.Application
import javafx.collections.FXCollections
import javafx.scene.Scene
import javafx.scene.chart.BarChart
import javafx.scene.chart.CategoryAxis
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart
import javafx.scene.chart.XYChart.Series
import javafx.scene.control.Button
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.layout.BorderPane
import javafx.scene.layout.StackPane
import javafx.stage.Stage
import java.util.*


class Main4 : Application() {
    private var bc : BarChart<String, Number>? = null  //объект-диаграмма
    private var series1 : Series<String, Number>? = null  //серия данных
    //т.е. некоторый набор однотипных данных
    private var data1 : XYChart.Data<String, Number>? = null //данные для столбцов

    private fun init(primaryStage: Stage) {
        val root = BorderPane()
        primaryStage.scene = Scene(root)
        root.center = createChart() //помещаем диаграмму в центр окна
        val startButton = Button("Start") //кнопка изменения значения столбца в диаграмме
        startButton.setOnAction{
            data1?.yValue = data1?.yValue!!.toInt() + 100
        }
        root.bottom = startButton //в низ окна вставляем созданную кнопку
    }

    private fun createChart(): BarChart<String, Number> { //метод, создающий и возвращающий диаграмму
        val years = arrayOf("2007", "2008", "2009") //массив строк с подписями оси ОХ
        val xAxis = CategoryAxis() //создаем ось ОХ
        val yAxis = NumberAxis() //создаем ось OY

        //задаем формат подписей делений оси OY – со знаком $
        yAxis.tickLabelFormatter = NumberAxis.DefaultFormatter(yAxis, "$", null)
        //создаем столбчатую диаграмму с осями xAxis и yAxis
        bc = BarChart(xAxis, yAxis)
//        bc!!.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED) { event ->
//            println("BarChart clicked!")
//            println(event.getTarget())
//            if (event.getTarget() is StackPane) {
//                val tempSP = event.getTarget() as StackPane
//                val tempStr = (event.getTarget() as StackPane).styleClass.toString()
//                val serNum = tempStr[tempStr.indexOf("series") + 6].toInt() - 48
//                //					System.out.println("series"+serNum);
//                val dataNum = tempStr[tempStr.indexOf("data") + 4].toInt() - 48
//                //					System.out.println("data"+dataNum);
////					System.out.println(bc.getData().get(serNum).getData().get(dataNum));
//                val tempData = bc!!.data[serNum].data[dataNum]
////                tempData.setYValue((tempData.yValue.toInt() + 100) as Number)
//            }
//        }

        bc!!.title = "Advanced Bar Chart" // задаем название диаграммы
        xAxis.label = "Year" //задаем общую подпись оси ОХ
        //задаем подписи категорий оси ОХ
        xAxis.categories = FXCollections.observableArrayList(Arrays.asList(*years))
        yAxis.label = "Price" //задаем общую подпись оси OY
        series1 = Series() //создаем набор однотипных данных
        series1!!.name = "Data Series 1" //даем ему название и так еще 2 раза
        val series2 = Series<String, Number>()
        series2.name = "Data Series 2"
        val series3 = Series<String, Number>()
        series3.name = "Data Series 3"
        // задаем данные
//для 2007 года данные со значением 567
        data1 = XYChart.Data(years[0], 567)
        series1!!.data.add(data1) //добавляем данные в серию
        //остальные данные создаем и сразу добавляем
        series1!!.data.add(XYChart.Data(years[1], 1292))
        series1!!.data.add(XYChart.Data(years[2], 2180))
        series2.data.add(XYChart.Data(years[0], 956))
        series2.data.add(XYChart.Data(years[1], 1665))
        series2.data.add(XYChart.Data(years[2], 2450))
        series3.data.add(XYChart.Data(years[0], 800))
        series3.data.add(XYChart.Data(years[1], 1000))
        series3.data.add(XYChart.Data(years[2], 2800))
        bc!!.data.add(series1) //добавляем созданные наборы в диаграмму
        bc!!.data.add(series2)
        bc!!.data.add(series3)
        return bc!!  //возвращаем созданную диаграмму
    }

    override fun start(primaryStage: Stage?) {
        init(primaryStage!!)
        primaryStage.show()
        for (serie in bc!!.data) {
            for (item in serie.data) {
                item.node.setOnMousePressed {
                    println("you clicked $item $serie")
                    if (it.isPrimaryButtonDown) {
                        item.yValue = item.yValue.toInt() + 100
                    }
                    else item.yValue = item.yValue.toInt() - 100
                }
            }
        }

    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(Main4::class.java)
        }
    }
}