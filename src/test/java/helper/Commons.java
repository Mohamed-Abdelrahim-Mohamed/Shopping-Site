package helper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class Commons {
    public String getXpath(WebElement element) {
        int n = element.findElements(By.xpath("./ancestor::*")).size();
        String path = "";
        WebElement current = element;
        for (int i = n; i > 0; i--) {
            String tag = current.getTagName();
            int lvl = current.findElements(By.xpath("./preceding-sibling::" + tag)).size() + 1;
            path = String.format("/%s[%d]%s", tag, lvl, path);
            current = current.findElement(By.xpath("./parent::*"));
        }
        return "/" + current.getTagName() + path;
    }

    public String getRandomString(int length) {
        StringBuilder sb = new StringBuilder();
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }

    public int convertFromStringWithCommaToInteger(String text) {
        int num = 0;
        String textFormated = text.replaceAll(",", "");
        textFormated = textFormated.trim();
        num = Integer.valueOf(textFormated);
        return num;
    }

    public boolean checkStringValueInListOfStrings(List<String> list, String valueToCheck) {
        boolean present = false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(valueToCheck)) {
                present = true;
            } else {
                present = false;
            }
        }
        return present;
    }

    public boolean checkListOfStringsContainsStringValue(List<String> list, String valueToCheck) {
        boolean present = false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).contains(valueToCheck)) {
                present = true;
            } else {
                present = false;
            }
        }
        return present;
    }

    public boolean checkListOfStringsStartsWithStringValue(List<String> list, String valueToCheck) {
        boolean present = false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).startsWith(valueToCheck)) {
                present = true;
            } else {
                present = false;
            }
        }
        return present;
    }

    public boolean checkListOfStringsEndsWithStringValue(List<String> list, String valueToCheck) {
        boolean present = false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).endsWith(valueToCheck)) {
                present = true;
            } else {
                present = false;
            }
        }
        return present;
    }

}
