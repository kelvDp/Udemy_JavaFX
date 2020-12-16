package sample;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.dataModel.ToDoData;
import sample.dataModel.ToDoItem;
import java.time.LocalDate;

public class DialogController {

    @FXML
    private TextField shortDesc;
    @FXML
    private TextArea details;
    @FXML
    private DatePicker deadline;

    @FXML
    public ToDoItem processResults() {
        String shortDescription = shortDesc.getText().trim();
        String detailArea = details.getText().trim();
        LocalDate date = deadline.getValue();

        // use singleton to add to the list
//        ToDoData.getInstance().addToDoItem(new ToDoItem(shortDescription, detailArea, date));

        // return the last item added to the list so that it is checked
        ToDoItem newItem = new ToDoItem(shortDescription, detailArea, date);
        ToDoData.getInstance().addToDoItem(newItem);

        return newItem;
    }
}
