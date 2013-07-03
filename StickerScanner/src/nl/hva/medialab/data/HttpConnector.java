package nl.hva.medialab.data;

import java.lang.reflect.Type;
import java.net.URI;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public final class HttpConnector<T> {

	private static final String API_URL = "http://145.92.6.118/medialab-api/";

	private static final Integer TIMEOUT = 30 * 1000;

	private UriComponentsBuilder builder;

	private RestTemplate template;

	private Class<T> generic;

	private HttpEntity<?> requestEntity;

	private HttpHeaders headers;

	/**
	 * 
	 * @param generic
	 */
	protected HttpConnector(Class<T> generic)
	{
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setConnectTimeout(TIMEOUT);
		factory.setReadTimeout(TIMEOUT);
		this.builder = UriComponentsBuilder.fromUriString(API_URL);
		this.template = new RestTemplate(factory);

		this.generic = generic;
		this.template.getMessageConverters().add(new GsonHttpMessageConverter(getGson()));
		this.headers = new HttpHeaders();
	}

	/**
	 * 
	 * @param pathSegments
	 * @return
	 */
	protected HttpConnector<T> path(String[] pathSegments)
	{
		this.builder = builder.pathSegment(pathSegments);
		return this;
	}

	/**
	 * 
	 * @param headerParams
	 * @return
	 */
	protected HttpConnector<T> header(List<NameValuePair> headerParams)
	{
		for (NameValuePair param : headerParams)
		{
			headers.add(param.getName(), param.getValue());
		}
		return this;
	}

	/**
	 * 
	 * @param queryParams
	 * @return
	 */
	protected HttpConnector<T> query(List<NameValuePair> queryParams)
	{
		for (NameValuePair param : queryParams)
		{
			this.builder = builder.queryParam(param.getName(), param.getValue());
		}
		return this;
	}

	/**
	 * 
	 * @param body
	 * @return
	 */
	protected HttpConnector<T> body(MultiValueMap<String, Object> body)
	{
		this.requestEntity = new HttpEntity<Object>(body, headers);
		return this;
	}

	/**
	 * 
	 * @return
	 */
	protected HttpConnector<T> form()
	{
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		return addFormConverter();
	}

	/**
	 * 
	 * @return
	 */
	protected HttpConnector<T> multipart()
	{
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		return addFormConverter();
	}

	/**
	 * 
	 * @return
	 */
	private HttpConnector<T> addFormConverter()
	{
		this.template.getMessageConverters().add(new FormHttpMessageConverter());
		return this;
	}

	/**
	 * 
	 * @return
	 */
	protected T post()
	{
		return sendRequest(HttpMethod.POST);
	}

	/**
	 * 
	 * @return
	 */
	protected T put()
	{
		return sendRequest(HttpMethod.PUT);
	}

	/**
	 * 
	 * @return
	 */
	protected T get()
	{
		return sendRequest(HttpMethod.GET);
	}

	/**
	 * 
	 * @param method
	 * @return
	 */
	private T sendRequest(HttpMethod method)
	{
		HttpStatus status = null;
		try
		{
			ResponseEntity<T> response = template.exchange(toUri(), method, getRequestEntity(), generic);
			status = response.getStatusCode();
			if (HttpStatus.OK.equals(status))
			{
				return response.getBody();
			}
		} catch (HttpClientErrorException e)
		{
			Log.e(getClass().toString(), String.format("Error %s request", method.toString()), e);
			e.printStackTrace();
		} catch (Exception e)
		{
			Log.e(getClass().toString(), String.format("Error %s request", method.toString()), e);
			e.printStackTrace();
		}
		Log.e(getClass().toString(), String.format("Error %s request, status: %s", method.toString(), status));
		return null;
	}

	/**
	 * 
	 * @return
	 */
	private HttpEntity<?> getRequestEntity()
	{
		if (this.requestEntity == null)
		{
			this.requestEntity = new HttpEntity<Object>(headers);
		}
		return requestEntity;
	}

	/**
	 * 
	 * @return
	 */
	private URI toUri()
	{
		return this.builder.build().toUri();
	}

	/**
	 * 
	 * @return
	 */
	private Gson getGson()
	{
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
			public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
			{
				return new Date(json.getAsJsonPrimitive().getAsLong());
			}
		});

		return builder.create();
	}

	/**
	 * 
	 * @return
	 */
	public HttpHeaders getHeaders()
	{
		return headers;
	}

}
