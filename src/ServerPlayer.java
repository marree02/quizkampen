import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ServerPlayer extends Thread {

    Socket socket;
    GameRoom game;
    ServerPlayer opponent;
    PrintWriter out;
    BufferedReader in;
    String userName;
    String playerOneOrTwo;


    public ServerPlayer(Socket accept, GameRoom game, String playerOneOrTwo) {
        this.socket = accept;
        this.game = game;
        this.playerOneOrTwo = playerOneOrTwo;
        try {
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            userName = in.readLine();

            System.out.println("Välkommen " + userName);


        } catch (Exception e) {
            e.printStackTrace();
        }

        // out.println("Väntar på motståndare");

    }

    public void setOpponent(ServerPlayer opponent) {
        this.opponent = opponent;
    }

    public ServerPlayer getOpponent() {
        return opponent;
    }

    @Override
    public void run() {

        try {

            //Steg 2: Varje servertråd skickar till sin klient om klienten är spelare 1 eller 2
            out.println(playerOneOrTwo);

            if (playerOneOrTwo.equals("1")) {

                String[] categories = game.questionGenerator.getCategoriesAsArray(3);

                //Steg 3: en sträng för varje kategori som kan väljas skickas till klienten vars tur det är att välja
                out.println(categories[0]);
                out.println(categories[1]);
                out.println(categories[2]);

                out.flush();

                //Steg 4: väntar på att få vald kategori från klienten
                String chosenCategory = in.readLine();

                System.out.println(chosenCategory);
                game.questionGenerator.setCategory(chosenCategory);

            }

            if (in.readLine().equals("KATEGORI VALD")) {
                opponent.out.println("KATEGORI VALD");
                out.println("KATEGORI VALD");
            }


            System.out.println(game.questionGenerator.getCurrentCategory());
            System.out.println("Redo att skicka frågor");

            System.out.println(game.q.getCurrentCategory());
            System.out.println("Redo att skicka frågor");

            System.out.println(game.q.getCurrentQuestion());
            System.out.println(game.q.getCorrectAnswer());


            out.close(); //Kanske hitta annan lösning istället för denna

               /*
                while((fromClient = in.readLine()) != null){
                    System.out.println("server" + fromClient);
                }
                */


            while (true) {


            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
