package story;

import java.util.List;

public class TQQuest {
    public String title;
    public String author;
    public TQCharacter character;
    //    json attributes (json file received from Twine using Twison)
    public TwPassage[] passages;
    public String name;
    public int startnode;
    public String creator, creator_version, ifid;

    @Override
    public String toString() {
        return "story.TQQuest{" +
                "passages=" + passages +
                ",\n name='" + name + '\'' +
                ",\n startnode='" + startnode + '\'' +
                ",\n creator='" + creator + '\'' +
                ",\n creator_version='" + creator_version + '\'' +
                ",\n ifid='" + ifid + '\'' +
                '}';
    }
}
