package sample;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * Created by Max on 25.01.2018.
 */
public class CipherManager {

    //Alphabeth that will be used (44 chars)
    String alphabeth = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.,!?()[]";

    //Get index where character is located in the alphabeth string
    private int getIndexInAlphabet(char character){
        return  alphabeth.indexOf(character);
    }

    //Encrypt the string plaintext with the string key (should be 44 characters)
    public String Encrypt(String plainText, String key, boolean printStatistic){
        String cipher = "";
        //Filter characters that are not part of the defined alphabeth, remove whitespaces, remove newlines and concert to uppercase
        plainText = filterPlainText(plainText);
        if(printStatistic)
            PrintStatistic(plainText);
        //Iterate over every character in plaintext and concateniate the encrypted character to cipher
        for(int i = 0; i < plainText.length(); i++){
            //Get location of character in alphabeth
            int index = getIndexInAlphabet(plainText.charAt(i));
            //Print message if character was not part of alphabeth
            if(index == -1){
                System.out.println("Failed Char: " + plainText.charAt(i));
            }
            //Encrypt character
            //Rotate alphabeth ring by current character position and get corresponding character in key
            cipher += getCharacterFromKeyWithRound(key,index,i);
        }

        return cipher;
    }

    //Rotate alphabeth position by round and return corresponding character in key
    public char getCharacterFromKeyWithRound(String key, int alphbetPos, int round){
        //Calculate how many times the ring was rotated from its initial postition by calculating the character position modulo 44
        int shift = round%alphabeth.length();
        //Shift the positiong of character in alphabeth by this value
        int pos = alphbetPos + shift;
        //Check if key bounds are exceeded
        if(pos >= key.length()){
            //Start from the beginning again
            pos -= key.length();
        }
        //Retrun matching character from key on that position
        return key.charAt(pos);
    }

    //Decrypt the cipher with key
    public String Decrypt(String cipher, String key){
        String plainText = "";
        //Iterate over every character in the cipher
        for(int i = 0; i < cipher.length(); i++){
            char selectedKeyChar = cipher.charAt(i);
            //Get postion of character in key
            int index = key.indexOf(selectedKeyChar);
            if(index != -1) {
                //Rotate key position in decryption direction by position modulo 44
                int shiftedIndex = (index-(i%alphabeth.length()));
                //if smaller < 0 start at 44 again
                if(shiftedIndex < 0){
                    shiftedIndex = alphabeth.length() + shiftedIndex;
                }
                //Get matching character from alphabeth on that position
                char unencryptedChar = alphabeth.charAt(shiftedIndex);
                //concat to result
                plainText += unencryptedChar;
            }
            else {
                //If character was not in key replace with + instead (for instance for not perfect distribution)
                plainText += "+";
            }
        }
        return plainText;
    }

