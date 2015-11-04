package domainapp.dom.quick;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import com.google.common.eventbus.Subscribe;

import org.apache.isis.applib.AbstractSubscriber;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;

import org.incode.module.note.dom.api.notable.Notable;
import org.incode.module.note.dom.impl.notablelink.NotableLink;

import lombok.Getter;
import lombok.Setter;

@javax.jdo.annotations.PersistenceCapable(
        identityType= IdentityType.DATASTORE,
        schema="quick")
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@DomainObject(
        objectType = "quick.NotableLinkForQuickObject"
)
public class NotableLinkForQuickObject extends NotableLink {

    @DomainService(nature = NatureOfService.DOMAIN, menuOrder = "1")
    public static class InstantiationSubscriber extends AbstractSubscriber {
        @Programmatic
        @Subscribe
        public void on(final InstantiateEvent ev) {
            if(ev.getPolymorphicReference() instanceof QuickObject) {
                ev.setSubtype(NotableLinkForQuickObject.class);
            }
        }
    }

    @Override
    public void setPolymorphicReference(final Notable polymorphicReference) {
        super.setPolymorphicReference(polymorphicReference);
        setQuickObject((QuickObject) polymorphicReference);
    }

    @Column(
            allowsNull = "false",
            name = "demoObjectId"
    )
    @Getter @Setter
    private QuickObject quickObject;

    @javax.inject.Inject
    private QuickObjectRepository quickObjectRepository;
}
