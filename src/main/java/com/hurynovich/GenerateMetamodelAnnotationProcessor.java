package com.hurynovich;

import com.google.auto.service.AutoService;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;

@AutoService(Processor.class)
public class GenerateMetamodelAnnotationProcessor extends AbstractProcessor {

	private final MetamodelGenerator metamodelGenerator = new MetamodelGeneratorImpl();

	@Override
	public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
		roundEnv.getElementsAnnotatedWith(GenerateMetamodel.class).forEach(element -> {
			if (element.getKind().isClass()) {
				try {
					metamodelGenerator.generate((TypeElement) element, processingEnv);
				} catch (final IOException e) {
					processingEnv.getMessager().printMessage(
							Diagnostic.Kind.ERROR,
							"Failed to generate metamodel, cause: " + e.getMessage(),
							element);
				}
			} else {
				processingEnv.getMessager().printMessage(
						Diagnostic.Kind.WARNING,
						"Only class can be annotated with @GenerateMetamodel, all other elements will be ignored",
						element);
			}
		});

		return false;
	}

	@Override
	public Set<String> getSupportedAnnotationTypes() {
		return Collections.singleton(GenerateMetamodel.class.getCanonicalName());
	}

	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.RELEASE_11;
	}

}
