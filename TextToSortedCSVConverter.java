package com.coding.java;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.StringTokenizer;

class Sentence {
    private String content;
    private ArrayList<String> words;

    public Sentence(String content, ArrayList<String> words) {
        this.content = content;
        this.words = words;
    }

    public String getContent() {
        return content;
    }

    public ArrayList<String> getWords() {
        return words;
    }
}

public class TextToSortedCSVConverter {
    private Map<Sentence, ArrayList<String>> sentenceMap;
    private String inputFilePath;
    private String outputFilePath;

    public TextToSortedCSVConverter(String inputFilePath, String outputFilePath) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        sentenceMap = new LinkedHashMap<>();
    }

    public void processText() {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath)) ) {
            String line;

            while ((line = reader.readLine()) != null) {
                StringTokenizer sentenceTokenizer = new StringTokenizer(line, ".!?");

                while (sentenceTokenizer.hasMoreTokens()) {
                    String sentenceContent = sentenceTokenizer.nextToken().trim();

                    ArrayList<String> words = new ArrayList<>();
                    StringTokenizer wordTokenizer = new StringTokenizer(sentenceContent, " ");
                    while (wordTokenizer.hasMoreTokens()) {
                        words.add(wordTokenizer.nextToken());
                    }
                    Collections.sort(words);

                    Sentence sentence = new Sentence(sentenceContent, words);
                    sentenceMap.put(sentence, words);
                }
            }

            writeCSV();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeCSV() {
        try (FileWriter fileWriter = new FileWriter(outputFilePath)) {
            fileWriter.append("Sentence,Words\n");

            for (Sentence sentence : sentenceMap.keySet()) {
                ArrayList<String> words = sentenceMap.get(sentence);
                StringBuilder csvRow = new StringBuilder();
                csvRow.append("\"").append(sentence.getContent()).append("\",\"");

                for (int i = 0; i < words.size(); i++) {
                    if (i > 0) {
                        csvRow.append(",");
                    }
                    csvRow.append(words.get(i));
                }

                csvRow.append("\"\n");
                fileWriter.append(csvRow.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   
    public static void main(String[] args) {
        TextToSortedCSVConverter converter = new TextToSortedCSVConverter("C://input/input.txt", "C://output/output.csv");
        converter.processText();
    }
}
