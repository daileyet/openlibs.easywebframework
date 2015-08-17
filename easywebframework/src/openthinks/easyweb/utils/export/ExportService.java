/**
 * 
 */
package openthinks.easyweb.utils.export;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 * @author dailey
 * 
 */
public class ExportService {

	private ExportData exportData;
	private SourceTemplate sourceTemplate;
	private TargetDefine targetDefine;

	public ExportData getExportData() {
		return exportData;
	}

	public void setExportData(ExportData exportData) {
		this.exportData = exportData;
	}

	public SourceTemplate getSourceTemplate() {
		return sourceTemplate;
	}

	public void setSourceTemplate(SourceTemplate sourceTemplate) {
		this.sourceTemplate = sourceTemplate;
	}

	public TargetDefine getTargetDefine() {
		return targetDefine;
	}

	public void setTargetDefine(TargetDefine targetDefine) {
		this.targetDefine = targetDefine;
	}

	public void export() {

		// prepared model
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("bean", exportData.getBeanData());
		Map<String, Object> references = exportData.getReferenceData();
		if (references != null) {
			for (Entry<String, Object> entry : references.entrySet()) {
				root.put(entry.getKey(), entry.getValue());
			}
		}
		// prepared configuration and template
		Configuration cfg = new Configuration();
		cfg.setClassForTemplateLoading(getClass(),
				sourceTemplate.getTemplatePath());
		cfg.setObjectWrapper(new DefaultObjectWrapper());

		Template template = null;
		try {
			template = cfg.getTemplate(sourceTemplate.getTemplateName());
		} catch (IOException e) {
			e.printStackTrace();
			try {
				cfg.setDirectoryForTemplateLoading(new File(sourceTemplate
						.getTemplatePath()));
			} catch (IOException e1) {
				e1.printStackTrace();
				throw new RuntimeException(e1);
			}
		}
		try {
			OutputStream out = new FileOutputStream(
					targetDefine.getOutputPath());
			Writer write = new OutputStreamWriter(out);
			template.process(root, write);
			write.flush();
			write.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
