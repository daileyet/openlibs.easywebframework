package openthinks.easyweb.utils.export.bean;

import java.util.ArrayList;
import java.util.List;

public class OptionsBean {

	public void addItem(OptionBean item) {
		items.add(item);
	}

	public void addDefaultItem(String label, boolean checked) {
		items.add(new DefaultOptionBean(label,
				checked ? DefaultOptionBean.CHECKEDS[1]
						: DefaultOptionBean.CHECKEDS[0]));
	}

	public void addDefaultItem(String label) {
		addDefaultItem(label, false);
	}

	public OptionsBean(List<OptionBean> items) {
		super();
		this.items = items;
	}

	public void setCheckedOption(String label, boolean checked) {
		DefaultOptionBean option = new DefaultOptionBean();
		option.setLabel(label);
		int index = items.indexOf(option);
		if (index > -1) {
			OptionBean optionBean = items.get(index);
			if (optionBean instanceof DefaultOptionBean) {
				((DefaultOptionBean) optionBean)
						.setChecked(checked ? DefaultOptionBean.CHECKEDS[1]
								: DefaultOptionBean.CHECKEDS[0]);
			}
		}
	}

	public OptionsBean() {
		super();
	}

	private List<OptionBean> items = new ArrayList<OptionBean>();

	public List<OptionBean> getItems() {
		return items;
	}

	public void setItems(List<OptionBean> items) {
		this.items = items;
	}

}
