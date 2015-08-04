/**
 * The configure approach by annotation package
 */
package openthinks.easyweb.annotation.configure;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Make a configure identity for the annotated class, and the annotated class will be a configure item in file <B>web.xml</B>
 * <BR>
 * <pre><code> &lt;context-param&gt;
 *  &lt;param-name&gt;configureClassName&lt;/param-name&gt;
 *  &lt;param-value&gt;openthinks.easyweb.test.EasyWebConfigure&lt;/param-value&gt;
 * &lt;/context-param&gt;
 * </code></pre>
 * easyweb annotation configure example:
 * <pre><code>@EasyConfigure
@ScanPackages({ "openthinks.easyweb.test" })
@BootstrapClass("openthinks.easyweb.test.BootstrapImpl")
@RequestSuffixs(".do,.htm")
public class EasyWebConfigure {
	...
}</code></pre>
 * @author minjdai
 * @see RequestSuffixs
 * @see ScanPackages
 * @see BootstrapClass
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EasyConfigure {
}
