package domainapp.webapp;

import java.util.concurrent.Callable;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.core.metamodel.services.ServicesInjectorSpi;
import org.apache.isis.core.runtime.system.context.IsisContext;
import org.apache.isis.core.runtime.system.persistence.PersistenceSession;

import org.isisaddons.module.settings.dom.UserSetting;
import org.isisaddons.module.settings.dom.UserSettingsService;
import org.isisaddons.module.settings.dom.UserSettingsServiceRW;
import org.isisaddons.module.settings.dom.jdo.UserSettingJdo;

import de.agilecoders.wicket.core.settings.ActiveThemeProvider;
import de.agilecoders.wicket.core.settings.IBootstrapSettings;
import de.agilecoders.wicket.core.settings.ITheme;
import de.agilecoders.wicket.core.settings.SessionThemeProvider;
import de.agilecoders.wicket.core.settings.ThemeProvider;

public class DomainAppUserSettingsThemeProvider implements ActiveThemeProvider {

    static final String ACTIVE_THEME = "activeTheme";

    private final IBootstrapSettings settings;

    public DomainAppUserSettingsThemeProvider(final IBootstrapSettings settings) {
        this.settings = settings;
    }

    // //////////////////////////////////////

    @Override
    public ITheme getActiveTheme() {
        if(IsisContext.getSpecificationLoader().isInitialized()) {
            final String themeName = IsisContext.doInSession(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    final String currentUserName = currentUserName();

                    final Class<UserSettingsService> serviceClass = UserSettingsService.class;
                    final UserSettingsService userSettingsService = lookupService(serviceClass);

                    final UserSetting activeTheme = userSettingsService.find(currentUserName, ACTIVE_THEME);
                    return activeTheme != null ? activeTheme.valueAsString() : null;
                }
            });
            return themeFor(themeName);
        }
        return new SessionThemeProvider().getActiveTheme();
    }

    @Override
    public void setActiveTheme(final String themeName) {
        IsisContext.doInSession(new Runnable() {
            @Override
            public void run() {
                final String currentUserName = currentUserName();

                final UserSettingsServiceRW userSettingsService = getServicesInjector().lookupService(UserSettingsServiceRW.class);
                final UserSettingJdo activeTheme = (UserSettingJdo) userSettingsService.find(currentUserName, ACTIVE_THEME);
                if(activeTheme != null) {
                    activeTheme.updateAsString(themeName);
                } else {
                    userSettingsService.newString(currentUserName, ACTIVE_THEME, "Active Bootstrap theme for user", themeName);
                }
            }
        });
    }

    @Override
    public void setActiveTheme(final ITheme theme) {
        setActiveTheme(theme.name());
    }

    private ITheme themeFor(final String themeName) {
        final ThemeProvider themeProvider = settings.getThemeProvider();
        if(themeName != null) {
            for (final ITheme theme : themeProvider.available()) {
                if (themeName.equals(theme.name()))
                    return theme;
            }
        }
        return themeProvider.defaultTheme();
    }

    // //////////////////////////////////////

    protected <T> T lookupService(final Class<T> serviceClass) {
        return getServicesInjector().lookupService(serviceClass);
    }

    protected String currentUserName() {
        final DomainObjectContainer container = getServicesInjector().lookupService(DomainObjectContainer.class);
        return container.getUser().getName();
    }

    // //////////////////////////////////////

    protected ServicesInjectorSpi getServicesInjector() {
        return getPersistenceSession().getServicesInjector();
    }

    protected PersistenceSession getPersistenceSession() {
        return IsisContext.getPersistenceSession();
    }


}
