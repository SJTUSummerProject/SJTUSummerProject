package sjtusummerproject.signmicroservice.Service.ServiceImpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.JmsProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import sjtusummerproject.signmicroservice.Service.InvokeCodeService;

@Service
public class InvokeCodeServiceImpl implements InvokeCodeService{
	@Value("${codeservice.url}")
	private String url ;

	RestTemplate template = new RestTemplate();

	@Override
	public boolean validCode(String token, String answer){
		MultiValueMap<String, String> postBody = new LinkedMultiValueMap<>();
		// 封装参数，千万不要替换为Map与HashMap，否则参数无法传递
		postBody.add("token", token);
		postBody.add("answer",answer);

		// 1、使用postForObject请求接口
		boolean result = template.postForObject(url, postBody, boolean.class);
		return result;
	}

	public void setTemplate(RestTemplate template) {
		this.template = template;
	}
}
