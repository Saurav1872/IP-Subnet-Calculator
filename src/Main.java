import com.network.Network;
public class Main {
    public static void main(String[] args) {

        Network net = new Network("192.168.1.65", "255.255.255.240");
        net.printNetworkInfo();

        
    }
}
