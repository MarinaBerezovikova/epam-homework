package com.company.examples;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class RegEx_1 {
    /* Task 1
Cоздать приложение, разбирающее текст (текст хранится в строке) и позволяющее выполнять с текстом
три различных операции:
1. отсортировать абзацы по количеству предложений;
2. в каждом предложении отсортировать слова по длине;
3. отсортировать лексемы в предложении по убыванию количества вхождений заданного символа,
   а в случае равенства – по алфавиту.
     */
    public static void main(String[] args) throws Exception {
        //считываем текст из файла в строку
        String addressText = "When we make tea and coffee we use energy: gas, electricity and coal. Global warming comes from energy we use. So each time we make a cup of tea we make a problem for our world. People drink 229 million cups of tea and coffee every day. We should save the energy we use to make tea and coffee.\n" +
                "If we want three cups of tea, we should put three cups of water in the kettle.\n" +
                "But often we put in five or six cups of water. We use 90 seconds more for a kettle with three cups than for a kettle with one cup.\n" +
                "So each time we use a kettle we should save energy. We should think: how many cups do I want? Water is a problem too. How can we save energy, save water and be healthy?";
//        BufferedReader reader = new BufferedReader(new FileReader(addressText));
//        StringBuilder str = new StringBuilder();
//        String text;
//        while (text != null) {
//            str.append(text);
//            str.append(System.lineSeparator());
//            text = reader.readLine();
//        }
//        reader.close();
        String text;
        text = addressText;
        char symbol = 'а';
        System.out.println("Исходный текст:");
        System.out.println(text);
        System.out.println("Абзацы текста отсортированы по количеству предложений в них:");
        String sortText = sortingParagraphs(text);
        System.out.println(sortText);
        System.out.println("\nПредложения текста отсортированы по длине слов в них:");
        String[] arraySentences = creatingArraySentences(sortText);
        for (int i = 0; i < arraySentences.length; i++) {
            System.out.println(sortingSentences(arraySentences[i]));
        }
        System.out.println("\nЛексемы в предложениях отсортированы по количеству вхождения символа '" + symbol + "':");
        for (int i = 0; i < arraySentences.length; i++) {
            System.out.println(sortedWordsCountSymbol(creatingArraySentences(text)[i], symbol));
        }
    }

    // 1. отсортировать абзацы по количеству предложений;
    public static String sortingParagraphs(String text) {
        //разбиваем текст на абзацы и создаем массив
        String[] paragraphsArr = text.split("\\r?\\n");
        //поиск предложений по заглавным буква (начало) и знакам препинания (конец)
        Pattern patternSentence = Pattern.compile("[A-ZА-Я][^!.?]+[!.?]");
        boolean sorted = true;
        int countFirst = 0;
        int countSecond = 0;
        while (sorted) {
            sorted = false;
            for (int i = 0; i < paragraphsArr.length - 1; i++) {
                Matcher matcherFirst = patternSentence.matcher(paragraphsArr[i]);
                while (matcherFirst.find()) {
                    countFirst++;
                }
                Matcher matcherSecond = patternSentence.matcher(paragraphsArr[i + 1]);
                while (matcherSecond.find()) {
                    countSecond++;
                }
                if (countFirst > countSecond) {
                    String tempStr = paragraphsArr[i];
                    paragraphsArr[i] = paragraphsArr[i + 1];
                    paragraphsArr[i + 1] = tempStr;
                    sorted = true;
                }
                countFirst = 0;
                countSecond = 0;
            }
        }
        StringBuilder sortParagraph = new StringBuilder();
        for (int i = 0; i < paragraphsArr.length; i++) {
            sortParagraph.append(paragraphsArr[i] + "\n");
        }
        return sortParagraph.toString().trim();
    }

    //Создадим массив из предложений текста
    public static String[] creatingArraySentences(String text) {
        Pattern patternSentence = Pattern.compile("[A-ZА-Я][^!.?]+[!.?]");
        Matcher matcher = patternSentence.matcher(text);
        int count = 0;
        while (matcher.find()) {//подсчитываем количество предложений в тексте
            count++;
        }
        matcher = patternSentence.matcher(text);
        String[] arraySentence = new String[count];
        int id = 0;
        while (matcher.find()) {
            arraySentence[id] = matcher.group();
            id++;
        }
        return arraySentence;
    }

    //2. в каждом предложении отсортировать слова по длине;
    public static String sortingSentences(String sentence) {
        //шаблон для поиска слов и массив из найденных слов

        Pattern patternSymbol = Pattern.compile("[A-ZА-Яa-zа-я0-9]+");
        Matcher matcherSymbol = patternSymbol.matcher(sentence);
        StringBuilder builder = new StringBuilder();
        while (matcherSymbol.find()) {
            builder.append(new StringBuffer(matcherSymbol.group()) + " ");
        }
        String[] sentenceArr = builder.toString().trim().split(" ");
        Arrays.sort(sentenceArr, Comparator.comparing(String::length));
        StringBuilder sortSentence = new StringBuilder();
        for (int i = 0; i < sentenceArr.length; i++) {
            sortSentence.append(sentenceArr[i] + " ");
        }
        return sortSentence.toString().trim();
    }

    /*3. отсортировать лексемы в предложении по убыванию количества вхождений заданного символа,
         а в случае равенства – по алфавиту.*/
    public static String sortedWordsCountSymbol(String sentence, char symbol) {
        Pattern patternSymbol = Pattern.compile("[A-ZА-Яa-zа-я0-9]+");
        Matcher matcherSymbol = patternSymbol.matcher(sentence);
        StringBuilder builder = new StringBuilder();
        while (matcherSymbol.find()) {
            builder.append(new StringBuffer(matcherSymbol.group()) + " ");
        }
        int countFirst = 0;
        int countSecond = 0;
        String[] wordsArray = builder.toString().trim().split(" ");
        boolean sorted = true;
        while (sorted) {
            sorted = false;
            for (int i = 0; i < wordsArray.length - 1; i++) {
                for (int j = 0; j < wordsArray[i].length(); j++) {
                    if (wordsArray[i].charAt(j) == symbol) {
                        countFirst++;
                    }
                }
                for (int k = 0; k < wordsArray[i + 1].length(); k++) {
                    if (wordsArray[i + 1].charAt(k) == symbol) {
                        countSecond++;
                    }
                }
                if (countFirst <= countSecond) {
                    if (countFirst < countSecond) {
                        String temp = wordsArray[i];
                        wordsArray[i] = wordsArray[i + 1];
                        wordsArray[i + 1] = temp;
                        sorted = true;
                    } else {
                        if (wordsArray[i].toLowerCase().compareTo(wordsArray[i + 1].toLowerCase()) > 0) {
                            String temp = wordsArray[i];
                            wordsArray[i] = wordsArray[i + 1];
                            wordsArray[i + 1] = temp;
                            sorted = true;
                        }
                    }
                }
                countFirst = 0;
                countSecond = 0;
            }
        }
        StringBuilder sortWord = new StringBuilder();
        for (int i = 0; i < wordsArray.length; i++) {
            sortWord.append(wordsArray[i] + " ");
        }
        return sortWord.toString().trim();
    }
}
