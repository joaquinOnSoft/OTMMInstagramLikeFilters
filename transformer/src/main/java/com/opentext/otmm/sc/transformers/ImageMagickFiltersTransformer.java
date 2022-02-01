package com.opentext.otmm.sc.transformers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import com.artesia.common.exception.BaseTeamsException;
import com.artesia.server.common.plugin.ServerPluginResources;
import com.artesia.server.transformer.ImportTransformationResult;
import com.artesia.server.transformer.ImportTransformer;
import com.artesia.server.transformer.TransformerAssetWrapper;
import com.artesia.transformer.TransformerArgument;
import com.artesia.transformer.TransformerInfo;
import com.opentext.otmm.sc.imgfilters.IMDecorator;
import com.opentext.otmm.sc.imgfilters.IMFilter;
import com.opentext.otmm.sc.imgfilters.ImageMagickHelper;

public class ImageMagickFiltersTransformer extends AbstractTransformer implements ImportTransformer {

	private static final String VERSION = "v21.01.31";

	private static final String ARG_IMAGEMAGICK_HOME_PATH = "IMAGEMAGICK_HOME_PATH";
	private static final String ARG_OUTPUT_PATH = "OUTPUT_PATH";
	
	@Override
	public void initialize(TransformerInfo info, Map<String, String> configurationAttributesMap) {
		log.debug(">>> initialize() >>> " + VERSION);		
	}

	@Override
	public ImportTransformationResult transformAssetForImport(TransformerAssetWrapper assetWrapper, File workingDir,
			List<TransformerArgument> argList, ServerPluginResources resources) throws BaseTeamsException{		

		//assetWrapper.getAsset().getSupportingContentInfo()
		log.debug(">>> transformAssetForImport() >>> " + VERSION);

		//
		// Manage transformer attributes
		//
		File outputDir = null;
		File imagemagickDir = null;
		File tmpDir = null;
		
		if (argList != null && argList.size() > 0) {
			
			for (TransformerArgument arg : argList) {
				tmpDir = new File(arg.getDefaultValue());				
				log.debug(arg.getName() + " " + tmpDir.getAbsolutePath());
				
				switch (arg.getName()) {
				case ARG_IMAGEMAGICK_HOME_PATH:					
					if(tmpDir.isDirectory()) {
						imagemagickDir = tmpDir;
					}
					break;
				case ARG_OUTPUT_PATH:
					if(tmpDir.isDirectory()) {
						outputDir = tmpDir;
					}					
					break;
				}
			}
		}
		
		if(outputDir == null) {
			outputDir = workingDir;
		}		
		
		log.info("BASE Output directory : " + outputDir.getAbsolutePath());
		
		//
		// Generate output folder (with the asset id) 
		//
		File outputDirWithId = createOutputDirWithId(outputDir, assetWrapper.getAsset().getAssetId().asString());
		
		log.info("Output directory (with asset ID): " + outputDirWithId.getAbsolutePath());
		log.info("Working directory: " + workingDir.getAbsolutePath());		
		
		//
		// Apply filters
		//
		String assetPath = assetWrapper.getMasterFile().getAbsolutePath();
		log.info("Asset path: " + assetPath);
						
		ImportTransformationResult result = new ImportTransformationResult(true);
		
		ImageMagickHelper imHelper = imagemagickDir == null?  new ImageMagickHelper() : new ImageMagickHelper(imagemagickDir.getAbsolutePath());
		imHelper.addDecorator(IMDecorator.MINIATURE);
				        
		int exitCode = -1;
		IMFilter[] filters = IMFilter.values();
		for(IMFilter filter: filters) {
			log.debug("Applying filter: " + filter.getName());
			
			imHelper.addFilter(filter);			
			exitCode = imHelper.apply(assetPath, outputDirWithId);			
						
			log.info("Filters applyed. Exit code: " + exitCode);
						
			imHelper.removeFilter(filter);
		}
				
		log.trace("<<< transformAssetForImport() >>> " + VERSION);

		return result;
	}

	private File createOutputDirWithId(File outputDir, String assetId) {
		File outputDirWithId = new File(outputDir.getAbsolutePath() + File.separator + assetId);
		
		if(!outputDirWithId.exists()) {
			Path pathOutputDirWithId = Paths.get(outputDirWithId.getAbsolutePath());
			try {
				Files.createDirectories(pathOutputDirWithId);
			} catch (IOException e) {
				log.error("Creating output directory (with asset ID): ", e);
				
				outputDirWithId = outputDir;
				log.warn("Using default output dir by default: " + outputDir.getAbsolutePath());
			}
		}
		
		return outputDirWithId;
	}
}
