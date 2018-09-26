import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CrossWord {


    public int size ;
    private char [][]board;
    private Scanner myscan;

    public CrossWord (Scanner myscan){ // init board
        this.myscan = myscan;
        this.size = getBoardSize( myscan);
        board = new char [size][size]; // create board
        buildBoard(board);
    }

//yyyy
    private int getBoardSize(Scanner myscan){
        try {
            return myscan.nextInt();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("ERROR -1: FILE NOT START WITH INTEGER");
        return -1;
    }
    private void buildBoard (char board [] []){
        myscan.nextLine(); // skip the first line
        StringBuilder str = new StringBuilder();
        while (myscan.hasNext()){
            str.append(myscan.next());
        }
        int x = 0;
        for (int i = 0 ; i < size ; i ++) {
            for (int j = 0 ; j < size ; j ++){
                board[i][j]=  str.charAt(x);
                x++;
            }
        }
    }

    public void printCrossWord (){
        StringBuilder str = new StringBuilder();
        for (int i = 0 ; i <size ; i++){
            for (int j = 0 ; j < size ; j++){
                str.append(board[i][j]);
            }
            System.out.println(str);
            str.delete(0, str.length());
        }
    }

    public ArrayList<Integer> scanBoard(){ // return an array contains the number of words length needed to solve the crossword
        ArrayList <Integer> words_sizes  = new ArrayList();
        words_sizes.add(size);
        boolean prev_block = false ; // if there is a previouis block example +++-- and we are checking the index 4
        int lastIndexUpdate = 0 ; // to keep update the last word length for example +++-+++ after we count the first part the second part should be 3
        for (int i = 0 ;i < size ; i ++){
            for(int j = 0 ; j < size ; j ++){ // the length we need to return is > = 2
                if(board[i][j] == '+') prev_block = false; // there is no prev block
                if (board[i][j] == '-' && prev_block ==false ) { // check the word length for example if one raw has +++-++++ this will return 3 to the list and there is no '-' before it
                    prev_block = true;
                    if ((j + 1) > 1) {
                        words_sizes.add((j - lastIndexUpdate) +1 ); // if the length of the first word is greater than 2  :example +++--
                        lastIndexUpdate = j;
                        if ((j + 1) != size && (j + 1) > 1) { // check the possible ramming word length in a raw
                            //  check the length at the bottom of the - symbol
                            int len = 1;
                            for (int k = i; k < size; k++) {
                                if (board[k][j] == '-') {
                                    if (len <= 1) break;
                                }
                                len++;
                            }
                            words_sizes.add(len);
                        }
                    }
                }
            }
        }
        return words_sizes ;
    }

}
