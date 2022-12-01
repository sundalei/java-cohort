package com.wileyedge.view;

public interface UserIO {
    
    void print(String msg);

    int readInt(String prompt, int min, int max);
}
