import java.net.Socket;

public class ServerPlayer extends Thread {

    Socket socket = new Socket();
    GameRoom game = new GameRoom();


    public ServerPlayer(Socket accept, GameRoom game) {
        this.socket = accept;
        this.game = game;

    }
}
