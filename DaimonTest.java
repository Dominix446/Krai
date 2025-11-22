public class DaimonTest{
    public static void main(String[] args){
        daimon_pyrtanis d = new daimon_pyrtanis();
        Krai t = d.thought("Turn on the living-room light at 8 pm, please.");
        Krai[] top = t.getLinks();
        System.out.println("Thought protocol: " + t.getDataString());
        System.out.println("TOP LENGTH: " + top.length);
        System.out.println("POS_NP: " + d.POS_NP.getDataString());
        Krai tokens = top[0];
        Krai parse = top[1];
        System.out.println("TOKENS:");
        Krai[] tk = tokens.getLinks();
        for(int i=0;i<tk.length;i++){
            String p = tk[i].getProtocol()==null?"null":tk[i].getProtocol().getDataString();
            System.out.println(i+": " + tk[i].getDataString() + " -> pos=" + p);
        }
        System.out.println("PARSE:");
        Krai[] ps = parse.getLinks();
        System.out.println("SUBJ: " + ps[0].getDataString() + " pos=" + ps[0].getProtocol().getDataString());
        System.out.println("VERB: " + ps[1].getDataString() + " pos=" + ps[1].getProtocol().getDataString());
        System.out.println("OBJ: " + ps[2].getDataString() + " pos=" + ps[2].getProtocol().getDataString());
        System.out.println("INTENT: " + top[2].getDataString());
        Krai[] sls = top[3].getLinks();
        System.out.println("SLOTS:");
        for(int i=0;i<sls.length;i++){
            System.out.println(sls[i].getDataString() + " -> " + (sls[i].getDataTag()==null?"":sls[i].getDataTag().getDataString()));
        }
        System.out.println("CONFIDENCE: " + top[4].getDataDouble());
    }
}