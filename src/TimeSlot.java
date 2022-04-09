import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;

public class TimeSlot extends VBox {
    
    public TimeSlot(String day) {
        super();
        Text dayOfClass = new Text(day);
        Text c1 = new Text("Class 1 Time Slot:");
        TextField c1Entry = new TextField();
        Text c2 = new Text("Class 2 Time Slot:");
        TextField c2Entry = new TextField();
        Text c3 = new Text("Class 3 Time Slot:");
        TextField c3Entry = new TextField();
        Text c4 = new Text("Class 4 Time Slot:");
        TextField c4Entry = new TextField();
        Text c5 = new Text("Class 5 Time Slot:");
        TextField c5Entry = new TextField();
        Text c6 = new Text("Class 6 Time Slot:");
        TextField c6Entry = new TextField();
        Text c7 = new Text("Class 7 Time Slot:");
        TextField c7Entry = new TextField();
        this.getChildren().addAll(dayOfClass, c1, c1Entry, c2, c2Entry,
        c3, c3Entry, c4, c4Entry, c5, c5Entry, c6, c6Entry, c7, c7Entry);


    }

}
