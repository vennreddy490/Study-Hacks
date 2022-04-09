import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.HashMap;

public class ScheduleHelper extends Application {

    TimeSlot[] daysOfWeek;
    HashMap<TimeSlot, Integer> numOfClasses;

    public ScheduleHelper() {
        daysOfWeek = new TimeSlot[] {
                new TimeSlot("Sunday"),
                new TimeSlot("Monday"),
                new TimeSlot("Tuesday"),
                new TimeSlot("Wednesday"),
                new TimeSlot("Thursday"),
                new TimeSlot("Friday"),
                new TimeSlot("Saturday")
        };
        numOfClasses = new HashMap<>();
        initializeHashMap();
    } // Constructor

    public void initializeHashMap() {
        for (TimeSlot day : daysOfWeek) {
            numOfClasses.put(day, 1);
        } // for
    } // initializeHashMap

    @Override
    public void start(Stage stage) {

        // Adding days and coloumns
        VBox root = new VBox();
        HBox hbox = new HBox();
        addChildrenToHBox(hbox);

        Button load = new Button("Load Schedule");
        load.setAlignment(Pos.BOTTOM_RIGHT);

        root.getChildren().add(hbox);
        root.getChildren().add(load);

        // Resizing Limits
        timeSlotGrows();

        addAddButton();

        // Initializing the Scene and displaying it
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMinHeight(400);
        stage.show();
        stage.setTitle("Schedule Helper");

    } // start

    public void addChildrenToHBox(HBox hbox) {
        for (TimeSlot day : daysOfWeek) {
            hbox.getChildren().add(day);
        } // for
    } // addChildrenToHBox

    public void timeSlotGrows() {
        for (TimeSlot day : daysOfWeek) {
            HBox.setHgrow(day, Priority.ALWAYS);
        } // for
    } // timeSlotGrows

    public void addAddButton() {
        for (TimeSlot day : daysOfWeek) {
            Button add = new Button("Add Class");
            day.getChildren().add(add);
            add.setOnAction(e -> {
                day.getChildren().remove(day.getChildren().size() - 1);
                numOfClasses.put(day, numOfClasses.get(day) + 1);
                Text className = new Text("Class " + numOfClasses.get(day) + " Time Slot:");
                TextField field = new TextField();
                day.getChildren().addAll(className, field, add);
            });
        } // for
    } // addAddButton

    public void newClass(TimeSlot day, int dayClassNum) {
        Text addedClass = new Text("Class " + (dayClassNum + 1) + " Time Slot:");
        TextField newClassTime = new TextField();
        day.getChildren().addAll(addedClass, newClassTime);
    }

    public static void main(String[] args) {
        Application.launch();
    }

}