interface GameView{
    void display(String text);
}
public class View{
// Implement the Terminal View 
public static class terminalView implements GameView{
    @Override
    public void display(String text){
        System.out.println(text);
    }
}

// Implement the GUI View
public static class guiView implements GameView{
    @Override
    public void display(String text){
        System.out.println(text);
    }
}
}
