/**
 * This File is our View, Game View depicts all the functions
 * that must be present in every type of view we create.
 * 
 * 
*/
interface GameView{
    void display(String text);
    int getSelection(String prompt, int min, int max);
    String getStringInput(String prompt);
}
public class View{
// Implement the Terminal View 
public static class terminalView implements GameView{
    @Override
    public void display(String text){
        System.out.println(text);
    }

    public int getSelection(String prompt,int min,int max){
        return 0;
    }
    public String getStringInput(String prompt){
        return "test";
    }
}

// Implement the GUI View
public static class guiView implements GameView{
    @Override
    public void display(String text){
        System.out.println(text);
    }
    // TODO
    public int getSelection(String prompt,int min,int max){
        return 0;
    }
    public String getStringInput(String prompt){
        return "test";
    }
}
}
