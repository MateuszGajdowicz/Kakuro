package org.projekt;

import java.io.*;

public class SaveInfo implements Serializable {

    static public void saveObj(GUI obj) throws IOException {

        FileOutputStream fileOut = new FileOutputStream("SavedBoard.ser");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);

        out.writeObject(obj);

        fileOut.close();
        out.close();

    }
}
