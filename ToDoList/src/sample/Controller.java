package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import sample.dataModel.ToDoData;
import sample.dataModel.ToDoItem;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class Controller {
    private List<ToDoItem> toDoItems;

    @FXML
    private ListView<ToDoItem> todoListView;
    @FXML
    private TextArea textAreaView;
    @FXML
    private Label deadlineLabel;
    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private ContextMenu listContextMenu;
    @FXML
    private ToggleButton filterToggleBtn;

    private FilteredList<ToDoItem> filteredList;

    public void initialize() {
//        ToDoItem item1 = new ToDoItem("Mail birthday card", "Buy a 70th birthday card for Hazel",
//                LocalDate.of(2020, Month.DECEMBER, 28));
//        ToDoItem item2 = new ToDoItem("Doctor's appointment", "Go for annual checkup at Clinic",
//                LocalDate.of(2020, Month.DECEMBER, 21));
//        ToDoItem item3 = new ToDoItem("Finish design proposal for client", "Email mock-up of site to christel",
//                LocalDate.of(2021, Month.JANUARY, 14));
//        ToDoItem item4 = new ToDoItem("Pickup Jane from airport", "Jane arriving at OR around 17:00 at gate B.",
//                LocalDate.of(2021, Month.MARCH, 4));
//        ToDoItem item5 = new ToDoItem("Look for christmas gifts", "Find a nice gift for Grant.",
//                LocalDate.of(2020, Month.NOVEMBER, 20));
//
//        toDoItems = new ArrayList<>();
//        toDoItems.add(item1);
//        toDoItems.add(item2);
//        toDoItems.add(item3);
//        toDoItems.add(item4);
//        toDoItems.add(item5);
//
//        // add initial item data to file to be loaded
//        ToDoData.getInstance().setToDoItems(toDoItems);

        // init the context menu that will give option to delete item
        listContextMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Delete");
        listContextMenu.getItems().addAll(deleteMenuItem);
        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ToDoItem item = todoListView.getSelectionModel().getSelectedItem();
                deleteItem(item);
            }
        });

        // display first item details when app starts
        todoListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends ToDoItem> observableVal, ToDoItem oldVal, ToDoItem newVal) {
                if (newVal != null) {
                    ToDoItem item = todoListView.getSelectionModel().getSelectedItem();
                    textAreaView.setText(item.getDetails());
                    // formatting the date
                    DateTimeFormatter df = DateTimeFormatter.ofPattern("MMMM d, yyyy");
//                    deadlineLabel.setText(item.getDeadline().toString());
                    deadlineLabel.setText(df.format(item.getDeadline()));
                }
            }
        });

        // to populate the list view
//        todoListView.getItems().setAll(toDoItems);
//        todoListView.getItems().setAll(ToDoData.getInstance().getToDoItems());
        filteredList = new FilteredList<>(ToDoData.getInstance().getToDoItems(), new Predicate<>() {
            @Override
            public boolean test(ToDoItem toDoItem) {
                return true;
            }
        });
        SortedList<ToDoItem> sortedList = new SortedList<>(filteredList, new Comparator<>() {
            @Override
            public int compare(ToDoItem o1, ToDoItem o2) {
                return o1.getDeadline().compareTo(o2.getDeadline());
            }
        });
//        SortedList<ToDoItem> sortedList = new SortedList<>(ToDoData.getInstance().getToDoItems(), new Comparator<ToDoItem>() {
//            @Override
//            public int compare(ToDoItem o1, ToDoItem o2) {
//                return o1.getDeadline().compareTo(o2.getDeadline());
//            }
//        }); // will sort items by due date

        todoListView.setItems(sortedList);
