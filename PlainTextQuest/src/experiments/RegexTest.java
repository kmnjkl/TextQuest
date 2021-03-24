package experiments;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {
    public static void main(String[] args) {
        String text = "Hello, how are @you? Do you @speak@ English? \n I'm @name@. I'm fine! \n What @@do@@ you think about @@name@@. Goodbye, @@name@@. I'm @name@";
        System.out.println(text.replaceAll("@@.*?@@", "LKJUH"));
        System.out.println(text.replaceAll("@name@", "LKJUH"));
//        Pattern pattern = Pattern.compile("([^@]*)@name@([^@]*)");
//        Matcher matcher = pattern.matcher(text);
//        while (matcher.find()) {
//            System.out.println(text.substring(matcher.start(), matcher.end()));
//        }
    }
}
