package util;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateExceptionHandler;
/**
 * 内部类设置FreeMarker参数 得到Configuration
 * @author liuwenjun
 * @time 9:37:55 AM
 */
public class FreeMarkerManager {
	private static Configuration cfg = new Configuration();

	static {
		// 定义模板的位置，从类路径相对FreeMarkerManager所在的模板加载路径
		cfg.setTemplateLoader(new ClassTemplateLoader(FreeMarkerManager.class,"/util/ftl"));
		// 设置对象的包装器
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		// 设置异常处理器
		// 这样的话就可以${a.b.c.d}即使没有属性也不会出错
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
	}
	public static Configuration getConfiguration() {
		return cfg;
	}
}
