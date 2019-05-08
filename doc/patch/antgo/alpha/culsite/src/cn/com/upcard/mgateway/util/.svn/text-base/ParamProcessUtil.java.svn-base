package cn.com.upcard.mgateway.util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class ParamProcessUtil {
	/**
	 * 将参数以字典序排序，并且以key=value&key=value的形式将非空参数拼接成字符串，
	 * @param data 参数
	 * @param salt  签名的key值
	 * @param excludes  不包含的的参数
	 * @return
	 */
	public static String generateSignParam(final Map<String, String> data, String salt, List<String> excludes) {
		Set<String> keySet = data.keySet();
		String[] keyArray = keySet.toArray(new String[keySet.size()]);
		Arrays.sort(keyArray);
		StringBuilder sb = new StringBuilder();
		if (excludes == null || excludes.size() < 1) {
			for (String key : keyArray) {
				if (excludes.contains(key)) {
					continue;
				}
				if (StringUtils.isEmpty(data.get(key))) {
					continue;
				}
				sb.append(key).append("=").append(data.get(key).trim()).append("&");
			}
			sb.append("comm_key=").append(salt);
			return sb.toString();
		}
		for (String key : keyArray) {
			if (StringUtils.isEmpty(data.get(key))) {
				continue;
			}
			sb.append(key).append("=").append(data.get(key).trim()).append("&");
		}
		sb.append("comm_key=").append(salt);
		return sb.toString();
	}
}
