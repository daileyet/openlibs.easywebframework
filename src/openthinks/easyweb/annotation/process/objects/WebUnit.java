package openthinks.easyweb.annotation.process.objects;

import java.util.Set;

public interface WebUnit {

	public String getRelativePath();

	public String getFullPath();

	public String getName();

	public <T extends WebUnit> T parent();

	public <T extends WebUnit> Set<T> children();

	public boolean isValid();

}
