package com.innowise.main.java.filesworkers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileWorker {

    public boolean isFileExists(File xmlFile) {
        if (xmlFile.exists()) {
            return true;
        } else {
            try {
                if (xmlFile.createNewFile()) {
                    String baseXml = """
                            <?xml version="1.0" encoding="UTF-8"?>
                            <Users>
                            
                            </Users>""";
                    write(baseXml, xmlFile);
                    return true;
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            return false;
        }
    }

    public void write(String text, File file) {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            byte[] buffer = text.getBytes();
            fos.write(buffer, 0, buffer.length);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public String read(File file) {
        String result="";
        try(FileInputStream fin=new FileInputStream(file))
        {
            int i=-1;
            while((i=fin.read())!=-1){
                result+=(char)i;
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        return result;
    }
}
