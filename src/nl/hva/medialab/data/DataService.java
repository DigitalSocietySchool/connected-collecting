package nl.hva.medialab.data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.medialab.beans.ConnectorConstants;
import com.medialab.beans.UserResult;

public class DataService {

	public UserResult getStickers(Long userId)
	{
		String[] pathParams = { ConnectorConstants.USER_RESOURCE_NAME, ConnectorConstants.GET_USER_STICKERS_NAME, String.valueOf(userId) };
		
		HttpConnector<UserResult> connector = new HttpConnector<UserResult>(UserResult.class);
		return connector.path(pathParams).get();
	}
	
	public UserResult addSticker(File file, Long userId)
	{
		String[] pathParams = { ConnectorConstants.USER_RESOURCE_NAME, ConnectorConstants.ADD_STICKER_NAME };
		String[] formKeys = { ConnectorConstants.FILE_NAME, ConnectorConstants.USER_ID_NAME };
		Object[] formValues = { new FileSystemResource(file), String.valueOf(userId) };
		
		MultiValueMap<String, Object> body = createMultiValueMap(formKeys, formValues);
		
		HttpConnector<UserResult> connector = new HttpConnector<UserResult>(UserResult.class);
		return connector.path(pathParams).multipart().body(body).post();
	}
	
	/**
	 * 
	 * @param keys
	 * @param values
	 * @return
	 */
	protected List<NameValuePair> createValuePairs(String[] keys, String[] values)
	{
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		if (keys != null && values != null && keys.length == values.length)
		{
			for (int i = 0; i < keys.length; i++)
			{
				list.add(new BasicNameValuePair(keys[i], values[i]));
			}
		}
		return list;
	}
	
	/**
	 * 
	 * @param keys
	 * @param values
	 * @return
	 */
	protected MultiValueMap<String, Object> createMultiValueMap(String[] keys, Object[] values)
	{
		int capacity = (keys != null ? keys.length : 0);
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>(capacity);
		if (keys != null && values != null)
		{
			for (int i = 0; i < keys.length; i++)
			{
				map.add(keys[i], values[i]);
			}
		}
		return map;
	}
}
