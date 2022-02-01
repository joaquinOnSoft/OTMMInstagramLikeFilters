package com.opentext.otmm.sc.imgfilters;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class IMDecoratorTest {
	@Test
	public void getOptions() {
		assertEquals(IMDecorator.MINIATURE.getOptions(), "-resize 200x200^");
		assertEquals(IMDecorator.PREVIEW.getOptions(), "-resize 800x600^");
		assertEquals(IMDecorator.BORDER.getOptions(), "-bordercolor white -border 20");
	}
}
