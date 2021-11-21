package com.hurynovich;

import com.squareup.javapoet.JavaFile;

import javax.lang.model.element.TypeElement;

public interface MetamodelGenerator {

	JavaFile generate(TypeElement typeElement);

}
