package com.opentext.otmm.sc.imgfilters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;

public class ImageMagickHelperTest {
	private ImageMagickHelper helper;
	
	//TODO Replace for a not local path
	private final String IMAGEMAGICK_HOME_PATH_JOAQUIN = "C:\\Program Files\\ImageMagick-7.1.0-Q16-HDRI";
	
	@Before
	public void setup() {
		helper = new ImageMagickHelper(IMAGEMAGICK_HOME_PATH_JOAQUIN);
		helper.addFilter(IMFilter.BLACK_AND_WHITE);
		helper.addDecorator(IMDecorator.BORDER);
		helper.addDecorator(IMDecorator.MINIATURE);
	}
	
	@Test
	public void getOptions() {
		String options = helper.getOptions("kitten.jpg");
		
		assertEquals(" -type Grayscale -bordercolor white -border 20 -resize 200x200^", options);
	}
	
	@Test
	public void generateOutputFileName() {
		String outputName = helper.generateOutputFileName("kitten.jpg", "_miniature_border_bw");
		assertEquals("kitten_miniature_border_bw.jpg", outputName);
	}
	
	@Test
	public void apply() {
		File png = loadResource("cat-g94c5d075a_640.jpg");
				
		assertNotNull(png);
		assertTrue(png.isFile());
		
		int exitCode = helper.apply(png.getAbsolutePath(), png.getParentFile());
		
		assertEquals(0, exitCode);
		
		String strImgFiltered = png.getParentFile() + File.separator + helper.getOutputFileName(png.getName());
		File imgFiltered = new File(strImgFiltered);
		
		assertTrue(imgFiltered.exists());
		assertTrue(imgFiltered.isFile());
		
		imgFiltered.delete();
	}
	
	@Test
	public void applyAllFilters() {
		helper.clearDecorators();
		helper.clearFilters();
		helper.addDecorator(IMDecorator.MINIATURE);
		
		File png = loadResource("cat-g94c5d075a_640.jpg");
		
		assertNotNull(png);
		assertTrue(png.isFile());
		
		int exitCode = -1;
		
		IMFilter[] filters = IMFilter.values();
		for(IMFilter filter: filters) {
			helper.addFilter(filter);
			
			exitCode = helper.apply(png.getAbsolutePath(), png.getParentFile());
			
			assertEquals(0, exitCode);
			
			String strImgFiltered = png.getParentFile() + File.separator + helper.getOutputFileName(png.getName());
			File imgFiltered = new File(strImgFiltered);
			
			assertTrue(imgFiltered.exists());
			assertTrue(imgFiltered.isFile());
			
			imgFiltered.delete();						
		}		
	}	

	private File loadResource(String fileName) {
		File png = null;
		// Picture by: Ty_Swartz
		// Original picture: https://pixabay.com/photos/cat-kitten-pet-kitty-young-cat-551554/
		URL resource = ImageMagickHelperTest.class.getClassLoader().getResource(fileName);
		
		if (resource == null) {
			throw new IllegalArgumentException("file is not found!");
		} else {
			png = new File(resource.getFile());
		}
		return png;
	}	
}
