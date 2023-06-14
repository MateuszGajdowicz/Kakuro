package org.projekt;

import java.io.*;

public class ReadInfo implements Serializable {

    static public GUI readObj(GUI gui) throws IOException, ClassNotFoundException {

        GUI objRead = null;

        FileInputStream fileIn = new FileInputStream("SavedBoard.ser");
        ObjectInputStream in = new ObjectInputStream(fileIn);

        objRead = (GUI) in.readObject();

        in.close();
        fileIn.close();

        return objRead;
    }
}
