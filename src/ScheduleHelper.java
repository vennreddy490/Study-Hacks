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
        HBox hbox = new HBox(7.5);
        addChildrenToHBox(hbox);

        Button load = new Button("Load Schedule");
        load.setAlignment(Pos.BOTTOM_RIGHT);
        loadEvent(load);
        addButtons();

        root.getChildren().add(hbox);
        root.getChildren().add(load);

        // Resizing Limits
        timeSlotGrows();

        // Initializing the Scene and displaying it
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMinHeight(400);
        stage.setMinWidth(905);
        // stage.setMaxWidth(1200);
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

    public void addButtons() {
        for (TimeSlot day : daysOfWeek) {
            Button add = new Button("Add");
            Button remove = new Button("Remove");
            setButtonProperties(add);
            setButtonProperties(remove);

            HBox buttonWrap = new HBox(add, remove);
            buttonWrap.setAlignment(Pos.CENTER);
            day.getChildren().add(buttonWrap);

            add.setOnAction(e -> {
                if (numOfClasses.get(day) == 0) {
                    day.getChildren().remove(day.getChildren().size() - 1);
                    remove.setDisable(false);
                } // if
                day.getChildren().remove(day.getChildren().size() - 1);
                numOfClasses.put(day, numOfClasses.get(day) + 1);
                day.addNewClass(numOfClasses.get(day));
                day.getChildren().add(buttonWrap);
            }); // setOnAction
            remove.setOnAction(e -> {
                day.getChildren().remove(day.getChildren().size() - 2);
                day.getChildren().remove(day.getChildren().size() - 2);
                numOfClasses.put(day, numOfClasses.get(day) - 1);
                if (day.getChildren().size() == 2) {
                    Text noClasses = new Text("No Classes Today");
                    day.getChildren().remove(day.getChildren().size() - 1);
                    day.getChildren().addAll(noClasses, buttonWrap);
                    remove.setDisable(true);
                } // if
            }); // setOnAction
        } // for
    } // addAddButton

    public void setButtonProperties(Button button) {
        HBox.setHgrow(button, Priority.ALWAYS);
        button.setMinWidth(60);
        button.setMaxWidth(Double.MAX_VALUE);
    } // setButtonProperties

    public void loadEvent(Button load) {
        load.setOnAction(e -> {
            for (TimeSlot day : daysOfWeek) {
                ScheduleCalc newSchedule = new ScheduleCalc();
                if (day.getChildren().size() > 3) {
                    for (int i = 2; i < day.getChildren().size(); i += 2) {
                        String start = getTextFromTextField(day, i, 0);
                        String end = getTextFromTextField(day, i, 2);
                        int startIndex = newSchedule.convertTimeToMinutes(start);
                        int endIndex = newSchedule.convertTimeToMinutes(end);
                        newSchedule.fillSchedule(startIndex, endIndex);
                    } // for
                } // if
            } // for
        }); // setOnAction
    } // loadEvent

    public String getTextFromTextField(TimeSlot day, int listIndex, int fieldIndex) {
        HBox timeWrapper = (HBox) day.getChildren().get(listIndex);
        TextField field = (TextField) timeWrapper.getChildren().get(fieldIndex);
        return field.getText();
    } // getTextFieldFromHBox

    public static void main(String[] args) {
        Application.launch();
    }

}