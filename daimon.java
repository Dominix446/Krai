import java.io.*;
class daimon_pyrtanis{
    Krai[] dict;
    Krai POS_NOUN;
    Krai POS_VERB;
    Krai POS_ADJ;
    Krai POS_ADV;
    Krai POS_PROPN;
    Krai POS_PRON;
    Krai POS_DET;
    Krai POS_PREP;
    Krai POS_NP;
    Krai POS_NUM;
    Krai POS_PUNCT;
    Krai POS_UNKNOWN;
    Krai INTENT_COMMAND;
    Krai INTENT_QUERY;
    Krai INTENT_ASSERT;

    public daimon_pyrtanis(){
        loadDictionary();
        POS_NOUN = new Krai("NOUN", new Krai[0], null);
        POS_VERB = new Krai("VERB", new Krai[0], null);
        POS_ADJ = new Krai("ADJ", new Krai[0], null);
        POS_ADV = new Krai("ADV", new Krai[0], null);
        POS_PROPN = new Krai("PROPN", new Krai[0], null);
        POS_PRON = new Krai("PRON", new Krai[0], null);
        POS_DET = new Krai("DET", new Krai[0], null);
        POS_PREP = new Krai("PREP", new Krai[0], null);
        POS_NUM = new Krai("NUM", new Krai[0], null);
        POS_PUNCT = new Krai("PUNCT", new Krai[0], null);
        POS_UNKNOWN = new Krai("UNKNOWN", new Krai[0], null);
        POS_NP = new Krai("NP", new Krai[0], null);
        INTENT_COMMAND = new Krai("COMMAND", new Krai[0], null);
        INTENT_QUERY = new Krai("QUERY", new Krai[0], null);
        INTENT_ASSERT = new Krai("ASSERT", new Krai[0], null);
    }

    void loadDictionary(){
        try{
            BufferedReader br = new BufferedReader(new FileReader("english-dictionary.txt"));
            int lines = 0;
            while(br.readLine() != null) lines++;
            br.close();
            dict = new Krai[lines];
            br = new BufferedReader(new FileReader("english-dictionary.txt"));
            String s;
            int i = 0;
            while((s = br.readLine()) != null){
                dict[i++] = new Krai(s, new Krai[0], null);
            }
            br.close();
        }catch(Exception e){
            dict = new Krai[0];
        }
    }

    boolean dictContains(String w){
        if(w==null) return false;
        String lw = w.toLowerCase();
        int lo = 0;
        int hi = dict.length - 1;
        while(lo <= hi){
            int mid = (lo + hi) / 2;
            String mw = dict[mid].getDataString();
            int cmp = lw.compareTo(mw);
            if(cmp == 0) return true;
            if(cmp < 0) hi = mid - 1; else lo = mid + 1;
        }
        return false;
    }

