import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientApp {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("35.200.251.56", 2000);

        OutputStream os = socket.getOutputStream();
        InputStream is = socket.getInputStream();

        Thread read = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        if (is.available() > 0) {
                            int d = 0;
                            String msg = "";
                            while ((d = is.read()) != 38) {
                                msg = msg + (char) d;
                            }
                            System.out.println(msg);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        read.start();

        Thread write = new Thread(new Runnable() {
            @Override
            public void run() {
                Scanner sc = new Scanner(System.in);
                while (true) {
                    String msg = sc.nextLine();
                    try {
                        os.write((msg + "&").getBytes());
                        os.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        write.start();
    }
}
