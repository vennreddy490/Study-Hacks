import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;

public class TimeSlot extends VBox {
    
    public TimeSlot(String day) {
        super();
        Text dayOfClass = new Text(day);
        Text c1 = new Text("Class 1 Time Slot:");
        TextField c1Entry = new TextField();
        this.setAlignment(Pos.TOP_CENTER);
        this.getChildren().addAll(dayOfClass, c1, c1Entry);
    }

}
