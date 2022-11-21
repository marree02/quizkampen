import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerPlayer extends Thread {

    Socket socket = new Socket();
    GameRoom game = new GameRoom();
    ServerPlayer opponent;
    PrintWriter out;
    BufferedReader in;
    String userName;


    public ServerPlayer(Socket accept, GameRoom game) {
        this.socket = accept;
        this.game = game;
        try {
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            userName = in.readLine();

            System.out.println("Välkommen " + userName);


        } catch (Exception e) {
            e.printStackTrace();
        }

        out.println("Väntar på motståndare");

    }

    public void setOpponent(ServerPlayer opponent) {
        this.opponent = opponent;
    }

    public ServerPlayer getOpponent(){
        return opponent;
    }

    @Override
    public void run() {

        try {

            System.out.println("Tråd startad");

            System.out.println("Du spelar mot " + opponent.userName);



           /*
            while((fromClient = in.readLine()) != null){
                System.out.println("server" + fromClient);
            }
            */



            while(true) {



        }

        } catch (Exception e) {
        e.printStackTrace();
        }

    }
}
