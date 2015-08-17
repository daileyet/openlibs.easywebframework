package openthinks.easyweb.utils.export;

public abstract class SourceTemplate {

	public abstract String getType();

	public abstract String getTemplateName();

	/**
	 * could be template file path or class path
	 * 
	 * @return
	 */
	public abstract String getTemplatePath();

	public static SourceTemplate create(String templatePath,
			String templateName, String type) {
		DefaultSourceTemplate template = new DefaultSourceTemplate();
		template.templateName = templateName;
		template.templatePath = templatePath;
		template.type = type;
		return template;
	}

	public static SourceTemplate create(String templatePath, String templateName) {
		return create(templatePath, templateName, "");
	}

	private static class DefaultSourceTemplate extends SourceTemplate {
		private String type;
		private String templateName;
		private String templatePath;

		@Override
		public String getType() {
			return type;
		}

		@Override
		public String getTemplateName() {
			return templateName;
		}

		@Override
		public String getTemplatePath() {
			return templatePath;
		}

	}
}