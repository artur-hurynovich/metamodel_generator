package com.hurynovich;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

public class MetamodelGeneratorImpl implements MetamodelGenerator {

	private static final String METAMODEL_NAME_POSTFIX = "_";

	private static final String INDENT = "    ";

	private static final String FIELD_NAME_SEPARATOR = "_";

	@Override
	public JavaFile generate(final TypeElement typeElement) {
		final TypeSpec.Builder classBuilder = TypeSpec.
				classBuilder(typeElement.getSimpleName() + METAMODEL_NAME_POSTFIX).
				addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT);
		typeElement.getEnclosedElements().stream().
				filter(element -> element.getKind().isField()).
				forEach(fieldElement -> classBuilder.addField(buildField(fieldElement)));

		return JavaFile.
				builder(ClassName.get(typeElement).packageName(), classBuilder.build()).
				indent(INDENT).
				build();
	}

	private FieldSpec buildField(final Element fieldElement) {
		final String fieldElementName = fieldElement.getSimpleName().toString();
		return FieldSpec.
				builder(ClassName.get(String.class), formatFieldName(fieldElementName)).
				addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL).
				initializer("\"" + fieldElementName + "\"").
				build();
	}

	public String formatFieldName(final String originalName) {
		final StringBuilder formattedFieldNameBuilder = new StringBuilder();
		for (int i = 0; i < originalName.length(); i++) {
			final char c = originalName.charAt(i);
			if (Character.isUpperCase(c)) {
				formattedFieldNameBuilder.append(FIELD_NAME_SEPARATOR);
			}

			formattedFieldNameBuilder.append(Character.toUpperCase(c));
		}

		return formattedFieldNameBuilder.toString();
	}

}
