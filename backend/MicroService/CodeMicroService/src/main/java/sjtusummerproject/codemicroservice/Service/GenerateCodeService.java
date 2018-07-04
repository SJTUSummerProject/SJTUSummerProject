package sjtusummerproject.codemicroservice.Service;

import net.sf.json.JSONObject;
import net.sf.json.JSONArray;

import java.util.HashMap;

public interface GenerateCodeService {
    /* map 包括
    * image : code-image
    * code-ans : code-answer
    *
    * */
    HashMap<String,Object> GetCode();
}
