package data;

import java.util.Random;

/**
 * Created by sean on 5/13/16.
 */
public class Number {
    Random _random;

    public Number(){
        _random = new Random();

    }

    public int random(){
        return _random.nextInt();
    }
}
