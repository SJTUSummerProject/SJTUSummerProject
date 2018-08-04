package sjtusummerproject.codemicroservice.Mock;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class MockRandom extends Random {
    @Override
    public int nextInt(int bound) {
        if (bound == 0) return 0;
        else return bound-1;
    }
}
