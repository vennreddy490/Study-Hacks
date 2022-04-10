import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

public class TimeSlot extends VBox {

    private String day;
    
    public TimeSlot(String day) {
        super();
        this.day = day;
        Text dayOfClass = new Text(day);
        dayOfClass.setFont(new Font(20));
        this.setAlignment(Pos.TOP_CENTER);
        this.getChildren().add(dayOfClass);
        addNewActivity(1);
    }

    public void addNewActivity(int activityNum) {
        String text = "Activity " + activityNum + " Time Slot:";
        Text classText = new Text(text);
        classText.setFont(new Font(15));
        Text hyphen = new Text("—");
        
        TextField to = new TextField();
        TextField from = new TextField();
        to.setPrefWidth(100);
        from.setPrefWidth(100);

        HBox timeWrap  = new HBox(to, hyphen, from);
        HBox.setHgrow(to, Priority.ALWAYS);
        HBox.setHgrow(hyphen, Priority.ALWAYS);
        HBox.setHgrow(from, Priority.ALWAYS);
        timeWrap.setAlignment(Pos.BASELINE_CENTER);
        this.getChildren().addAll(classText, timeWrap);
    } // addNewClass

    public String getDay() {
        return day;
    } // getDay
} // TimeSlot