    String[] splitTokens(String input){
        char[] cs = input.toCharArray();
        int n = cs.length;
        String[] temp = new String[n*2 + 1];
        int t = 0;
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<n;i++){
            char c = cs[i];
            boolean isAlphaNum = (c>='A'&&c<='Z')||(c>='a'&&c<='z')||(c>='0'&&c<='9')||c=='\''||c=='-';
            if(isAlphaNum){
                sb.append(c);
            } else {
                if(sb.length() > 0){
                    temp[t++] = sb.toString();
                    sb.setLength(0);
                }
                if(!Character.isWhitespace(c)){
                    temp[t++] = Character.toString(c);
                }
            }
        }
        if(sb.length()>0) temp[t++] = sb.toString();
        String[] out = new String[t];
        for(int i=0;i<t;i++) out[i] = temp[i];
        return out;
    }

    String lower(String s){
        if(s==null) return "";
        return s.toLowerCase();
    }

    boolean isNumber(String s){
        if(s==null||s.length()==0) return false;
        int dot=0;
        for(int i=0;i<s.length();i++){
            char c = s.charAt(i);
            if((c>='0' && c<='9')) continue;
            if(c=='.' && ++dot==1) continue;
            if(c==':' && s.indexOf(':')>=0) continue;
            return false;
        }
        return true;
    }

    String lemmatize(String s){
        if(s==null) return s;
        String w = lower(s);
        if(w.endsWith("ies") && w.length()>3){
            String cand = w.substring(0,w.length()-3)+"y";
            if(dictContains(cand)) return cand;
        }
        if(w.endsWith("es") && w.length()>2){
            String cand = w.substring(0,w.length()-2);
            if(dictContains(cand)) return cand;
        }
        if(w.endsWith("s") && w.length()>1){
            String cand = w.substring(0,w.length()-1);
            if(dictContains(cand)) return cand;
        }
        if(w.endsWith("ing") && w.length()>3){
            String cand = w.substring(0,w.length()-3);
            if(dictContains(cand)) return cand;
            if(w.length()>4){
                cand = w.substring(0,w.length()-3)+"e";
                if(dictContains(cand)) return cand;
            }
        }
        if(w.endsWith("ed") && w.length()>2){
            String cand = w.substring(0,w.length()-2);
            if(dictContains(cand)) return cand;
            if(w.endsWith("ied")){
                cand = w.substring(0,w.length()-3)+"y";
                if(dictContains(cand)) return cand;
            }
        }
        return w;
    }

    Krai[] makeTokenNodes(String[] tokens){
        Krai[] nodes = new Krai[tokens.length];
        for(int i=0;i<tokens.length;i++){
            String tk = tokens[i];
            Krai pos = POS_UNKNOWN;
            if(tk.length()==1 && ",.!?;:".indexOf(tk.charAt(0))>=0) pos = POS_PUNCT;
            else if(isNumber(tk)) pos = POS_NUM;
            else if(Character.isUpperCase(tk.charAt(0)) && i!=0) pos = POS_PROPN;
            else if(lower(tk).endsWith("ly")) pos = POS_ADV;
            else if(lower(tk).endsWith("ing")) pos = POS_VERB;
            else if(lower(tk).endsWith("ed")) pos = POS_VERB;
            else if(lower(tk).endsWith("able")||lower(tk).endsWith("ous")||lower(tk).endsWith("ful")||lower(tk).endsWith("ive")||lower(tk).endsWith("less")) pos = POS_ADJ;
            else if(lower(tk).endsWith("ment")||lower(tk).endsWith("ness")||lower(tk).endsWith("tion")||lower(tk).endsWith("ity")) pos = POS_NOUN;
            else if(dictContains(lower(tk))){
                String lw = lower(tk);
                if(lw.equals("the")||lw.equals("a")||lw.equals("an")) pos = POS_DET;
                else if(lw.equals("he")||lw.equals("she")||lw.equals("it")||lw.equals("they")||lw.equals("we")||lw.equals("i")||lw.equals("you")) pos = POS_PRON;
                else if(lw.equals("in")||lw.equals("on")||lw.equals("at")||lw.equals("by")||lw.equals("with")||lw.equals("to")||lw.equals("for")||lw.equals("from")||lw.equals("of")) pos = POS_PREP;
                else {
                    pos = POS_NOUN;
                }
            } else {
                pos = POS_NOUN;
            }
            Krai k = new Krai(tk, new Krai[0], pos);
            k.dataInt = i;
            nodes[i] = k;
        }
        
        
        Krai[] phrasesTemp = new Krai[0];
        int pcount = 0;
        for(int i=0;i<nodes.length;i++){
            if(nodes[i].getProtocol()==POS_DET || nodes[i].getProtocol()==POS_PROPN || nodes[i].getProtocol()==POS_NOUN){
                int j = i;
                StringBuilder sb = new StringBuilder();
                if(nodes[j].getProtocol()==POS_DET || nodes[j].getProtocol()==POS_PROPN || nodes[j].getProtocol()==POS_NOUN){
                    sb.append(nodes[j].getDataString());
                    j++;
                }
                while(j<nodes.length && (nodes[j].getProtocol()==POS_ADJ || nodes[j].getProtocol()==POS_NOUN || nodes[j].getProtocol()==POS_PROPN || nodes[j].getProtocol()==POS_NUM)){
                    sb.append(" "); sb.append(nodes[j].getDataString());
                    j++;
                }
                if(j>i+1){
                    Krai phraseNode = new Krai(sb.toString(), new Krai[0], POS_NP);
                    Krai[] newP = new Krai[phrasesTemp.length+1];
                    for(int k=0;k<phrasesTemp.length;k++) newP[k]=phrasesTemp[k];
                    newP[newP.length-1]=phraseNode;
                    phrasesTemp = newP;
                }
                i = j-1;
            }
        }
        
        if(phrasesTemp.length>0){
            Krai[] nn = new Krai[nodes.length + phrasesTemp.length];
            for(int i=0;i<nodes.length;i++) nn[i]=nodes[i];
            for(int i=0;i<phrasesTemp.length;i++) nn[nodes.length+i] = phrasesTemp[i];
            nodes = nn;
        }
        for(int i=0;i+1<nodes.length;i++){
            if(i==0 && (nodes[i].getProtocol()==POS_NOUN || nodes[i].getProtocol()==POS_UNKNOWN) && nodes[i+1].getProtocol()==POS_PREP){
                nodes[i].protocol = POS_VERB;
            }
        }
        for(int i=1;i<nodes.length;i++){
            if(nodes[i].getProtocol()==POS_VERB && nodes[i-1].getProtocol()==POS_DET){
                nodes[i].protocol = POS_NOUN;
            }
        }
        return nodes;
    }

    Krai findFirst(Krai[] nodes, Krai tag){
        for(int i=0;i<nodes.length;i++){
            if(nodes[i].getProtocol() == tag) return nodes[i];
        }
        return null;
    }

    Krai thought(String input){
        String[] tokens = splitTokens(input);
        Krai[] nodes = makeTokenNodes(tokens);
        Krai verb = findFirst(nodes, POS_VERB);
        Krai subj = null;
        Krai obj = null;
        if(verb != null){
            int v = verb.getDataInt();
            for(int i=v-1;i>=0;i--){
                Krai p = nodes[i];
                if(p.getProtocol() == POS_NOUN || p.getProtocol() == POS_PROPN || p.getProtocol() == POS_PRON){
                    subj = p; break;
                }
            }
            for(int i=v+1;i<nodes.length;i++){
                Krai p = nodes[i];
                if(p.getProtocol() == POS_NOUN || p.getProtocol() == POS_PROPN || p.getProtocol() == POS_PRON){
                    obj = p; break;
                }
            }
        } else {
            for(int i=0;i<nodes.length;i++){
                if(nodes[i].getProtocol()==POS_NOUN||nodes[i].getProtocol()==POS_PROPN||nodes[i].getProtocol()==POS_PRON){
                    if(subj==null) subj = nodes[i]; else if(obj==null) obj = nodes[i];
                }
            }
        }
        Krai[] trip = new Krai[3];
        trip[0] = subj==null?new Krai("", new Krai[0], POS_NOUN):subj;
        trip[1] = verb==null?new Krai("", new Krai[0], POS_VERB):verb;
        trip[2] = obj==null?new Krai("", new Krai[0], POS_NOUN):obj;
        Krai tokensList = new Krai("TOKENS", nodes, null);
        Krai parse = new Krai("PARSE", trip, null);
        Krai intentNode;
        if(input.trim().endsWith("?")) intentNode = INTENT_QUERY;
        else if(parse.getLinks()[1].getDataString().length()>0 && parse.getLinks()[1].getProtocol()==POS_VERB) intentNode = INTENT_COMMAND;
        else intentNode = INTENT_ASSERT;
        Krai slots = new Krai("SLOTS", new Krai[0], null);
        double conf = 0.5;
        if(verb!=null){
            conf += 0.2;
            Krai actionSlot = new Krai("action", new Krai[0], null);
            actionSlot.dataTag = new Krai(verb.getDataString(), new Krai[0], null);
            slots = new Krai("SLOTS", new Krai[]{actionSlot}, null);
        }
        if(obj!=null){
            conf += 0.2;
            Krai deviceSlot = new Krai("device", new Krai[0], null);
            deviceSlot.dataTag = new Krai(obj.getDataString(), new Krai[0], null);
            Krai[] sarr = slots.getLinks();
            Krai[] ns = new Krai[sarr.length+1];
            for(int i=0;i<sarr.length;i++) ns[i]=sarr[i];
            ns[ns.length-1]=deviceSlot;
            slots = new Krai("SLOTS", ns, null);
        }
        if(subj!=null) conf += 0.1;
        if(conf>1.0) conf=1.0;
        Krai confNode = new Krai(conf, new Krai[0], null);
        Krai thought = new Krai("THOUGHT", new Krai[]{tokensList, parse, intentNode, slots, confNode}, null);
        return thought;
    }
}