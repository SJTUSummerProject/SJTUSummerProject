package sjtusummerproject.codemicroservice.Service.ServiceImpl;

import org.junit.Before;
import org.junit.Test;
import sjtusummerproject.codemicroservice.CodemicroserviceApplicationTests;

import java.awt.*;
import java.util.HashMap;

import static org.junit.Assert.*;

public class GenerateCodeServiceImplTest extends CodemicroserviceApplicationTests {
    @Before
    public void setup(){
        generateCodeService.setRandom(mockRandom);
    }
    @Test
    public void testGetRandColorNormal(){
        Color color = generateCodeService.getRandColor(100, 121);
        Color expectColor = new Color(120,120,120);
        assertEquals(expectColor, color);
    }
    @Test
    public void testGetRandColorAbnormal(){
        Color color = generateCodeService.getRandColor(266, 266);
        Color expectColor = new Color(255,255,255);
        assertEquals(expectColor, color);
    }
    @Test
    public void testGetSingleNumberChar(){
        char number = generateCodeService.getSingleNumberChar();
        assertEquals('9', number);
    }

    @Test
    public void testGetLowerOrUpperChar() {
        char upper = generateCodeService.getLowerOrUpperChar(1);
        assertEquals('Z', upper);
        char lower = generateCodeService.getLowerOrUpperChar(0);
        assertEquals('z',lower);
    }
    @Test
    public void testGetCode(){
        HashMap<String, Object> result = generateCodeService.GetCode();
        String answer = (String)result.get("code-ans");
        assertEquals("zzzz", answer);
    }
    @Test
    public void testInit(){
        GenerateCodeServiceImpl generateCodeService = new GenerateCodeServiceImpl();
        assertNotNull(generateCodeService.getRandom());
    }

}