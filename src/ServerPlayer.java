import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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

            //Det första som händer - ställer sig och väntar på att få användarnamnet
            userName = in.readLine();

            System.out.println("Välkommen " + userName);

        } catch (Exception e) {
            e.printStackTrace();
        }

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

            String[] categories = game.questionGenerator.getCategoriesAsArray(3);

            //Steg 3: en sträng för varje kategori som kan väljas skickas till båda klienterna
            out.println(categories[0]);
            out.println(categories[1]);
            out.println(categories[2]);

            //Steg 4: väntar på att få vald kategori från klienten
            String chosenCategory = in.readLine();

            //If blir true om kategorin kommer från den spelare som ska välja kategori
            //Då meddelas den andre spelaren att kategori är vald
            if(!chosenCategory.equals("INGEN KATEGORI")) {
                game.questionGenerator.setCategory(chosenCategory);
                opponent.out.println();
            }

            out.println();

            while (true) {

                out.println(opponent.userName);
                out.println(game.questionGenerator.getCurrentCategory());
                out.println(game.questionGenerator.getCurrentQuestion());

                String[] choices = game.questionGenerator.getChoicesAsArray();
                for (String s : choices) {
                    System.out.println(s);
                    out.println(s);
                }

                out.println(game.questionGenerator.getCorrectAnswer());

                // Väntar på att klienten ska svara på frågan
                in.readLine();

                // skickar sträng så klienten vet att den ska starta nytt gameGui
                out.println();

                game.questionGenerator.nextQuestion();


            }

            // out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