//        todoListView.setItems(ToDoData.getInstance().getToDoItems()); // can just do this now to bind with observable
        // set this so user can only select one item at a time
        todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // have first item selected when app starts
        todoListView.getSelectionModel().selectFirst();

        // creating a cell factory to customize colors and insert text/data etc
        todoListView.setCellFactory(new Callback<ListView<ToDoItem>, ListCell<ToDoItem>>() {
            @Override
            public ListCell<ToDoItem> call(ListView<ToDoItem> toDoItemListView) {
                ListCell<ToDoItem> cell = new ListCell<>() {
                    @Override
                    protected void updateItem(ToDoItem toDoItem, boolean empty) {
                        super.updateItem(toDoItem, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(toDoItem.getShortDesc());
                            if (toDoItem.getDeadline().isBefore(LocalDate.now().plusDays(1))) {
                                setTextFill(Color.RED);
                            } else if (toDoItem.getDeadline().equals(LocalDate.now().plusDays(1))) {
                                setTextFill(Color.BROWN);
                            }
                        }
                    }
                };

                cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                    if (isNowEmpty) {
                        cell.setContextMenu(null);
                    } else {
                        cell.setContextMenu(listContextMenu);
                    }
                });

                return cell;
            }
        });
    }

//    @FXML
//    public void handleClickListView() {
//        // get the item that is currently selected
//        ToDoItem item = todoListView.getSelectionModel().getSelectedItem();
////        System.out.println("The selected item is " + item);
//
//        // show the long desc of item in the text area
////        textAreaView.setText(item.getDetails());
//
//        // add a due date with string
//        String sb = item.getDetails() + "\n\n\n\n" +
//                "Due: " +
//                item.getDeadline().toString();
//        textAreaView.setText(sb);
//    }

    // hard coded a method in init to check for changes so can remove below
//    @FXML
//    public void handleClickListView() {
//        ToDoItem item = todoListView.getSelectionModel().getSelectedItem();
//        textAreaView.setText(item.getDetails());
//        deadlineLabel.setText(item.getDeadline().toString());
//    }

    // have to add code to show dialog in here and not in the dialog controller class
    // since the dialog will pop up when clicking on the menu which is in main control class
    @FXML
    public void showNewItemDialog() {
        // create an instance of a dialog
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Add new ToDo item");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("toDoItemDialog.fxml"));
        try {
//            Parent root = FXMLLoader.load(getClass().getResource("toDoItemDialog.fxml"));
//            dialog.getDialogPane().setContent(root);
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
        }

        // add pre-made buttons
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
//            System.out.println("OK pressed");
            DialogController controller = fxmlLoader.getController();
//            controller.processResults();
            ToDoItem newItem = controller.processResults();
            // do this to show new to-do item after clicking ok
            // can leave below out cause now using data binding with observable
//            todoListView.getItems().setAll(ToDoData.getInstance().getToDoItems());
            // make sure the new item is selected
            todoListView.getSelectionModel().select(newItem);
        }
    }

    @FXML
    public void deleteItem(ToDoItem item) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete ToDo item");
        alert.setHeaderText("Delete item: " + item.getShortDesc());
        alert.setContentText("Are your sure? Press OK to confirm, or cancel to back out.");
        // don't have to add buttons, the alert adds them automatically
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            ToDoData.getInstance().deleteToDoItem(item);
        }
    }

    @FXML
    public void handleKeyPressed(KeyEvent event) {
        ToDoItem selectedItem = todoListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            if (event.getCode().equals(KeyCode.DELETE)) {
                deleteItem(selectedItem);
            }
        }
    }

    @FXML
    public void handleFilterBtn() {
        ToDoItem selectedItem = todoListView.getSelectionModel().getSelectedItem();
        if (filterToggleBtn.isSelected()) {
            filteredList.setPredicate(new Predicate<ToDoItem>() {
                @Override
                public boolean test(ToDoItem toDoItem) {
                    return toDoItem.getDeadline().equals(LocalDate.now());
                }
            });
            if (filteredList.isEmpty()) {
                textAreaView.clear();
                deadlineLabel.setText("");
            } else if (filteredList.contains(selectedItem)){
                todoListView.getSelectionModel().select(selectedItem);
            } else {
                todoListView.getSelectionModel().selectFirst();
            }
        } else {
            filteredList.setPredicate(new Predicate<ToDoItem>() {
                @Override
                public boolean test(ToDoItem toDoItem) {
                    return true;
                }
            });
            todoListView.getSelectionModel().select(selectedItem);
        }
    }
}
