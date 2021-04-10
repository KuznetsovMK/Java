package GB.lvl2lesson2;

public class MyArrayDataException extends RuntimeException {
    private int indexI;
    private int indexJ;
    private String value;

//    public int getIndexI() {
//        return indexI;
//    }
//
//    public int getIndexJ() {
//        return indexJ;
//    }
//
//    public String getValue() {
//        return value;
//    }

    public MyArrayDataException( int indexI, int indexJ,  String value) {
        super("Was found invalid element. position: [" + indexI + "][" +indexJ+ "] value: \"" + value + "\"");
        this.indexI = indexI;
        this.indexJ = indexJ;
        this.value = value;
    }
}
