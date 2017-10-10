package pl.blog.converters;

import pl.blog.domain.Article;

import java.util.GregorianCalendar;

/**
 * Created by Mike on 17.08.2017.
 */
public class TextToHTML {

    public static String htmlText(String text) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean flag = false;

        for (int i = 0; i < stringBuilder.length(); i++) {
            if (text.charAt(i) == '.' || text.charAt(i) == '!' || text.charAt(i) == '?') {
                if (!flag) {
                    flag = true;
                }
            } else {
                if (flag) {
                    flag = false;
                    stringBuilder.insert(i + 1, "<br/>");
                }
            }
            stringBuilder.append(text.charAt(i));
        }
        System.out.println(stringBuilder.toString());
        return stringBuilder.toString();
    }

    public static Article sideBarTexts(Article article) {
        article.setHeader(stringBuilder(article.getHeader(), 18));
        article.setBody(stringBuilder(article.getBody(), 18));

        return article;
    }

    public static Article articleTexts(Article article) {
        article.setHeader(stringBuilder(article.getHeader(), 25));
        article.setBody(stringBuilder(article.getBody(), 55));

        return article;
    }

    public static String stringBuilder(String text, int lenght) {       // don't want to have too long words in sidebar
        boolean flag;
        int count = 0;
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) != ' ') {
                flag = true;
            } else {
                flag = false;
            }
            if (flag) {
                count++;
                if (count > lenght) {
                    stringBuilder.append("-<br/>-");
                    count = 0;
                }
            } else {
                count = 0;
            }
            stringBuilder.append(text.charAt(i));
        }

        return stringBuilder.toString();
    }
}
