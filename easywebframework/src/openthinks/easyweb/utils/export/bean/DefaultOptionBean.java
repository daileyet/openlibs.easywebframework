package openthinks.easyweb.utils.export.bean;

public class DefaultOptionBean implements OptionBean {
	public static final String[] CHECKEDS = { "☐", "☑" };
	private String label;
	private String checked = CHECKEDS[0];

	public DefaultOptionBean(String label, String checked) {
		super();
		this.label = label;
		this.checked = checked;
	}

	public DefaultOptionBean() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DefaultOptionBean other = (DefaultOptionBean) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DefaultOptionBean [label=" + label + ", checked=" + checked
				+ "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.openthinks.easyweb.utils.export.bean.OptionBean#getLabel()
	 */
	@Override
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.openthinks.easyweb.utils.export.bean.OptionBean#getChecked()
	 */
	@Override
	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

}