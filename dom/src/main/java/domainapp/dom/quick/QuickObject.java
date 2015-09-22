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
package domainapp.dom.quick;

import javax.jdo.JDOHelper;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.joda.time.LocalDate;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.util.ObjectContracts;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "SimpleObject"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="id")
@javax.jdo.annotations.Version(
        strategy=VersionStrategy.VERSION_NUMBER, 
        column="version")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "find", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.modules.simple.SimpleObject "),
        @javax.jdo.annotations.Query(
                name = "findByName", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.modules.simple.SimpleObject "
                        + "WHERE name.indexOf(:name) >= 0 ")
})
@javax.jdo.annotations.Unique(name="SimpleObject_name_UNQ", members = {"name"})
@DomainObject(
        editing = Editing.DISABLED
)
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_ROOT,
        cssClassFa = "fa-flag"
)
public class QuickObject implements Comparable<QuickObject> {


    //region > identificatiom
    public TranslatableString title() {
        return TranslatableString.tr("Object: {name}", "name", getName());
    }
    //endregion

    //region > name (property)

    private String name;

    @javax.jdo.annotations.Column(allowsNull="false", length = 40)
    @Title(sequence="1")
    @Property
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    // endregion

    //region > updateName (action)

    public static class UpdateNameDomainEvent extends ActionDomainEvent<QuickObject> {
        public UpdateNameDomainEvent(final QuickObject source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }

    @Action(
            domainEvent = UpdateNameDomainEvent.class
    )
    public QuickObject updateName(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "New name")
            final String name) {
        setName(name);
        return this;
    }

    public String default0UpdateName() {
        return getName();
    }

    public TranslatableString validateUpdateName(final String name) {
        return name.contains("!")? TranslatableString.tr("Exclamation mark is not allowed"): null;
    }

    //endregion

    //region > anInteger (property)
    private Integer anInteger;

    @javax.jdo.annotations.Column(allowsNull="true")
    public Integer getInteger() {
        return anInteger;
    }

    public void setInteger(final Integer anInteger) {
        this.anInteger = anInteger;
    }
    //endregion

    //region > updateInteger (action)

    public static class UpdateIntegerDomainEvent extends ActionDomainEvent<QuickObject> {
        public UpdateIntegerDomainEvent(final QuickObject source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }

    @Action(
            domainEvent = UpdateIntegerDomainEvent.class
    )
    public QuickObject updateInteger(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "New integer")
            final Integer integer) {
        setInteger(integer);
        return this;
    }

    public Integer default0UpdateInteger() {
        return getInteger();
    }

    //endregion

    //region > aLocalDate (property)
    private LocalDate aLocalDate;

    @javax.jdo.annotations.Column(allowsNull="true")
    public LocalDate getLocalDate() {
        return aLocalDate;
    }

    public void setLocalDate(final LocalDate aLocalDate) {
        this.aLocalDate = aLocalDate;
    }
    //endregion

    //region > updateLocalDate (action)

    public static class UpdateLocalDateDomainEvent extends ActionDomainEvent<QuickObject> {
        public UpdateLocalDateDomainEvent(final QuickObject source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }

    @Action(
            domainEvent = UpdateLocalDateDomainEvent.class
    )
    public QuickObject updateLocalDate(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "New localdate")
            final LocalDate localdate) {
        setLocalDate(localdate);
        return this;
    }

    public LocalDate default0UpdateLocalDate() {
        return getLocalDate();
    }

    //endregion

    //region > aBoolean (property)
    private Boolean aBoolean;

    @javax.jdo.annotations.Column(allowsNull="true")
    public Boolean getBoolean() {
        return aBoolean;
    }

    public void setBoolean(final Boolean aBoolean) {
        this.aBoolean = aBoolean;
    }
    //endregion

    //region > updateBoolean (action)

    public static class UpdateBooleanDomainEvent extends ActionDomainEvent<QuickObject> {
        public UpdateBooleanDomainEvent(final QuickObject source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }

    @Action(
            domainEvent = UpdateBooleanDomainEvent.class
    )
    public QuickObject updateBoolean(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "New boolean")
            final Boolean aBoolean) {
        setBoolean(aBoolean);
        return this;
    }

    public Boolean default0UpdateBoolean() {
        return getBoolean();
    }

    //endregion

    //region > version (derived property)
    public Long getVersionSequence() {
        return (Long) JDOHelper.getVersion(this);
    }
    //endregion

    //region > compareTo

    @Override
    public int compareTo(final QuickObject other) {
        return ObjectContracts.compare(this, other, "name");
    }

    //endregion

    //region > injected services

    @javax.inject.Inject
    @SuppressWarnings("unused")
    private DomainObjectContainer container;

    //endregion


}
