package zombie;

public class MainApp {
    static void main(String[] args) {
        run();
    }
    public static void run(){
        Human bob = new Human(10);
        System.out.println(bob);
    }
}
