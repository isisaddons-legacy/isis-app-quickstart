package domainapp.dom.quick;

import java.util.List;

import javax.inject.Inject;

import com.google.common.eventbus.Subscribe;

import org.apache.isis.applib.AbstractSubscriber;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.eventbus.AbstractDomainEvent;

import org.incode.module.note.dom.impl.notablelink.NotableLink;
import org.incode.module.note.dom.impl.notablelink.NotableLinkRepository;

@DomainService(nature = NatureOfService.DOMAIN)
@DomainServiceLayout(menuOrder = "1")
public class QuickObjectDeleteRelatedNotes extends AbstractSubscriber{


    @Subscribe
    public void on(QuickObject.DeleteDomainEvent ev) {
        final AbstractDomainEvent.Phase eventPhase = ev.getEventPhase();
        if(eventPhase == AbstractDomainEvent.Phase.EXECUTING) {
            final QuickObject source = ev.getSource();
            final List<NotableLink> links = notableLinkRepository.findByNotable(source);
            for (NotableLink link : links) {
                container.removeIfNotAlready(link);
            }
        }
    }

    @Inject NotableLinkRepository notableLinkRepository;
}
