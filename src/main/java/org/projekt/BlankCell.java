package org.projekt;

import java.io.Serializable;

public class BlankCell extends Cell implements Serializable {
    public BlankCell(){}

    @Override
    public int getValue() {return 0;}

}
