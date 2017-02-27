package com.belatrixsf.events.presentation.presenters;

import android.content.Context;

import com.belatrixsf.events.domain.interactors.LoginInteractor;
import com.belatrixsf.events.domain.interactors.base.Callback;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by dvelasquez on 2/27/17.
 */
//@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest {

    LoginPresenter presenter;

    @Mock
    private Context mockContext;
    @Mock
    private LoginPresenter.View view;
    @Mock
    private LoginInteractor mockLoginInteractor;
    @Captor
    private ArgumentCaptor<Callback> dummyCallbackArgumentCaptor;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new LoginPresenter(view,mockLoginInteractor);
    }

    @Test
    public void loginSuccessful() throws Exception {
        given(view.getContext()).willReturn(mockContext);
        final String username = "diego";
        final String password = "diego";
        presenter.login(username,password);
        verify(view).showProgressDialog();
        verify(mockLoginInteractor,times(1)).execute(dummyCallbackArgumentCaptor.capture(), any(LoginInteractor.Params.class));
        dummyCallbackArgumentCaptor.getValue().onResult(username);
        verify(view).dismissProgressDialog();
        verify(view).onLoginSuccess();
    }


    @Test
    public void loginFail() throws Exception {
        given(view.getContext()).willReturn(mockContext);
        final String username = "diego";
        final String password = "diego";
        final String errorMessage = "fail";
        presenter.login(username,password);
        verify(view).showProgressDialog();
        verify(mockLoginInteractor,times(1)).execute(dummyCallbackArgumentCaptor.capture(), any(LoginInteractor.Params.class));
        dummyCallbackArgumentCaptor.getValue().onError(errorMessage);
        verify(view).dismissProgressDialog();
        verify(view).onLoginError(errorMessage);
    }
}