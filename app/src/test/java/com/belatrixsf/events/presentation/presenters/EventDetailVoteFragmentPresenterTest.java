package com.belatrixsf.events.presentation.presenters;

import android.content.Context;

import com.belatrixsf.events.domain.interactors.ProjectListInteractor;
import com.belatrixsf.events.domain.interactors.ProjectVoteInteractor;
import com.belatrixsf.events.domain.model.Project;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by dvelasquez on 2/28/17.
 */
public class EventDetailVoteFragmentPresenterTest {
    EventDetailVoteFragmentPresenter presenter;

    @Mock
    private Context mockContext;
    @Mock
    private EventDetailVoteFragmentPresenter.View view;
    @Mock
    private ProjectListInteractor mockListInteractor;
    @Mock
    private ProjectVoteInteractor mockVoteInteractor;
    @Captor
    private ArgumentCaptor<ProjectVoteInteractor.CallBack> dummyCallbackArgumentCaptor;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new EventDetailVoteFragmentPresenter(mockListInteractor, mockVoteInteractor);
        presenter.setView(view);
    }

    @Test
    public void voteForProject() throws Exception {
        presenter.voteForProject(1);
        verify(view).showProgressIndicator();
        verify(mockVoteInteractor,times(1)).execute(dummyCallbackArgumentCaptor.capture(), any(ProjectVoteInteractor.Params.class));
        dummyCallbackArgumentCaptor.getValue().onSuccess(new Project());
        verify(view).onVoteSuccessful();
        verify(view).hideProgressIndicator();
    }

}