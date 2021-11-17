package com.hurynovich;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import java.io.IOException;

public interface MetamodelGenerator {

	void generate(TypeElement typeElement, ProcessingEnvironment processingEnv) throws IOException;

}
