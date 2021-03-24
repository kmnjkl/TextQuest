import story.TQStory;
import story.TwLink;
import tqmanager.TQManager;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Game {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        consolePrint("File path: ");
        String filePath = sc.next();
        HashMap<String, String> characterProperties = new HashMap<>();
        characterProperties.put("name", "LKJUH");
        characterProperties.put("val", "10.1");
        HashMap<String, String> characterParameters = new HashMap<>();
        characterParameters.put("param1", "");
        String addedPath = TQManager.addQuest("TITLE", "AUTHOR", characterProperties, characterParameters, new File(filePath));
        consolePrint("= SUCCESSFULLY ADD QUEST. PATH: " + addedPath + "\n");

        consolePrint("\nFile path to get quest: ");
        String toGetPath = sc.next();
        TQStory story = new TQStory(TQManager.getQuest(toGetPath));
        consolePrint("= SUCCESSFULLY GET QUEST. STORY INITIALIZED.\n");
        boolean finished = false;
        while (!finished) {
            int currentPid = story.getCurrentPassagePid();
            String currentName = story.getCurrentPassageName();
            String passageText = story.processCurrentPassage();
            HashMap<String, String> parameters = story.getCurrentCharacterParameters();
            for (Map.Entry<String, String> p: parameters.entrySet()) {
                consolePrint(p.getKey() + "\t" + p.getValue());
            }
            consolePrint("\n");
            consolePrint(currentPid + "\t" + currentName + "\n" + passageText + "\n");
            TwLink[] links = story.getCurrentPassageLinks();
            if (story.isEnd()) {
                consolePrint("END.\n");
                finished = true;
            } else {
                int i = 1;
                for (TwLink l : links) {
                    consolePrint(i + " " + l.name + "(" + l.pid + " " + l.link + ")\n");
                    i++;
                }
                consolePrint("\nChoose link: ");
                story.goByLinkNumber(sc.nextInt());
                consolePrint("---------------------------------------------------------------------------\n");
            }
        }
        sc.close();
    }

    public static void consolePrint(String text) {
        System.out.print(text);
    }
}
