import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;



public class ScheduleHelper extends Application {

    public void start(Stage stage) {

        HBox root = new HBox();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        System.out.println(ScheduleCalc.bob);
        ScheduleHelper.launch();
    }

}