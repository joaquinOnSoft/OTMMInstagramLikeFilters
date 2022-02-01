package com.opentext.otmm.sc.imgfilters;

public enum IMDecorator {
	MINIATURE("Miniature", "min", "-resize 200x200^"),
	PREVIEW("Preview", "prev", "-resize 800x600^"),
	BORDER("Border", "border", "-bordercolor white -border 20");

	private final String name;
	private final String abbrev;
	private final String options;
	
	/**
	 * Image Magick decorator
	 * @param opt - Image Magick options 
	 * @see acoomans/instagram-filters  repository. https://github.com/acoomans/instagram-filters
	 */
	IMDecorator(String name, String abbrev, String opt) {
		this.name = name;
		this.abbrev = abbrev;
		options = opt;
	}
	
	public String getName() {
		return name;
	}
	
	public String getAbbrev() {
		return abbrev;
	}

	public String getOptions() {
		return options;
	}
}
