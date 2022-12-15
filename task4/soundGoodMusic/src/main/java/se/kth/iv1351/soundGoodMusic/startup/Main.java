package se.kth.iv1351.soundGoodMusic.startup;

import se.kth.iv1351.soundGoodMusic.controller.Controller;
import se.kth.iv1351.soundGoodMusic.view.BlockingInterpreter;

public class Main {

    public static void main(String[] args) {
        try {
        new BlockingInterpreter(new Controller()).handleCmds();
        } catch(Exception exception) {
            System.out.println("Failed to connect");
            exception.printStackTrace();
        }
    }
}


//RENT GTR02 199909207595ST
//TERMINATE GTR02 199909207595ST

//RENT GTR01 199808116134ST
//TERMINATE GTR01 199808116134ST