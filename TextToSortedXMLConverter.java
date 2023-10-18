package com.coding.java;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.StringTokenizer;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

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

public class TextToSortedXMLConverter {
    private Map<Sentence, ArrayList<String>> sentenceMap;
    private String inputFilePath;
    private String outputFilePath;

    public TextToSortedXMLConverter(String inputFilePath, String outputFilePath) {
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

            writeXML();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeXML() {
        try (FileWriter fileWriter = new FileWriter(outputFilePath)) {
            JAXBContext context = JAXBContext.newInstance(Map.class, Sentence.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            marshaller.marshal(sentenceMap, fileWriter);
        } catch (IOException | JAXBException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
    	TextToSortedXMLConverter converter = new TextToSortedXMLConverter("C://input/input.txt", "C://output/output.xml");
        converter.processText();
    }
}