package com.belatrix.events.domain.interactors;

import com.belatrix.events.domain.interactors.base.AbstractInteractor;
import com.belatrix.events.domain.model.Project;
import com.belatrix.events.domain.repository.UserRepository;
import com.belatrix.events.utils.account.AccountUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class ListMyIdeasInteractor extends AbstractInteractor {

    private final UserRepository mUserRepository;
    private final AccountUtils mAccountUtils;

    @Inject
    public ListMyIdeasInteractor(UserRepository userRepository, AccountUtils accountUtils) {
        this.mUserRepository = userRepository;
        this.mAccountUtils = accountUtils;
    }

    public void listMyIdeas(final Callback callback, final int eventId) {
        disposable = mUserRepository.listIdeas(mAccountUtils.getToken()).subscribe(new Consumer<List<Project>>() {
            @Override
            public void accept(List<Project> projects) throws Exception {
                List<Project> lst = new ArrayList<>();
                for (Project project : projects) {
                    if (project.getEvent().getId() == eventId) {
                        lst.add(project);
                    }
                }
                callback.onLoadListMyIdeas(lst);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                callback.onErrorListMyIdeas();
            }
        });
    }

    public interface Callback {
        void onLoadListMyIdeas(List<Project> result);

        void onErrorListMyIdeas();
    }
}
