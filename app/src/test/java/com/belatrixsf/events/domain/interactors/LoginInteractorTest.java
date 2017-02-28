package com.belatrixsf.events.domain.interactors;

import com.belatrixsf.events.domain.executor.Executor;
import com.belatrixsf.events.domain.executor.MainThread;
import com.belatrixsf.events.domain.interactors.base.Callback;
import com.belatrixsf.events.domain.repository.Repository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by dvelasquez on 2/28/17.
 */
public class LoginInteractorTest {

    LoginInteractor interactor;
    @Mock
    Executor executor;
    @Mock
    MainThread mainThread;
    @Mock
    Repository repository;
    @Mock
    Runnable runnable;
    @Mock
    Callback<String> callback;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        interactor = new LoginInteractor(executor, mainThread,repository);
    }

    @Test
    public void loginSuccessful(){
        final String username = "user01";
        final String password = "user02";
        when(repository.login(username,password)).thenReturn(true);
        interactor.execute(callback,LoginInteractor.Params.forUser(username, password));
        interactor.run(interactor.getParams());
        verify(repository,times(1)).login(username,password);
        verify(mainThread).post(any(Runnable.class));
        assertTrue(repository.login(username,password));
    }


    @Test
    public void loginSuccessFail(){
        final String username = "user01";
        final String password = "user02";
        when(repository.login(username,password)).thenReturn(false);
        interactor.execute(callback,LoginInteractor.Params.forUser(username, password));
        interactor.run(interactor.getParams());
        verify(repository,times(1)).login(username,password);
        verify(mainThread).post(any(Runnable.class));
        assertFalse(repository.login(username,password));
    }

}