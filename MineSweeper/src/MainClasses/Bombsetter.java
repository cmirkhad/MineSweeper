package MainClasses;
int ab = 0;
public class Bombsetter {
    int[][] array;
    int row,column;
    private int BombNumbers=0;
    private double bombspercentage =0;
    public Bombsetter(int row, int column, double bombspercentage) {
        array=new int[row][column];
        this.row=row;
        this.column=column;
        this.bombspercentage = bombspercentage;
        setBombs();
        setnumbersAroundBombs();
    }
    private void setBombs(){
        for (int row = 0; row <array.length ; row++) {
            for (int column = 0; column < array[0].length; column++) {
                if(Math.random()< bombspercentage) {
                    array[row][column]=-2;
                    BombNumbers++;
                }
            }
        }
    }
    private void setnumbersAroundBombs(){
        for (int row = 0; row <array.length ; row++) {
            for (int column = 0; column < array[0].length; column++) {
                if( array[row][column]==-2){
                    for (int i = -1; i <=1 ; i++) {
                        for (int j = -1; j <=1 ; j++) {
                            try{
                                if(j!=0 | i!=0){
                                    if(array[row+i][column+j]!=-2) {
                                        array[row+i][column+j]+=1;
                                    }
                                }
                            }
                            catch(Exception e){
                                continue;

                            }

                        }
                    }
                }
            }
        }
    }
    public int[][] getArray(){
        return array;
    }

    public void showArray(){
        for (int[] row : array) {
            for (int field : row) System.out.print(field+" ");
            System.out.println();
        }
    }
    public int getBombNumbers(){
        return BombNumbers;
    }
    public void setNewArray(double bombsperc){
        BombNumbers=0;
        array=new int[row][column];
        this.bombspercentage =bombsperc;
        setBombs();
        setnumbersAroundBombs();
    }

}
