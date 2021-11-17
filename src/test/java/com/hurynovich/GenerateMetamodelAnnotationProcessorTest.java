package com.hurynovich;

import com.google.common.base.Joiner;
import com.google.common.truth.Truth;
import com.google.testing.compile.JavaFileObjects;
import com.google.testing.compile.JavaSourcesSubjectFactory;
import org.junit.Test;

import javax.tools.JavaFileObject;
import java.util.List;

public class GenerateMetamodelAnnotationProcessorTest {

	private static final String NEW_LINE = "\n";

	@Test
	public void classWithOneLoggedMethod() {
		final JavaFileObject input = JavaFileObjects.forSourceString(
				"com.example.TestObject",
				Joiner.on(NEW_LINE).join(
						"package com.example;",
						"import com.hurynovich.GenerateMetamodel;",
						"@GenerateMetamodel",
						"public class TestObject {",
						"	private Long id;",
						"	private String name;",
						"	public Long getId() {",
						"		return id;",
						"	}",
						"	public void setId(Long id) {",
						"		this.id = id;",
						"	}",
						"	public String getName() {",
						"		return name;",
						"	}",
						"	public void setName(String name) {",
						"		this.name = name;",
						"	}",
						"}"
				)
		);

		final JavaFileObject output = JavaFileObjects.forSourceString(
				"com.example.TestObject_",
				Joiner.on(NEW_LINE).join(
						"package com.example;",
						"",
						"import java.lang.String;",
						"",
						"public abstract class TestObject_ {",
						"	public static final String ID = \"id\";",
						"",
						"	public static final String NAME = \"name\";",
						"}"
				)
		);

		Truth.assert_()
				.about(JavaSourcesSubjectFactory.javaSources())
				.that(List.of(input))
				.processedWith(new GenerateMetamodelAnnotationProcessor())
				.compilesWithoutError()
				.and()
				.generatesSources(output);
	}

}
