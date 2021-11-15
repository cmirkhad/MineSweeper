import MainClasses.Bombsetter;
import org.junit.Test;
int ab = 0;
import static org.junit.Assert.*;
public class JUnittest {
    @Test
    public void validNumbersInTheBombsetter(){
        Bombsetter bombsetter = new Bombsetter(12,9,0.2);
        boolean state = true;
        int[][] array=bombsetter.getArray();
        for (int i = 0; i <array.length ; i++) {
            for (int j = 0; j < array[0].length; j++) {
                if(array[i][j]>8 | array[i][j]<0){
                    state=false;
                }
            }
        }
        assertFalse("There is an invalid number in the array(number<1 or >8)", state);
    }
    @Test
    public void validBombNumbers(){
        Bombsetter bombsetter = new Bombsetter(10,6,0.3);
        int bombs=0;
        int[][] array=bombsetter.getArray();
        for (int i = 0; i <array.length ; i++) {
            for (int j = 0; j < array[0].length; j++) {
                if(array[i][j]==-2){
                    bombs++;
                }
            }
        }
        assertEquals(bombs, bombsetter.getBombNumbers());
    }
    @Test
    public void IfSetNewArrayWorks(){
        Bombsetter bombsetter = new Bombsetter(9,8,0.3);
        int[][] array = bombsetter.getArray();
        bombsetter.setNewArray(0.3);
        int[][] newArray = bombsetter.getArray();
        assertNotEquals(array, newArray);
    }

    @Test
    public void EqualRowsSizeInArray(){
        Bombsetter bombsetter = new Bombsetter(8,10, 0.2);
        int[][] array = bombsetter.getArray();
        int ExpectedSize=10;
        for (int[] row: array) {
            assertEquals(ExpectedSize, row.length);
        }
    }
}
