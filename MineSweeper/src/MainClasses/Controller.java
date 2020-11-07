package MainClasses;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Controller {
    int a,b;
    private LoadingImage loadingImage;
    private Label winOrLostText = new Label();
    private Image imageflag = new Image(new FileInputStream("src\\icons\\flag.png"));
    private Image imageBomb = new Image(new FileInputStream("src\\icons\\bomb.png"));
    private Image closedimage = new Image(new FileInputStream("src\\icons\\closed.png"));
    private int numberFlaged;

    @FXML
    private Button ExtraHardButton;

    @FXML
    private Button HardButton;

    @FXML
    private Button ExitButton;


    @FXML
    private Button MediumButton;

    @FXML
    private Button EasyButton;
    @FXML
    private Label MainText;

    public Controller() throws FileNotFoundException {
    }
    void actionGame(int rows,int columns, double bombsperc) throws FileNotFoundException {


        Stage game = new Stage();

        a=rows;
        b=columns;

        GridPane root = new GridPane();
        ImageView[][] closedimages = new ImageView[a][b];


        loadingImage = new LoadingImage(a,b, closedimages, root, bombsperc);
        loadingImage.showArray();
        Button restartbtn = new Button("Restart");
        Button menubtn = new Button("Menu");
        Label numflagedtext = new Label();
        Label numBombstext = new Label();

        winOrLostText.setStyle("-fx-font-size: 15; -fx-font-weight: bold");

        loadingImage.addStuff(restartbtn, 0, b,3,1);
        loadingImage.addStuff(menubtn,3,b,3,1);
        loadingImage.addStuff(numflagedtext, a-3, b,3,1);
        loadingImage.addStuff(numBombstext, b-3, b+1,3,1);
        loadingImage.addStuff(winOrLostText, 0, b+1,7,2);


        restartbtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                MouseButton button = event.getButton();
                if(button==MouseButton.PRIMARY){
                    try {
                        loadingImage.setRoot();
                        loadingImage.setNewArray(bombsperc);
                        gameplay(numflagedtext, numBombstext );
                        loadingImage.showArray();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        menubtn.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent mouseEvent){
                openMenu();
                game.close();
            }
        });

        gameplay(numflagedtext,numBombstext);


        Scene scene = new Scene(root, 30*b, 30*a+45);
        game.setResizable(false);
        game.setScene(scene);


        game.setTitle("Game");
        game.show();
    }
    private void openMenu(){
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("Minesweeper");
        stage.setScene(new Scene(root, 300, 300));
        stage.setResizable(false);
        stage.show();

    }
    @FXML
    void Loadthegame(ActionEvent event) throws IOException {
        Stage stage=(Stage)((Button)event.getSource()).getScene().getWindow();
        int rows=1,columns=1;
        double bombsperc=0;
        if(((Button)event.getSource()).getId().equals(EasyButton.getId())){
            rows=8;
            columns=8;
            bombsperc=0.15;
        }
        else if(((Button)event.getSource()).getId().equals(MediumButton.getId())){
            rows=10;
            columns=14;
            bombsperc=0.15;
        }
        else if(((Button)event.getSource()).getId().equals(HardButton.getId())){
            rows=20;
            columns=20;
            bombsperc=0.18;
        }
        else if(((Button)event.getSource()).getId().equals(ExtraHardButton.getId())){
            rows=20;
            columns=25;
            bombsperc=0.22;
        }


        stage.close();
        actionGame(rows, columns, bombsperc);
    }

    @FXML
    void QuitAction(ActionEvent event) {
        Stage stage = (Stage) ExitButton.getScene().getWindow();
        stage.close();
    }


    private void gameplay(Label numflagedtext, Label numBombstext) throws FileNotFoundException {

        numberFlaged = 0;
        numflagedtext.setText("Flags: "+ numberFlaged);
        numBombstext.setText("Bombs: "+ loadingImage.getBombNumbers());
        winOrLostText.setText("");

        for (int  row= 0; row <loadingImage.getImages().length ; row++) {
            for (int column = 0; column < loadingImage.getImages()[0].length; column++) {
                int finalRow = row;
                int finalColumn = column;

                loadingImage.getImages()[row][column].setOnMouseClicked(new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent event) {
                        MouseButton button = event.getButton();
                        try {
                            if(button==MouseButton.PRIMARY){
                                if(loadingImage.getArray()[finalRow][finalColumn]!=-2)
                                    reveal(finalRow,finalColumn);
                                else{
                                    doFail();
                                }


                            }
                            else if (button==MouseButton.SECONDARY){
                                if(loadingImage.getImages()[finalRow][finalColumn].getId().equals("closed")){
                                    loadingImage.getImages()[finalRow][finalColumn].setImage(imageflag);
                                    loadingImage.getImages()[finalRow][finalColumn].setId("flag");
                                    //System.out.println(loadingImage.getImages()[finalRow][finalColumn].getId());
                                    numflagedtext.setText("Flags: "+(++numberFlaged));
                                }
                                else if(loadingImage.getImages()[finalRow][finalColumn].getId().equals("flag")){
                                    loadingImage.getImages()[finalRow][finalColumn].setImage(closedimage);
                                    loadingImage.getImages()[finalRow][finalColumn].setId("closed");

                                    numflagedtext.setText("Flags: "+(--numberFlaged));
                                }
                                checkwin();
                            }
                            else if (button==MouseButton.MIDDLE){
                                if(!loadingImage.getImages()[finalRow][finalColumn].getId().equals("closed") & !loadingImage.getImages()[finalRow][finalColumn].getId().equals("flag")){
                                    int flagsaround=0;
                                    for (int i = -1; i <=1 ; i++) {
                                        for (int j = -1; j <=1 ; j++) {
                                            if(i!=0 | j!=0){
                                                try{
                                                    if(loadingImage.getImages()[finalRow+i][finalColumn+j].getId().equals("flag")){
                                                        flagsaround++;
                                                    }
                                                }catch(Exception e){
                                                    continue;
                                                }

                                            }
                                        }
                                    }
                                    if(flagsaround>=Integer.parseInt(loadingImage.getImages()[finalRow][finalColumn].getId())){
                                        for (int i = -1; i <=1 ; i++) {
                                            for (int j = -1; j <=1 ; j++) {
                                                if(i!=0 | j!=0){
                                                    try{
                                                    if(!loadingImage.getImages()[finalRow+i][finalColumn+j].getId().equals("flag")){
                                                        reveal(finalRow+i,finalColumn+j);
                                                    }}catch(Exception e){
                                                    continue;
                                                }
                                                }
                                            }
                                        }
                                    }

                                }
                            }
                        } catch (FileNotFoundException | InterruptedException e) {
                            e.printStackTrace();
                        }


                    }
                });
            }
        }
    }



    private void doFail() throws InterruptedException {
        for (int i = 0; i < loadingImage.getImages().length; i++) {
            for (int j = 0; j <loadingImage.getImages()[0].length ; j++) {
                if(loadingImage.getArray()[i][j]==-2)loadingImage.getImages()[i][j].setImage(imageBomb);
                loadingImage.setDisable(true);
            }
        }

        winOrLostText.setText("End:(");
        winOrLostText.setStyle("-fx-text-fill: red ");

    }



    void reveal(int a,int b) throws FileNotFoundException, InterruptedException {
        if(loadingImage.getArray()[a][b]!=-2) {
            loadingImage.getImages()[a][b].setImage
                    (new Image(new FileInputStream("src\\icons\\icon" + loadingImage.getArray()[a][b] + ".png")));
            loadingImage.getImages()[a][b].setId(""+loadingImage.getArray()[a][b]);
            if(loadingImage.getArray()[a][b]==0){
                for (int i = -1; i <=1 ; i++) {
                    for (int j = -1; j <=1 ; j++) {
                        if(i!=0 | j!=0)
                            try{
                                if(loadingImage.getImages()[a+i][b+j].getId().equals("closed"))
                                    reveal(a+i, b+j);
                            }catch(Exception e){
                                continue;
                            }
                    }
                }


            }

        }
        else if(loadingImage.getArray()[a][b]==-2){
            doFail();

        }
        checkwin();
        return;
    }



    private void checkwin(){
        int checkclosed=0;
        for (int i = 0; i <a ; i++) {
            for (int j = 0; j <b ; j++) {

                //System.out.println(i+" "+j+" "+loadingImage.getImages()[i][j].getId());
                if(loadingImage.getImages()[i][j].getId().equals("closed")) checkclosed++;

            }
        }
        //System.out.println("Number of closed: "+checkclosed);
        if(checkclosed==0 & loadingImage.getBombNumbers()==numberFlaged){
            winOrLostText.setText("You Win;) Congrats!!!!XD\nYou can restart");
            winOrLostText.setStyle("-fx-text-fill: #00ff00");
            loadingImage.setDisable(true);

        }



    }



}

