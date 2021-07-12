/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isd;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javax.swing.ImageIcon;
import javax.swing.filechooser.FileSystemView;

/**
 * FXML Controller class
 *
 * @author Raju
 */
public class Fx1Controller implements Initializable {
    ISD main;
    int flag=0;
    private final ObservableList<folder> list = FXCollections.observableArrayList();
    TableColumn T_Images = new TableColumn<folder, ImageView>("Icon");
    TableColumn T_Size = new TableColumn("Size");
    TableColumn T_name = new TableColumn("Name");
    TableColumn T_date = new TableColumn("Data Modified");
    @FXML
    private TreeView<File> tree;
    @FXML
    private TableView<folder> table;
    @FXML
    private TextField text;
    @FXML
    private Button button;
    @FXML
    private TilePane tile;

    /**
     * Initializes the controller class.
     */
    @Override
public void initialize(URL url, ResourceBundle rb) {
    File currentDir = new File("F:\\Education"); // current directory
    findFiles(currentDir,null);
    T_Images.setMinWidth(100);
    T_Images.setCellValueFactory(new PropertyValueFactory<folder, ImageView>("Image"));
    T_Size.setMinWidth(50);
    T_Size.setCellValueFactory(new PropertyValueFactory<folder, String>("Size"));
    T_name.setMinWidth(149);
    T_name.setCellValueFactory(new PropertyValueFactory<folder, String>("Name"));
    T_date.setMinWidth(120);
    T_date.setCellValueFactory(new PropertyValueFactory<folder, String>("Date"));
   
    ImageView picture= swingImgToFxImg(currentDir);
    ImageView picture2= swingImgToFxImg(currentDir);
    
    Label labeleWithImage = createLabledImage(picture,currentDir.getName());
               
        labeleWithImage.setMaxWidth(80);
        final String msg=currentDir.getPath();
        labeleWithImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
             public void handle(MouseEvent t) {
                    File folder=new File(msg);
                    ListUpdate(folder);
                    text.setText(msg);
             }           
        });
        tile.getChildren().add(labeleWithImage);
        
    Label icon = new Label();
    icon.setGraphic(picture2);
    VBox box =new VBox();
    box.getChildren().addAll(icon);
    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
    list.add(new folder(currentDir.getName(),sdf.format(currentDir.lastModified()) ,(currentDir.length())/1024,currentDir.getPath(),box));
    table.setItems(list);
    table.getColumns().addAll(T_Images,T_name,T_Size, T_date);
    text.setText("E:");
    
}
    public ImageView swingImgToFxImg(File folder){
        ImageIcon icn = (ImageIcon) FileSystemView.getFileSystemView().getSystemIcon(folder);
        java.awt.Image img = icn.getImage();
        BufferedImage bImg ;
        if (img instanceof BufferedImage) {
            bImg = (BufferedImage) img ;
        } else {
            bImg = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = bImg.createGraphics();
            graphics.drawImage(img, 0, 0, null);
            graphics.dispose();
        }
        Image fxImage = SwingFXUtils.toFXImage(bImg, null);
        
        ImageView icon=new ImageView();
        icon.setImage(fxImage);
        return  icon;
    }
    

private void findFiles(File dir, TreeItem<File> parent) {
    TreeItem<File> root = new TreeItem<>(dir);
    root.setExpanded(true);
    try {
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                System.out.println("directory:" + file.getCanonicalPath());
                findFiles(file,root);
            } else {
                System.out.println("     file:" + file.getCanonicalPath());
                root.getChildren().add(new TreeItem<>(file));
            }

        }
        if(parent==null){
            tree.setRoot(root);
        } else {
            parent.getChildren().add(root);
        }
    } catch (IOException e) {
       e.printStackTrace();
    }
}
public void setMain(ISD ob) {
    main=ob;
}

    /**
     *
     * @param path
     */
 public void ListUpdate(File path){
        list.clear();
        tile.getChildren().clear();
        if (path.isDirectory()){
            File[] listOfFiles = path.listFiles();
            SimpleDateFormat date_format = new SimpleDateFormat("MM-dd-yyyy");

            for (int i = 0; i < listOfFiles.length; i++) {
 
                ImageView picture;
                ImageView picture2;
          
             picture= swingImgToFxImg(listOfFiles[i]);
             picture2= swingImgToFxImg(listOfFiles[i]);
            
                
                Label icon = new Label();
                icon.setGraphic(picture);
                VBox box =new VBox();
                box.getChildren().addAll(icon);
                list.add(new folder(listOfFiles[i].getName(),date_format.format(listOfFiles[i].lastModified()) ,(listOfFiles[i].length())/1024,listOfFiles[i].getPath(),box));
                
                Label labeleWithImage = createLabledImage(picture2,listOfFiles[i].getName());
                labeleWithImage.setMaxWidth(80);
                final String msg=listOfFiles[i].getPath();
                labeleWithImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                 public void handle(MouseEvent t) {
                        File folder=new File(msg);
                        ListUpdate(folder);
                }           
                 });
                tile.getChildren().add(labeleWithImage);
            }
        }
    }
 
    private Label createLabledImage(ImageView imageView, String caption) {
        Label labeledImage = new Label(caption);
        labeledImage.setGraphic(imageView);
        labeledImage.setContentDisplay(ContentDisplay.TOP);

        return labeledImage;
    }
    @FXML
    private void view(MouseEvent event) {
     folder path=table.getSelectionModel().getSelectedItem();
     File directory=new File(path.getPath());
     ListUpdate(directory);
     table.setItems(list);
     text.setText(path.getPath());
      
    }

    @FXML
    private void TreeClick(MouseEvent event) {
        TreeItem<File> selectedItem = tree.getSelectionModel().getSelectedItem();
        ListUpdate(selectedItem.getValue());
        table.setItems(list);
        text.setText(selectedItem.getValue().getPath());
    }
    
    @FXML
    private void ViewChange(ActionEvent event) {
        if(flag==0){
           table.setVisible(false);
           tile.setVisible(true);
            
           flag=1;
        }
        else{
            table.setVisible(true);
            tile.setVisible(false);
            flag=0;
        }
    }
}
    

