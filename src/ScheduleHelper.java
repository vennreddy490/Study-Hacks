import java.sql.Time;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ScheduleHelper extends Application {

    @Override
    public void start(Stage stage) {

        // Adding days and coloumns
        HBox root = new HBox();
        TimeSlot sunday = new TimeSlot("Sunday");
        TimeSlot monday = new TimeSlot("Monday");
        TimeSlot tuesday = new TimeSlot("Tuesday");
        TimeSlot wednesday = new TimeSlot("Wednesday");
        TimeSlot thursday = new TimeSlot("Thursday");
        TimeSlot friday = new TimeSlot("Friday");
        TimeSlot saturday = new TimeSlot("Saturday");
        root.getChildren().addAll(sunday, monday, tuesday, wednesday, thursday, friday, saturday);

        // Resizing Limits
        ScheduleHelper.TimeSlotGrow(sunday, monday, tuesday, wednesday, thursday, friday, saturday);

        // Initializing the Scene and displaying it
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Schedule Helper");

    }

    public static void TimeSlotGrow(TimeSlot... days) {
        for (TimeSlot day : days) {
            HBox.setHgrow(day, Priority.ALWAYS);
        }
    }

    public static void main(String[] args) {
        System.out.println(ScheduleCalc.bob);
        ScheduleHelper.launch();
    }

}