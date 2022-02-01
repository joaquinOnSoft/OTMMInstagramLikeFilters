package com.opentext.otmm.sc.imgfilters;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ImageMagickHelper {
	private static final String DEFAULT_IMAGEMAGICK_HOME_PATH = "C:\\Apps\\AdaptiveMediaDelivery\\ImageMagick\\";
	private static final String IMAGEMAGICK_CMD = "magick";
	
	private String imgeMagickHomePath;
	
	private static final Log log = LogFactory.getLog(ImageMagickHelper.class);

	List<IMFilter> filters = new LinkedList<IMFilter>();
	List<IMDecorator> decorators = new LinkedList<IMDecorator>();
	
	public ImageMagickHelper() {
		this(DEFAULT_IMAGEMAGICK_HOME_PATH);
	}

	public ImageMagickHelper(String imgeMagickHomePath) {
		this.imgeMagickHomePath = imgeMagickHomePath;
	}
		
	public void addFilter(IMFilter filter) {
		filters.add(filter);
	}

	public void removeFilter(IMFilter filter) {
		filters.remove(filter);
	}	

	public void addDecorator(IMDecorator decorator) {
		decorators.add(decorator);
	}	
	
	public void removeDecorator(IMDecorator decorator) {
		decorators.remove(decorator);
	}		
	
	public void clearFilters() {
		filters.clear();
	}
	
	public void clearDecorators() {
		decorators.clear();
	}
	
	/**
	 * Apply all the filters to a given image
	 * @param imgPath - Image path
	 * @param workingDir - Working directory
	 * @return
	 * @see Java ProcessBuilder examples - https://mkyong.com/java/java-processbuilder-examples/
	 */
	public int apply(String imgPath, File workingDir) {
		int exitCode = -5000;
				
		
		List<String> commands = new LinkedList<String>();
		commands.add(IMAGEMAGICK_CMD);		
		commands.add(imgPath);
		commands.addAll(getOptionsAsList(imgPath));
		commands.add(workingDir.getAbsolutePath() + File.separator + getOutputFileName(imgPath));
		
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.directory(new File(imgeMagickHomePath));        
        processBuilder.command(commands);

        log.debug("Excution directory: " + processBuilder.directory());
        log.debug("COMMAND: " + processBuilder.command().toString().replace(",", " ").replace("[", "").replace("]", ""));
                
        try {
            Process process = processBuilder.start();

			// blocked :(
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line = null;
            while ((line = reader.readLine()) != null) {
            	log.debug(line);
            }

            exitCode = process.waitFor();
            log.info("Exit code: " + exitCode);

        } catch (IOException e) {
            log.error("Applying filter: ", e);
        } catch (InterruptedException e) {
        	log.error("Applying filter: ", e);
        }
				
        return exitCode;
	}
		
	/**
	 * Provides the output file name suffix, depending on the filters and decorator applied.
	 * <strong>NOTE</strong>: This method will provide a valid value once `getOptions` method has been called.
	 * @return output file name suffix
	 */
	public String getOutputFileNameSuffix() {
		StringBuilder outputFileNameSufix = new StringBuilder();

		for (IMFilter filter : filters) {			
			outputFileNameSufix.append("_").append(filter.getAbbrev());
		}		
		
		for (IMDecorator decorator : decorators) {			
			outputFileNameSufix.append("_").append(decorator.getAbbrev());
		}
		
		return outputFileNameSufix.toString();
	}

	/**
	 * Provides the output file name, depending on the filters and decorator applied.
	 * <strong>NOTE</strong>: This method will provide a valid value once `getOptions` method has been called.
	 * @return output file name
	 */	
	public String getOutputFileName(String inputFileName) {		
		String outputFileName = generateOutputFileName(inputFileName, getOutputFileNameSuffix()); 
		
		return outputFileName;
	}

	protected List<String> getOptionsAsList(String fileName) {
		List<String> imOpts = new LinkedList<String>();

		for (IMFilter filter : filters) {
			imOpts.addAll(Arrays.asList(filter.getOptions().split(" ")));
		}		
		
		for (IMDecorator decorator : decorators) {
			imOpts.addAll(Arrays.asList(decorator.getOptions().split(" ")));
		}

		return imOpts;
	}	
	
	protected String getOptions(String fileName) {
		StringBuffer strOptions = new StringBuffer();
		
		List<String> options = getOptionsAsList(fileName);
		for (String option : options) {
			strOptions.append(" ").append(option);
		}
		
		return strOptions.toString();
	}
		
	protected String generateOutputFileName(String inputFilename, String sufix) {
		StringBuilder outputName = null;
		
		if(inputFilename != null) {
			int indexSeparator = inputFilename.lastIndexOf(File.separator);
			if(indexSeparator != -1) {
				if((indexSeparator +1) < inputFilename.length()) {
					inputFilename = inputFilename.substring(indexSeparator + 1);
				}
			}
			
			int index = inputFilename.lastIndexOf(".");
			if(index != -1) {
				outputName = new StringBuilder();
				
				String part1 = inputFilename.substring(0, index);
				String part2 = inputFilename.substring(index);
				
				outputName.append(part1);
				if(sufix != null) {
					outputName.append(sufix);
				}
				outputName.append(part2);
			}
			
		}
		
		return outputName == null? null : outputName.toString();
	}
}