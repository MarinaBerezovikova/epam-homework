package com.company.examples;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegEx_2 {

    public static void main(String[] args) throws Exception {
        String addressText = "C:\\notes.xml";
        BufferedReader reader = new BufferedReader(new FileReader(addressText));
        String line = reader.readLine();
        while (line != null) {
            System.out.println(line.trim());
            analysisXML(line);
            System.out.println();
            line = reader.readLine();
        }
    }

    public static void analysisXML(String line) {
        String openingTag = "^<\\w+>$";
        String closingTag = "^</\\w+>$";
        String emptyTag = "^<\\w+/>$";
        String contentTag = "(<.+>)(.+)(</.+>)";
        //String tagAttribute = "<[\\w\\s=]+\".+\">";
        String tagAttribute = "(<\\w+\\s+)(\\w+)([\\s=\\s\"]+)(.+)(\">)";
        Pattern patternContent = Pattern.compile(contentTag);
        Matcher matcherContent = patternContent.matcher(line.trim());
        if (matcherContent.find()) {
            System.out.print(matcherContent.group(1) + " - открывающий тег, ");
            System.out.print(matcherContent.group(2) + " - содержимое тега (значение элемента), ");
            System.out.println(matcherContent.group(3) + " - закрывающий тег");
        }
        Pattern patternOpening = Pattern.compile(openingTag);
        Matcher matherOpening = patternOpening.matcher(line.trim());
        if (matherOpening.find()) {
            System.out.println(matherOpening.group() + " - открывающий тег");
        }
        Pattern patternClosing = Pattern.compile(closingTag);
        Matcher matcherClosing = patternClosing.matcher(line.trim());
        if (matcherClosing.find()) {
            System.out.println(matcherClosing.group() + " - закрывающий тег");
        }
        Pattern patternEmpty = Pattern.compile(emptyTag);
        Matcher matcherEmpty = patternEmpty.matcher(line.trim());
        if (matcherEmpty.find()) {
            System.out.println(matcherEmpty.group() + " - тег без тела");
        }
        Pattern patternAttribute = Pattern.compile(tagAttribute);
        Matcher matcherAttribute = patternAttribute.matcher(line.trim());
        if (matcherAttribute.find()) {
            System.out.print(matcherAttribute.group(0) + " - открывающий тег с атрибутом: ");
            System.out.print(matcherAttribute.group(2) + " - название атрибута, ");
            System.out.println(matcherAttribute.group(4) + " - значение атрибута");
        }
    }
}

