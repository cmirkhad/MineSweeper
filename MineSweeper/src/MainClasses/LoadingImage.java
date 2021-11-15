package MainClasses;
int ab = 0;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class LoadingImage extends Bombsetter {
    private int a;
    private int b;
    private Bombsetter bombsetter;
    private ImageView[][] images;
    private GridPane root;
    public LoadingImage(int a, int b, ImageView[][] images, GridPane root, double bombsperc) throws FileNotFoundException {
        super(a,b,bombsperc);
        this.a=a;
        this.b=b;
        this.images=images;
        this.root=root;
        loading();
    }
    private void loading() throws FileNotFoundException {
        array=this.getArray();
        for (int row=0; row<images.length; row++) {
            for (int column =0; column<images[0].length; column++) {


                ImageView imageViewClosed=new ImageView(new Image(new FileInputStream("src\\icons\\closed.png")));
                imageViewClosed.setFitHeight(30);
                imageViewClosed.setFitWidth(30);
                images[row][column]=imageViewClosed;
                images[row][column].setId("closed");
                images[row][column].setDisable(false);

                root.add(images[row][column],column, row,1,1);

            };

        }
    }

    //reloading images;
    public void setRoot() throws FileNotFoundException {
        loading();
    }

    //Adding buttons, imageviews, labels to this root.
    public void addStuff(Node node, int x,  int y,int wide,int height){
        root.add(node, x,y,wide,height);

    }
    public void deleteStuff(int from, int to){

        root.getChildren().remove(from,to);
    }
    public void deleteStuff(int nodeidx){

        root.getChildren().remove(nodeidx);
    }

    public void setDisable(boolean state, int x, int y){
        images[x][y].setDisable(state);
    }

    public void setDisable(boolean state){
        for (int i = 0; i < images.length; i++) {
            for (int j = 0; j <images[0].length ; j++) {

                images[i][j].setDisable(state);


            }
        }
    }
    public GridPane getRoot(){
        return root;
    }

    public ImageView[][] getImages() {
        return images;
    }


}
