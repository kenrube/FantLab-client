package org.odddev.fantlab.profile;

import org.odddev.fantlab.core.di.Injector;
import org.odddev.fantlab.core.network.IServerApi;
import org.odddev.fantlab.core.rx.ConfiguratorProvider;

import javax.inject.Inject;

import rx.Observable;
import timber.log.Timber;

/**
 * @author kenrube
 * @date 15.09.16
 */

public class UserProvider implements IUserProvider {

    @Inject
    ConfiguratorProvider mConfiguratorProvider;

    @Inject
    IServerApi mServerApi;

    public UserProvider() {
        Injector.getAppComponent().inject(this);
    }

    @Override
    public Observable<Void> login(String login, String password) {
        return mServerApi.login(login, password)
                .compose(mConfiguratorProvider.applySchedulers())
                .map(response -> {
                    Timber.e(response.toString());
                    return null;
                });
    }
}