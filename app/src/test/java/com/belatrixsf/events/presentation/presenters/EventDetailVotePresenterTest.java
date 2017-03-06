package com.belatrixsf.events.presentation.presenters;

import android.content.Context;

import com.belatrixsf.events.domain.interactors.ProjectListInteractor;
import com.belatrixsf.events.domain.interactors.ProjectVoteInteractor;
import com.belatrixsf.events.domain.interactors.base.Callback;

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
public class EventDetailVotePresenterTest {
    EventDetailVotePresenter presenter;

    @Mock
    private Context mockContext;
    @Mock
    private EventDetailVotePresenter.View view;
    @Mock
    private ProjectListInteractor mockListInteractor;
    @Mock
    private ProjectVoteInteractor mockVoteInteractor;
    @Captor
    private ArgumentCaptor<Callback> dummyCallbackArgumentCaptor;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new EventDetailVotePresenter(mockListInteractor, mockVoteInteractor);
        presenter.setView(view);
    }

    @Test
    public void voteForProject() throws Exception {
        presenter.voteForProject(1);
        verify(view).showProgressIndicator();
        verify(mockVoteInteractor,times(1)).execute(dummyCallbackArgumentCaptor.capture(), any(ProjectVoteInteractor.Params.class));
        dummyCallbackArgumentCaptor.getValue().onResult(true);
        verify(view).onVoteSuccessful();
        verify(view).hideProgressIndicator();
    }

}