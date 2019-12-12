package Synalogik.CodingChallenge;

import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@RestController
public class FileReader {
    @RequestMapping("/files")
    public String readFile(@RequestParam(value="textfile",defaultValue = "None") String fileName){
        File file = new File(fileName);
        TreeMap<Integer, Integer> wordLengthCount = new TreeMap<>();
        // Sorted list that stores number of occurrences for word lengths in document
        int totalCount = 0;
        int totalCharacters = 0; // Total characters used to calculate average word length
        int mostFreqWordLen = 0;
        String mostFreqLen = "";
        // String that will store most occurring word lengths, appended to returned string
        try {
            Scanner reader = new Scanner(file);
            // Scanner to read each word in the document
            while(reader.hasNext()) { // While the end of file has not been reached
                totalCharacters += countLetters(reader.next(), wordLengthCount);
                totalCount++;
            }
        }catch (FileNotFoundException exception){
            return "The file could not be found";
        }
        if(totalCount == 0 || totalCharacters == 0)
            // Returns error text if the word document contains no words containing valid characters
            return "This file contains no words";
        String returnedData = "";
        // String that will be returned to user
        returnedData += "Word count = " + totalCount + "\n";
        float avgWordLen = (float)totalCharacters/totalCount;
        // Calculate average word length
        returnedData += "Average word length = " + (Math.round(avgWordLen*1000)/1000.0) + "\r\n";
        // Rounds the average word length to three decimal points
        for (Map.Entry<Integer, Integer> wordCountMap : wordLengthCount.entrySet()) {
            // For loop to print out number of words for each length found
            returnedData += "Number of words of length " + wordCountMap.getKey() + " is " + wordCountMap.getValue() + "\r\n";
            if(mostFreqWordLen == 0 || mostFreqWordLen < wordCountMap.getValue())
                // Finds the highest count for found word lengths
                mostFreqWordLen = wordCountMap.getValue();
        }
        for (Map.Entry<Integer, Integer> wordCountMap : wordLengthCount.entrySet()) {
            // Loop prints out word lengths with count equal to mostFreqWordLen
            if(wordCountMap.getValue() == mostFreqWordLen && mostFreqLen.equals(""))
                mostFreqLen = "The most frequently occurring word length is " + mostFreqWordLen + ", for word lengths of " + wordCountMap.getKey() + " ";
            else if(wordCountMap.getValue().equals(mostFreqWordLen))
                // If there are multiple word lengths with counts equal to mostFreqWordLen
                mostFreqLen += "& " + wordCountMap.getKey();
        }
        returnedData += mostFreqLen;
        return returnedData;
    }
    /*
    * Method used to count the letters of each word read from file
    */
    private int countLetters(String currentWord, TreeMap<Integer, Integer> wordLengthCount){
        int wordLen = 0;
        for (char wordLetter : currentWord.toCharArray())
            if ((wordLetter > 46 && wordLetter < 58) || (wordLetter > 64 && wordLetter < 91) || (wordLetter > 96 && wordLetter < 123) ||wordLetter == 38) {
                // If the char is in 0-9, a-z, A-Z or an &
                wordLen++;
                if((wordLetter > 46 && wordLetter < 58) & !currentWord.contains("/"))
                    return 0;
            }
        if(wordLen != 0) { // Avoids counting of invalid words, e.g. "-"
            if (wordLengthCount.containsKey(wordLen))
                wordLengthCount.put(wordLen, wordLengthCount.get(wordLen) + 1);
                // Increases stored count for word length by one
            else
                wordLengthCount.put(wordLen, 1);
            // If the word length cannot be found in the TreeMap, an entry is created
        }
        return wordLen;
    }
}
