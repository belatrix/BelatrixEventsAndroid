package com.belatrix.events.domain.interactors;

import android.text.TextUtils;

import com.belatrix.events.domain.interactors.base.AbstractInteractor;
import com.belatrix.events.domain.model.Project;
import com.belatrix.events.domain.model.Vote;
import com.belatrix.events.domain.repository.EventRepository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;


public class ProjectListInteractor extends AbstractInteractor {

    @Inject
    EventRepository eventRepository;

    @Inject
    ProjectListInteractor() {
    }

    public void getInteractionList(final ProjectListInteractor.CallBack callback, Params params) {
        final Params p = params;
        disposable = eventRepository.interactionList(p.eventId).subscribe(new Consumer<List<Project>>() {
            @Override
            public void accept(final List<Project> projects) throws Exception {
                disposable = eventRepository.voteList(p.eventId).subscribe(new Consumer<List<Vote>>() {
                    @Override
                    public void accept(final List<Vote> votes) throws Exception {
                        for (Project project : projects) {
                            for (Vote vote : votes) {
                                if (project.getId() == vote.getId()) {
                                    project.setVotes(vote.getVotes());
                                    break;
                                }
                            }
                            if (TextUtils.isEmpty(project.getDescription())) {
                                String[] aux = project.getTitle().split(" - ");
                                project.setTitle(aux[0]);
                                project.setDescription(aux[1]);
                            }
                        }
                        Collections.sort(projects, new Comparator<Project>() {
                            @Override
                            public int compare(Project o1, Project o2) {
                                return o1.getVotes() < o2.getVotes() ? -1 : o1.getVotes() > o2.getVotes() ? 1 : 0;
                            }
                        });
                        callback.onSuccess(projects);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callback.onError();
                    }
                });
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                callback.onError();
            }
        });
    }

    public interface CallBack {
        void onSuccess(List<Project> result);

        void onError();
    }

    public static final class Params {
        int eventId;

        public Params(int eventId) {
            this.eventId = eventId;
        }

        public static ProjectListInteractor.Params forEvent(int eventId) {
            return new ProjectListInteractor.Params(eventId);
        }

    }

}
