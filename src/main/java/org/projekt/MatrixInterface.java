package org.projekt;

interface MatrixInterface<T>{
    T get(int x, int y);
//
//    void setValue(int x, int y, int element);

    int getWidth();

    int getHeight();
}