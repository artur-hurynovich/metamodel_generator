package com.hurynovich;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import java.io.IOException;

public class MetamodelGeneratorImpl implements MetamodelGenerator {

	private static final String METAMODEL_NAME_POSTFIX = "_";

	private static final String INDENT = "    ";

	@Override
	public void generate(final TypeElement typeElement, final ProcessingEnvironment processingEnv) throws IOException {
		final TypeSpec.Builder classBuilder = TypeSpec.
				classBuilder(typeElement.getSimpleName() + METAMODEL_NAME_POSTFIX).
				addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT);
		typeElement.getEnclosedElements().stream().
				filter(element -> element.getKind().isField()).
				forEach(fieldElement -> classBuilder.addField(buildField(fieldElement)));

		JavaFile.
				builder(ClassName.get(typeElement).packageName(), classBuilder.build()).
				indent(INDENT).
				build().
				writeTo(processingEnv.getFiler());
	}

	private FieldSpec buildField(final Element fieldElement) {
		final String fieldElementName = fieldElement.getSimpleName().toString();
		return FieldSpec.
				builder(ClassName.get(String.class), fieldElementName.toUpperCase()).
				addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL).
				initializer("\"" + fieldElementName + "\"").
				build();
	}

}
