package story;

import java.util.HashMap;

public class TQCharacter {
    public HashMap<String, String> properties = new HashMap<>();
    public HashMap<String, String> parameters = new HashMap<>();

//    Default constructor - for Jackson
    public TQCharacter() {

    }

    public TQCharacter(HashMap<String, String> properties, HashMap<String, String> parameters) {
        this.properties = properties;
        this.parameters = parameters;
    }
}