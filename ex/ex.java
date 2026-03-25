abstract class Game {
    void play() {
        start();
        process();
        end();
    }

    abstract void start();
    abstract void process();
    abstract void end();
}

class Cricket extends Game {
    void start() {
        System.out.println("cricket start");
    }

    void process() {
        System.out.println("cricket play");
    }

    void end() {
        System.out.println("cricket end");
    }
}

class Football extends Game {
    void start() {
        System.out.println("football start");
    }

    void process() {
        System.out.println("football play");
    }

    void end() {
        System.out.println("football end");
    }
}

public class ex {
    public static void main(String[] args) {
        Game g1 = new Cricket();
        g1.play();

        Game g2 = new Football();
        g2.play();
    }
}