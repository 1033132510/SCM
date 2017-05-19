package com.zzc.modules.sysmgr.common.util;

/**
 * 
 * @author apple
 *
 */
public class NumberSequenceUtil {
	/**
	 * 数字填充规则
	 * 
	 * @param seqNum
	 * @param seqNumLength
	 * @param prefixChar
	 * @return
	 */
	public static String appendPrefixChar(int seqNum, int seqNumLength, char prefixChar) {
		String seqNumStr = String.valueOf(seqNum);
		for (int i = seqNumStr.length(); i < seqNumLength; i++) {
			seqNumStr = prefixChar + seqNumStr;
		}
		return seqNumStr;
	}
}