    //Read file at path into string, perform frequency analysis and return key
    public String forceDecryptFileGetKey(String path){
        File cipherTextFile = new File(path);
        try {
            //System.out.println("Alphbet lenght: " + alphabeth.length());
            String text = new String(Files.readAllBytes(cipherTextFile.toPath()), StandardCharsets.UTF_8);
            //Remove eof
            text = text.substring(0,text.length()-1);
            //System.out.println("111:" + FrequencyAnalyseForE(text,0));
            String key = deriveKeyFromCipher(text);
            return key;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    //Read file at path into string, perform frequency analysis and use key to decrypt the file at path
    public String forceDecryptFile(String path){
        File cipherTextFile = new File(path);
        String text = null;
        try {
            text = new String(Files.readAllBytes(cipherTextFile.toPath()), StandardCharsets.UTF_8);

            text = text.substring(0,text.length()-1);
            String key = forceDecryptFileGetKey(path);
            String plain = Decrypt(text,key);
            return plain;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Decrypt file from path with key string and return plaintext result
    public String DecryptFromPath(String path,String key){
        File cipherTextFile = new File(path);
        String text = null;
        try {
            text = new String(Files.readAllBytes(cipherTextFile.toPath()), StandardCharsets.UTF_8);
            text = text.substring(0,text.length()-1);
            String plain = Decrypt(text,key);
            return plain;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Perform frequency analysis on cipher to derive Key
    public String deriveKeyFromCipher(String cipher){

        String key = "";
        //Split the cipher into 44 modulo bin strings(one for every position of the encryption wheel)
        String[] bins = createBinsFromText(cipher);
        //Perform frequency analysis on every bin for the letter E (Because in english language E is the most used character )
        // and concat resulting letter to key
        for(int i = 0; i < bins.length; i++){
            key += FrequencyAnalyseForE(bins[i],i);
        }
        //Shift resulting key which starts with E (because we derive letters with E distribution)
        // to the left until it starts with A and return resulting key
        return LeftShiftEKey(key);
    }

    //Split the cipher into 44 modulo bin strings(one for every position of the encryption wheel)
    public String[] createBinsFromText(String cipher){
        String[] bins = new String[alphabeth.length()];
        for(int i = 0; i < bins.length; i++){
            bins[i] = "";
        }
        for(int i = 0; i < cipher.length(); i++){
            int binIndex = i%bins.length;
            bins[binIndex] += cipher.charAt(i);
        }
        return bins;
    }

    //Shift resulting key which starts with E (because we derive letters with E distribution)
    public String LeftShiftEKey(String eKey){
        int shifts = getIndexInAlphabet('E');
        String shiftedKey = "";
        for(int i = 0; i < alphabeth.length(); i++){
            int index = i - shifts;
            if(index < 0){
                index = alphabeth.length() + index;
            }
            shiftedKey += eKey.charAt(index);
        }
        return shiftedKey;
    }

    //Perform frequency analysis on cipher for the letter E (Because in english language E is the most used character )
    // and return corresponding cipher letter
    //Cipher needs to use same key on every letter
    public char FrequencyAnalyseForE(String cipher, int bin){

        //Create one counter variabale for every letter in the alphabeth
        int[] characterAlphCount = new int[alphabeth.length()];

        //Iterate over every letter in cipher get the position in the alphabeth and increment the corresponding
        //letter counter to get a distribution with the occurances of the letter in the ciphertext
        for(int i = 0; i < cipher.length(); i++){
            int index = getIndexInAlphabet(cipher.charAt(i));
            if(index == -1){
                System.out.println(cipher.charAt(i));
            }
            try {
                //Increment letter counter
                characterAlphCount[index]++;
            }
            catch (Exception e){
                System.out.println("Exception:" + e.getMessage());
                System.out.println("Index:" + index);
                System.out.println("Character: " + cipher.charAt(i));
                System.out.println("at " + i);
                System.out.println("Cipher: " + cipher );
                System.out.println("length: " + cipher.length() );
                System.out.println("BinIndex: " + bin );
            }
        }

        //Find the letter that was used most in cipher (which is likely to be E as it is most used)
        int mostCountedCharIndex = 0;
        for(int i = 0; i < characterAlphCount.length; i++){
            if(characterAlphCount[mostCountedCharIndex] < characterAlphCount[i]){
                mostCountedCharIndex = i;
            }
        }
        //Return corresponding letter
        return alphabeth.charAt(mostCountedCharIndex);
    }

    //Filter characters that are not part of the defined alphabeth, remove whitespaces, remove newlines and concert to uppercase
    public String filterPlainText(String plainText){
        String filtered = "";
        plainText = plainText.replace("\n","");
        plainText = plainText.replace(" ","");
        plainText = plainText.toUpperCase();
        for(int i = 0; i < plainText.length(); i++){
            if(alphabeth.contains(""+plainText.charAt(i)))
                filtered += plainText.charAt(i);
        }
        return filtered;
    }

    //For testing and debugging

    public void PrintStatistic(String text){
        int[] characterAlphCount = new int[alphabeth.length()];

        for(int i = 0; i < text.length(); i++){
            int index = getIndexInAlphabet(text.charAt(i));
            characterAlphCount[index]++;
        }
        for(int i = 0; i < characterAlphCount.length; i++){
            System.out.println(alphabeth.charAt(i) + ":" + characterAlphCount[i]);
        }
    }

    public void PrintBiggestLetter(String text){
        int[] characterAlphCount = new int[alphabeth.length()];

        for(int i = 0; i < text.length(); i++){
            int index = getIndexInAlphabet(text.charAt(i));
            characterAlphCount[index]++;
        }

        int mostCountedCharIndex = 0;
        for(int i = 0; i < characterAlphCount.length; i++){
            if(characterAlphCount[mostCountedCharIndex] < characterAlphCount[i]){
                mostCountedCharIndex = i;
            }
        }

        System.out.println(alphabeth.charAt(mostCountedCharIndex) + ":" + characterAlphCount[mostCountedCharIndex]);
    }



}
