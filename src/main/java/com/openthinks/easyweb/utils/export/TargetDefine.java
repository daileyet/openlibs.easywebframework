package com.openthinks.easyweb.utils.export;

public abstract class TargetDefine {
	public abstract String getOutputPath();

	public abstract String getMetaType();

	public static TargetDefine create(String outputPath, String metaType) {
		DefaultTargetDefine targetDefine = new DefaultTargetDefine();
		targetDefine.outputPath = outputPath;
		targetDefine.metaType = metaType;
		return targetDefine;
	}

	public static TargetDefine create(String outputPath) {
		return create(outputPath, "");
	}

	private static class DefaultTargetDefine extends TargetDefine {
		private String outputPath;
		private String metaType;

		@Override
		public String getOutputPath() {
			return outputPath;
		}

		@Override
		public String getMetaType() {
			return metaType;
		}

	}
}