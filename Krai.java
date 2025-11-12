import java.util.*;

public class Krai {
    int dataInt;
    double dataDouble;
    boolean dataBool;
    String dataString;
    char dataChar;
    Krai protocol;
    Krai[] link;

    Krai() {
        dataInt = 0;
        dataDouble = 0.0;
        dataBool = false;
        dataString = "";
        dataChar = '\0';
        protocol = null;
        link = new Krai[0];
    }

    Krai(int d, Krai[] links, Krai p) {
        dataInt = d;
        protocol = p;
        link = Arrays.copyOf(links, links.length);
    }

    Krai(double d, Krai[] links, Krai p) {
        dataDouble = d;
        protocol = p;
        link = Arrays.copyOf(links, links.length);
    }

    Krai(boolean b, Krai[] links, Krai p) {
        dataBool = b;
        protocol = p;
        link = Arrays.copyOf(links, links.length);
    }

    Krai(String s, Krai[] links, Krai p) {
        dataString = s;
        protocol = p;
        link = Arrays.copyOf(links, links.length);
    }

    Krai(char c, Krai[] links, Krai p) {
        dataChar = c;
        protocol = p;
        link = Arrays.copyOf(links, links.length);
    }

    Krai[] getLinks() {
        return Arrays.copyOf(link, link.length);
    }

    Krai getProtocol() {
        return protocol;
    }

    int getDataInt() {
        return dataInt;
    }

    double getDataDouble() {
        return dataDouble;
    }

    boolean getDataBool() {
        return dataBool;
    }

    String getDataString() {
        return dataString;
    }

    char getDataChar() {
        return dataChar;
    }

    void linked_list() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter data for start:");
        int n = sc.nextInt();

        Krai start_protocol = new Krai();

        System.out.println("Enter number of nodes:");
        int t = sc.nextInt();
        Krai[] links = new Krai[1];
        Krai start = new Krai(n, links, start_protocol);

        Krai nptr = start;
        Krai current = nptr;

        for (int i = 1; i < t; i++) {
            System.out.print("Enter data for node " + (i + 1) + ": ");
            n = sc.nextInt();
            Krai[] newLink = new Krai[1];
            nptr = new Krai(n, newLink, null);
            current.link = Arrays.copyOf(new Krai[]{nptr}, 1);
            current = nptr;
        }

        System.out.println("\nLinked Krai chain formed:");
        Krai temp = start;
        int idx = 1;
        while (temp != null) {
            System.out.println("Node " + idx + " → dataInt: " + temp.getDataInt() +
                               " | protocol: " + (temp.getProtocol() == null ? "null" : "root"));
            if (temp.link.length == 0 || temp.link[0] == null) break;
            temp = temp.link[0];
            idx++;
        }
    }

    Krai protocol_equals(Krai protocol) {
        if (this.dataInt == protocol.dataInt) {
            return protocol;
        }
        return null;
    }

    Krai protocol_ROLARI(int a, Krai protocol) {
        if(protocol==null){
            return null;
        }
        if (a >= protocol.link.length || a < 0) {
            System.out.println("Invalid index given ");
            return protocol;    
        } else {
            Krai temp = new Krai();
            temp.dataInt=protocol.link[a].dataInt;
            temp.dataDouble=protocol.link[a].dataDouble;
            temp.dataString=protocol.link[a].dataString;
            temp.dataBool=protocol.link[a].dataBool;
            temp.dataChar=protocol.link[a].dataChar;
            temp.link=Arrays.copyOf(protocol.link[a].link,protocol.link[a].link.length);
            protocol.link[a].dataInt = this.dataInt;
            protocol.link[a].dataDouble = this.dataDouble;
            protocol.link[a].dataBool = this.dataBool;
            protocol.link[a].dataChar = this.dataChar;
            protocol.link[a].dataString = this.dataString;
            protocol.link[a].link = Arrays.copyOf(this.link, this.link.length);
            this.dataInt = temp.dataInt;
            this.dataDouble = temp.dataDouble;
            this.dataBool = temp.dataBool;
            this.dataChar = temp.dataChar;
            this.dataString = temp.dataString;
            this.link = Arrays.copyOf(temp.link, temp.link.length);           
            return protocol;
        }
    }

    Krai protocol_diffsim(Krai protocol, int a) {
        if (a >= this.link.length || a < 0) {
            return protocol;
        }
        if (protocol == null) {
            return null;
        } else {
            int j = Integer.MAX_VALUE;
            int p = -1;
            for (int i = 0; i < protocol.link.length; i++) {
                if (Math.abs(protocol.link[i].dataInt - this.link[a].dataInt) < j) {
                    j = Math.abs(protocol.link[i].dataInt - this.link[a].dataInt);
                    p = i;
                }
            }
            protocol = protocol.protocol_ROLARI(p, protocol);
            return protocol;
        }
    }

    public static void main(String[] args) {
        Krai demo = new Krai();
        demo.linked_list();
    }
}
