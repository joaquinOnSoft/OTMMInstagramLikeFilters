/*
 * (C) Copyright 2019 Joaquín Garzón (http://opentext.com) and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *   Joaquín Garzón - initial implementation
 */
package com.opentext.otmm.sc.transformers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.artesia.entity.TeamsIdentifier;
import com.artesia.metadata.MetadataField;
import com.artesia.metadata.MetadataValue;
import com.artesia.server.transformer.BaseTransformer;

public abstract class AbstractTransformer extends BaseTransformer {

	protected static final Log log = LogFactory.getLog(AbstractTransformer.class);

	public AbstractTransformer() {
		super();
	}
	
	private MetadataField createMetadataField(String fieldName, MetadataValue value) {
		MetadataField field = new MetadataField(new TeamsIdentifier(fieldName));
		field.setValue(value);
		
		log.debug("[Metadata Field] " + fieldName + ": " + value.toString());
		
		return field;
	}	

	protected MetadataField createMetadataField(String fieldName, String value) {
		MetadataValue mValue = new MetadataValue();
		mValue.setValue(value);
		return createMetadataField(fieldName, mValue);
	}

	protected MetadataField createMetadataField(String fieldName, float value) {
		MetadataValue mValue = new MetadataValue();
		mValue.setFloatValue(value);
		return createMetadataField(fieldName, mValue);
	}	
	
	protected MetadataField createMetadataField(String fieldName, double value) {
		return createMetadataField(fieldName, (float) value);
	}
}