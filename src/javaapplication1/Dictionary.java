package javaapplication1;

import com.darkprograms.speech.translator.GoogleTranslate;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import javazoom.jl.decoder.JavaLayerException;

import javazoom.jl.decoder.JavaLayerException;

import com.gtranslate.Audio;
import com.gtranslate.Language;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;


public class Dictionary {

    public static ArrayList<Word> wordList = new ArrayList<>();
    public static String fileName = "data.txt";

    public static void insertWordFromFile() {
        try (Stream<String> stream = Files.lines(Paths.get(fileName), StandardCharsets.UTF_8)) {
            stream.forEach((String line) -> {
                wordList.add(new Word(line));
            });
            System.out.println("Đọc file thành công");
        } catch (Exception e) {
            System.out.println("Không thể đọc file");
        }

    }

    public ArrayList<Word> dictionarySearcher(String search) {
        ArrayList<Word> listSearch = new ArrayList<>();
        int temp = Dictionary.getWordList().size();
        for (int i = 0; i < temp; i++) {
            Word word = Dictionary.getWordList().get(i);
            if (word.getWord_target().indexOf(search) == 0) {
                listSearch.add(word);
            }
        }
        return listSearch;
    }

    public String dictionaryLookup(String tu) {
        ArrayList<Word> listWord = Dictionary.getWordList();
        String a = "";

        for (int i = 0; i < listWord.size(); i++) {
            if (listWord.get(i).getWord_target().trim().equals(tu)) {
                a = listWord.get(i).getWord_explain();
                break;

            }
        }
        if (!"".equals(a)) {
            return a;
        } else {
            return ("Lỗi hiển thị!");
        }
    }

    public void removeWordElement(int index) {
        Dictionary.wordList.remove(index);
    }

    public void removeWord(String tu) {
        ArrayList<Word> listWord = Dictionary.getWordList();
        for (int i = 0; i < listWord.size(); i++) {
            if (listWord.get(i).getWord_target().trim().equals(tu)) {
                this.removeWordElement(i);
            }
        }
    }
   
    public void testPlayingAudio() throws IOException, JavaLayerException {
        Audio audio = Audio.getInstance();
        InputStream sound = audio.getAudio("Hello", Language.ENGLISH);
        audio.play(sound);
    }
    
    String translateByGoogle(String language,String spelling)
    {
        spelling=spelling.toLowerCase();
        String explain = "!!!Không có ý nghĩa!!!";
        try{
            explain = GoogleTranslate.translate(language, spelling);
        }catch(IOException e){}
        return explain;
    }
    
    public static void setTimeout(Runnable runnable, int delay){
    new Thread(() -> {
        try {
            Thread.sleep(delay);
            runnable.run();
        }
        catch (Exception e){
            System.err.println(e);
        }
    }).start();
}

    public static void wordListExportToFile() {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"));
            for (Word ele : Dictionary.wordList) {
                bw.write(ele.getWord_target() + "\t" + ele.getWord_explain());
                bw.newLine();
            }
            bw.close();
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            System.out.println("Error " + ex);
        } catch (IOException ex) {
            System.out.println("Error " + ex);
        }
    }

    public static ArrayList<Word> getWordList() {
        return wordList;
    }

    public static void setWordList(ArrayList<Word> wordList) {
        Dictionary.wordList = wordList;
    }
}
