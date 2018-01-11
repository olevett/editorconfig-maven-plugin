/**
 * Copyright (c) 2017 EditorConfig Maven Plugin
 * project contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ec4j.maven.linters;

import java.io.IOException;

import org.ec4j.core.ResourceProperties;
import org.ec4j.core.model.Property;
import org.ec4j.core.model.PropertyType;
import org.ec4j.core.model.PropertyType.IndentStyleValue;
import org.ec4j.maven.lint.api.Delete;
import org.ec4j.maven.lint.api.EditableResource;
import org.ec4j.maven.lint.api.Linter;
import org.ec4j.maven.lint.api.Location;
import org.ec4j.maven.lint.api.Violation;
import org.junit.Test;

public class XmlLinterTest {
    private final Linter linter = new XmlLinter();

    @Test
    public void simple() throws IOException {
        String text = "<?xml version=\"1.0\"?>\n" + //
                "<!-- license -->\n" + //
                "<root>\n" + //
                "  <parent-1>\n" + //
                "     <text-1>text in text-1</text-1>\n" + //
                "    </parent-1>\n" + //
                "</root>"; //
        String expectedText = "<?xml version=\"1.0\"?>\n" + //
                "<!-- license -->\n" + //
                "<root>\n" + //
                "  <parent-1>\n" + //
                "    <text-1>text in text-1</text-1>\n" + //
                "  </parent-1>\n" + //
                "</root>"; //
        EditableResource doc = LinterTestUtils.createDocument(text, ".xml");

        final ResourceProperties props = ResourceProperties.builder() //
                .property(new Property.Builder(null).type(PropertyType.indent_size).value("2").build()) //
                .property(new Property.Builder(null).type(PropertyType.indent_style).value("space").build()) //
                .property(new Property.Builder(null).type(PropertyType.trim_trailing_whitespace).value("true").build()) //
                .build();

        LinterTestUtils.assertParse(linter, doc, expectedText, props, //
                new Violation(doc, new Location(5, 5), new Delete(1), linter, PropertyType.indent_style.getName(),
                        IndentStyleValue.space.name(), PropertyType.indent_size.getName(), "2"), //
                new Violation(doc, new Location(6, 3), new Delete(2), linter, PropertyType.indent_style.getName(),
                        IndentStyleValue.space.name(), PropertyType.indent_size.getName(), "2"));
    }

}