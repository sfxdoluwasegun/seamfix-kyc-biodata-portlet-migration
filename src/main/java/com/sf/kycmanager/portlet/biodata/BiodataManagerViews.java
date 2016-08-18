package com.sf.kycmanager.portlet.biodata;

import com.sf.lfa.msg.page.IPageView;

public enum BiodataManagerViews implements IPageView {
	HOME("home.jsp"), BIOVIEW("bioView.jsp");

	private String jsp;

	private BiodataManagerViews(String jsp) {
		this.setJsp(jsp);
	}

	@Override
	public IPageView getHome() {
		return HOME;
	}

	@Override
	public IPageView getPage(String page) {
		return fromName(page);
	}

	public static BiodataManagerViews fromName(String name) {
		for (BiodataManagerViews nav : BiodataManagerViews.values()) {
			if (nav.toString().equals(name)) {
				return nav;
			}
		}
		throw new IllegalArgumentException("Unknown Type specified");
	}

	public String getJsp() {
		return jsp;
	}

	public void setJsp(String jsp) {
		this.jsp = jsp;
	}

}
