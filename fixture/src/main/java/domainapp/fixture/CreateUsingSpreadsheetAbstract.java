/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package domainapp.fixture;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.inject.Inject;

import com.google.common.collect.Lists;
import com.google.common.io.Resources;

import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.value.Blob;

import org.isisaddons.module.excel.dom.ExcelService;

public abstract class CreateUsingSpreadsheetAbstract<T> extends FixtureScript {

    private final Class<T> cls;

    public CreateUsingSpreadsheetAbstract(Class<T> cls) {
        this.cls = cls;
    }

    //region > resourceName (input)
    private String resourceName;

    @MemberOrder(sequence = "1")
    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(final String resourceName) {
        this.resourceName = resourceName;
    }
    //endregion

    //region > objects (output)
    private final List<T> objects = Lists.newArrayList();

    /**
     * The simpleobjects created by this fixture (output).
     */
    public List<T> getObjects() {
        return objects;
    }
    //endregion

    @Override
    protected void execute(final ExecutionContext ec) {

        // defaults
        final String resourceName = checkParam("resourceName", ec, String.class);

        // validate
        final URL resource = Resources.getResource(getClass(), resourceName);
        byte[] bytes;
        try {
            bytes = Resources.toByteArray(resource);
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not read from resource: " + resourceName);
        }

        // execute
        final Blob blob = new Blob(resourceName, ExcelService.XSLX_MIME_TYPE, bytes);
        final List<T> objects = excelService.fromExcel(blob, cls);

        for (final T obj : objects) {
            container.persistIfNotAlready(obj);
            this.objects.add(obj);
        }

    }

    @Inject
    private ExcelService excelService;

}
