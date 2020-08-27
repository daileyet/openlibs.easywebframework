package com.openthinks.easyweb.context;

import com.openthinks.libs.utilities.lookup.LookupPool;

/**
 * Shared web context, used {@link LookupPool} as object shared pool
 * @author dailey.yet@outlook.com
 */
public abstract class SharedContext extends LookupPool implements WebContext{

	protected SharedContext() {
		super();
	}

	@Override
	public String name() {
		return getClass().getName();
	}
	
	@Override
	protected void cleanUp() {
		super.cleanUp();
	}
}