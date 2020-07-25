package com.reflect.jooq.generator;

import org.jooq.codegen.JavaGenerator;
import org.jooq.codegen.JavaWriter;
import org.jooq.meta.TableDefinition;

public class ReflectGenerator extends JavaGenerator {

    @Override
    protected void generatePojoClassJavadoc(TableDefinition table, JavaWriter out) {
        super.generatePojoClassJavadoc(table, out);
        out.println("@%s", out.ref("lombok.Builder"));
    }

}
