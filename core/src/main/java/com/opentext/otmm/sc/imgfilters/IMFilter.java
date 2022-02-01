package com.opentext.otmm.sc.imgfilters;

/**
 * @see https://github.com/acoomans/instagram-filters
 * @see https://github.com/lauraschlimmer/hipster_filters
 * @see http://www.fmwconcepts.com/imagemagick/index.php
 * @author JoaquinOnSoft
 */
public enum IMFilter {
	BLACK_AND_WHITE("Black & White", "bw", "-type Grayscale"),
	// https://github.com/acoomans/instagram-filters/blob/master/instagram_filters/filters/gotham.py
	GOTHAN("Gothan", "gothan", "-modulate 120,10,100 -fill #222b6d -colorize 20 -gamma 0.5 -contrast"),
	// https://github.com/acoomans/instagram-filters/blob/master/instagram_filters/filters/lomo.py
	LOMO("Lomo", "lomo", "-channel R -level 33% -channel G -level 33%"),
	// https://github.com/acoomans/instagram-filters/blob/master/instagram_filters/filters/nashville.py
	NASHVILLE("Nashville", "nashville", "-contrast -modulate 100,150,100 -auto-gamma"),
	// https://github.com/lauraschlimmer/hipster_filters/blob/master/lensflare_processor.rb
	LENSFLARE("Lensflare", "lensflare", "-compose screen -gravity northwest"),
	// https://github.com/lauraschlimmer/hipster_filters/blob/master/vintage_processor.rb
	VINTAGE("Vintage", "vintage", "-compose over -gravity center -modulate 120,120 -brightness-contrast 0x20"),
	// http://www.fmwconcepts.com/imagemagick/painteffect/index.php
	PAINT("Paint", "paint", "-selective-blur 0x+20% -paint 5 ( +clone -sharpen 0x2 -morphology edgein diamond:1 -evaluate multiply 2 -colorspace gray -negate ) -compose multiply -composite"),
	//http://www.fmwconcepts.com/imagemagick/polarblur/index.php
	POLARBLUR("PolarBlur", "polarblur", "-virtual-pixel edge -distort DePolar -1 -morphology Convolve Blur:0x10,90 -virtual-pixel HorizontalTile -background black -distort Polar -1");
	
	private final String name;
	private final String abbrev;
	private final String options;
	
	IMFilter(String name, String abbrev, String opts) {
		this.name = name;
		this.abbrev = abbrev;
		options = opts;
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
