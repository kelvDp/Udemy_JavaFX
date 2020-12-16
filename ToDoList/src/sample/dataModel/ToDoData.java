package sample.dataModel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

// create a singleton class that will store the data from the list
public class ToDoData {
    private static final ToDoData instance = new ToDoData();
    private static final String fileName = "ToDoListItems.txt";
    // will use an observable list instead of a normal list so that the app updates itself automatically
    // and to make data binding possible
//    private List<ToDoItem> toDoItems;
    private ObservableList<ToDoItem> toDoItems;
    private final DateTimeFormatter formatter;

    private ToDoData() {
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }

    public static ToDoData getInstance() {
        return instance;
    }

    //    public List<ToDoItem> getToDoItems() {
//        return toDoItems;
//    }
    public ObservableList<ToDoItem> getToDoItems() {
        return toDoItems;
    }

    public void addToDoItem(ToDoItem item) {
        toDoItems.add(item);
    }

    // temporary method to get initial data
//    public void setToDoItems(List<ToDoItem> toDoItems) {
//        this.toDoItems = toDoItems;
//    }

    public void loadToDoItems() {
        toDoItems = FXCollections.observableArrayList(); // fx needs a list in an obsarraylist format
        Path path = Paths.get(fileName);
        String input;

        try (BufferedReader br = Files.newBufferedReader(path)) {
            while ((input = br.readLine()) != null) {
                String[] itemPieces = input.split("\t");
                String shortDesc = itemPieces[0];
                String details = itemPieces[1];
                String dateString = itemPieces[2];

                LocalDate date = LocalDate.parse(dateString, formatter);

                ToDoItem toDoItem = new ToDoItem(shortDesc, details, date);
                toDoItems.add(toDoItem);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void storeToDoItems() {
        Path path = Paths.get(fileName);

        try (BufferedWriter bw = Files.newBufferedWriter(path)) {
            Iterator<ToDoItem> iter = toDoItems.iterator();
            while (iter.hasNext()) {
                ToDoItem item = iter.next();
                bw.write(String.format("%s\t%s\t%s",
                        item.getShortDesc(),
                        item.getDetails(),
                        item.getDeadline().format(formatter)));

                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteToDoItem(ToDoItem item) {
        toDoItems.remove(item);
    }
}
