package com.sukaiyi.skylieve;

import com.sukaiyi.skylieve.api.lieve.service.FarmService;
import com.sukaiyi.skylieve.api.lieve.service.SigUtil;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sukaiyi
 */
@Configuration
public class LieveServiceConfig {

	@Value("${sky-lieve.access_token}")
	String mAccessToken;
	@Value("${sky-lieve.app_key}")
	String mAppKey;

	@Bean
	FarmService getFarmService() {
		OkHttpClient client = new OkHttpClient.Builder()
				.addInterceptor(chain -> {
					Request original = chain.request();
					Request.Builder requestBuilder = original.newBuilder()
							.header("Accept-Language", "zh_CN")
							.header("User-Agent", "Dalvik/2.1.0 (Linux; U; Android 7.0; MI 5 MIUI/8.1.25)")
							.header("Content-Type", "application/x-www-form-urlencoded")
							.header("Welove-UA", "[Device:MI5][OSV:7.0][CV:Android4.0.2][WWAN:0][zh_CN][platform:tencent][WSP:2]");
					Request request = requestBuilder.build();
					return chain.proceed(request);
				})
				.addInterceptor(chain -> {
					Request request = chain.request();
					if (request.method().equals("POST")) {
						Map<String, String> param = new HashMap<>();
						if (request.body() instanceof FormBody) {
							FormBody formBody = (FormBody) request.body();
							for (int i = 0; i < formBody.size(); i++) {
								param.put(formBody.encodedName(i), formBody.encodedValue(i));
							}
						}
						param.put("access_token", mAccessToken);
						param.put("app_key", mAppKey);
						param.put("ph", "farm");
						try {
							param.put("sig", SigUtil.calcSig("POST", request.url().toString(), param));
						} catch (Exception e) {
							e.printStackTrace();
						}
						if (request.body() instanceof FormBody) {
							FormBody formBody = (FormBody) request.body();
							for (int i = 0; i < formBody.size(); i++) {
								param.put(formBody.name(i), formBody.value(i));
							}
						}
						FormBody.Builder bodyBuilder = new FormBody.Builder();
						param.forEach(bodyBuilder::add);
						request = request.newBuilder().post(bodyBuilder.build()).build();
					}
					return chain.proceed(request);
				})
				.build();
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl("http://api.welove520.com/")
				.client(client)
				.addConverterFactory(ScalarsConverterFactory.create())
				.build();
		return retrofit.create(FarmService.class);
	}
}
