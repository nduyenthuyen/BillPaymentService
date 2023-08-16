package utils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringHelper {
    public static String getField(String input, String regex) {
      String returnVal = null;
      final Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
      final Matcher matcher = pattern.matcher(input);
      
      if (matcher.find()) {
          returnVal = matcher.group(1);
      }

      return returnVal;
    }

    public static String padRight(String s, int n) {
      if(n > 0) {
        String text = s + " ".repeat(n);
          return text;
      }

      return s;
  }
}
