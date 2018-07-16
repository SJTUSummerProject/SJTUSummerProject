package sjtusummerproject.signmicroservice.Service.ServiceImpl;

import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import sjtusummerproject.signmicroservice.Service.InvokeCodeService;

@Service
public class InvokeCodeServiceImpl implements InvokeCodeService{
	private String url = "http://code-microservice:8080/Code/Validate";

	@Override
	public boolean validCode(String token, String answer){
		MultiValueMap<String, String> postBody = new LinkedMultiValueMap<>();
		RestTemplate template = new RestTemplate();
		// 封装参数，千万不要替换为Map与HashMap，否则参数无法传递
		postBody.add("token", token);
		postBody.add("answer",answer);

		// 1、使用postForObject请求接口
		boolean result = template.postForObject(url, postBody, boolean.class);
		return result;
	}
}
