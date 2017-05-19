package com.zzc.common.util;

import com.zzc.common.image.AbstractImagePath;
import com.zzc.common.image.LocalDevEnvImagePath;
import com.zzc.common.image.TestAndProductEnvImagePath;

/**
 * 图片路径静态（或简单）工厂类，根据系统所在的环境不同，产生相对应的对象
 * 
 * @author chenjiahai
 *
 */
public class ImagePathFactory {

	public static AbstractImagePath newInstance(boolean isLocalDev) {
		if (isLocalDev) {
			return new LocalDevEnvImagePath();
		} else {
			return new TestAndProductEnvImagePath();
		}
	}
}
