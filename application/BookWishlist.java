package application;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
public class BookWishlist extends Application {
    List<String> wishlist = new ArrayList<>();  
    ListView<String> lv = new ListView<>();
    public void start(Stage primaryStage) {
        FileChooser fc = new FileChooser();
        MenuBar mb = new MenuBar();
        Menu f = new Menu("File");
        MenuItem n = new MenuItem("New");
        n.setOnAction(event -> {
            wishlist.clear();
            lv.getItems().clear();
            lv.getItems().addAll(wishlist);
        });    
        MenuItem s = new MenuItem("Save");
        s.setOnAction(event -> {
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            fc.setInitialFileName("wishlist.txt");
            File file = fc.showSaveDialog(primaryStage);
            if (file != null) {
                savebooks(file, wishlist);
            }
        });
        Menu exit = new Menu("Exit");
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(event -> System.exit(0));

        f.getItems().addAll(n, s);
        exit.getItems().add(exitItem);
        mb.getMenus().addAll(f, exit);
        lv.setPrefHeight(200);
        lv.setStyle("-fx-background-radius: 8; -fx-border-radius: 8;");
        lv.setStyle("-fx-background-color: #ffffff;");
        TextField title = new TextField();
        title.setPromptText("Book Title");
        TextField author = new TextField();
        author.setPromptText("Author");
        TextField genre = new TextField();
        genre.setPromptText("Genre");
        Button add = new Button("Add Book");
        add.setStyle("-fx-background-color: #42a5f5; -fx-text-fill: white; -fx-background-radius: 8;");
        add.setOnAction(event -> {
            String t = title.getText();
            String a = author.getText();
            String g = genre.getText();
            if (!t.isEmpty() && !a.isEmpty() && !g.isEmpty()) {
                String newBook = t + " by " + a + " (" + g + ") [Not Read]";
                wishlist.add(newBook);
                lv.getItems().clear();
                lv.getItems().addAll(wishlist);
                title.clear();
                author.clear();
                genre.clear();
            }
        });
        Button remove = new Button("Remove Book");
        remove.setStyle("-fx-background-color: #ef5350; -fx-text-fill: white; -fx-background-radius: 8;");
        remove.setOnAction(event -> {
            String selectedBook = lv.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                wishlist.remove(selectedBook);
                lv.getItems().clear();
                lv.getItems().addAll(wishlist); 
            }
        });
        Button read = new Button("Mark as Read");
        read.setStyle("-fx-background-color: #66bb6a; -fx-text-fill: white; -fx-background-radius: 8;");
        read.setOnAction(event -> {
            String selectedBook = lv.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                String updatedBook = selectedBook.replace("[Not Read]", "[Read]"); 
                wishlist.set(wishlist.indexOf(selectedBook), updatedBook); 
                lv.getItems().clear();
                lv.getItems().addAll(wishlist);
            }
        });
        VBox fl = new VBox(10, title, author, genre, add);
        fl.setAlignment(Pos.CENTER);
        HBox bl = new HBox(10, remove, read);
        bl.setAlignment(Pos.CENTER);
        VBox ml = new VBox(15, fl, lv, bl);
        ml.setMaxWidth(450);
        ml.setAlignment(Pos.TOP_CENTER);
        ml.setPadding(new javafx.geometry.Insets(20));
        ml.setStyle("-fx-background-color:#E8F5E9;");
        BorderPane l = new BorderPane();
        l.setTop(mb);
        l.setCenter(ml);
        Scene sc = new Scene(l, 500, 500);
        sc.getRoot().setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 14px;");

        primaryStage.setTitle("BOOK WISHLIST");
        primaryStage.setScene(sc);
        primaryStage.show();
    }
    void savebooks(File file, List<String> books) {
        try (FileWriter fw = new FileWriter(file)) {
            fw.write("Book Wishlist\n");
            fw.write("====================\n\n");
            for (int i = 0; i < books.size(); i++) {
                String book = books.get(i);
                String[] parts = book.split(" by | \\(|\\) |\\[|\\]");
                fw.write("Title: " + parts[0] + "\n");
                fw.write("Author: " + parts[1] + "\n");
                fw.write("Genre: " + parts[2] + "\n");
                fw.write("Status: " + (book.contains("[Read]") ? "Read" : "Not Read") + "\n");
                fw.write("-------------------------\n");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}

