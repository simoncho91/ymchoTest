package com.shinsegae_inc.sitims.common;

import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.FastHashMap;

@SuppressWarnings("rawtypes")
public class CmMap<K, V> implements Map, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	transient FastHashMap		map;
	
	public CmMap() {
		map		= new FastHashMap();
	}
	
	public void clear() {
		map.clear();
	}
	
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}
	
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}
	
	public Set entrySet() {
		return map.entrySet();
	}
	
	public Object get(Object key) {
		return map.get(key);
	}
	
	public boolean isEmpty() {
		return map.isEmpty();
	}
	
	public Set keySet() {
		return map.keySet();
	}
	
	public Object put(Object key, Object value) {
		
		String[]	keyFilter		= new String[] {"v_", "n_", "c_"};
		String		keyTemp			= key.toString().toLowerCase();
		int			len				= keyFilter.length;
		boolean		isLowerCase		= false;
		Object		valueTemp		= value;
		for (int i = 0; i < len; i++) {
			if (keyTemp.indexOf(keyFilter[i]) == 0) {
				isLowerCase		= true;
				break;
			}
		}
		
		if (valueTemp == null)
			valueTemp = "";
		
		if ( isLowerCase ) {
			
			if (valueTemp instanceof Clob) {
				return map.put(key.toString().toLowerCase() , clobToString((Clob)valueTemp));
			} 
			else if (valueTemp instanceof java.lang.String) {
			//	return map.put(key.toString().toLowerCase() , ((String)valueTemp).replaceAll("\"", "&#034;"));
				return map.put(key.toString().toLowerCase(), valueTemp );
			}
			else {
				return map.put(key.toString().toLowerCase() , valueTemp);
			}
		}
		else {
			return map.put(key, valueTemp);
		}
	}
	
	public void putAll(Map map) {
		this.map.putAll(map);
	}
	
	public Object remove(Object key) {
		return map.remove(key);
	}
	
	public int size() {
		return map.size();
	}
	
	public Collection values() {
		return map.values();
	}
	
	public String clobToString (Clob clob) {
		StringBuffer	sbf	= new StringBuffer();
		Reader			rd		= null;
		char[]			buf		= new char[1024];
		int				readCnt	= 0;

		try {
			rd	= clob.getCharacterStream();
			readCnt = rd.read(buf, 0, 1024);
			while (readCnt != -1 ) {
				sbf.append(buf, 0, readCnt);
				readCnt = rd.read(buf, 0, 1024);
			}
		} catch (IOException | SQLException e) {
			return "";
		}

		return sbf.toString();
	}
	
	public int getInt(String key) {
		int result = 0;
		try {
			result = Integer.parseInt(String.valueOf(map.get(key)));
		} catch (NumberFormatException e) {
			return 0;
		}

		return result;
	}
	
	public String getString(String key) {
		return (map.get(key) == null ? "" : String.valueOf(map.get(key)));
	}
	
	public String getStringExcel(String key) {
		return (map.get(key) == null ? "" : String.valueOf(map.get(key)).replaceAll("&#034;", "\""));
	}
	
	public String[] getStringArray(String key) {
		
		if (map.get(key) == null) 
			return null;
		
		if ( ! map.get(key).getClass().isArray() ) {
			String[] result = {""};
			result[0] = (String) map.get(key); 
			return result;
		}
		
		return (String[])map.get(key);
	}
	
	public String getString(String key, String defaultValue) {
		String result = (String)map.get(key);
		return result == null ? defaultValue : result;
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public long getLong(String key) {
		long result = 0;

		result = Long.parseLong(String.valueOf(map.get(key)));
		return result;
	}
	
	public double getDouble(String key) {
		double result = 0d;
		
		result = Double.parseDouble(String.valueOf(map.get(key)));
		
		return result;
	}
	
	public boolean getBoolean(String key) {
		boolean result = false;
		
		result = (Boolean) map.get(key);
		
		return result;
	}
	
	public void putDefault(String key, String defaultVal) {
		this.putAnullB(key, this.getString(key), defaultVal);
	}
	
	public void putAnullB(String key, String value1, String value2) {
		String value	=	(value1 != null && !value1.equals("")) ? value1 : value2;
		this.put(key, value);
	}
}
