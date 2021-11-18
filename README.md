A simple annotation processor for generating class metamodel at compile phase. Class should be annotated with @GenerateMetamodel. Generation is performed NOT recursively, e. g. 
you have a class:
  @GenerateMetamodel
  public class TestObject {

    private Long id;

    private String name;

    private TestInnerObject inner;
    
    // getters, setters, ...

Generated metamodel will be:
  public abstract class TestObject_ {
      public static final String ID = "id";
      public static final String NAME = "name";
      public static final String INNER = "inner";

      public TestObject_() {
      }
  }

If you also need TestInnerObject metamodel, it should be annotated with @GenerateMetamodel as well.

This annotation processor is not published to maven central repo, so if you want to use it, you can download it and publish to your local maven repo ('mvn clean install'). After 
this you can add a dependency to your project:
  <dependency>
		<groupId>com.hurynovich</groupId>
		<artifactId>metamodel_generator</artifactId>
		<version>1.0-SNAPSHOT</version>
	</dependency>
  
  For usage example see com.hurynovich.data_storage.dao.impl.DataUnitDAOImpl in https://github.com/artur-hurynovich/data-storage
