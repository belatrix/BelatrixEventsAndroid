package com.belatrix.events.presentation.presenters;

/**
 * Created by dvelasquez on 2/27/17.
 */

public class LoginPresenterTest {

    /*
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
        presenter = new LoginPresenter(mockLoginInteractor);
        presenter.setView(view);
    }

    @Test
    public void loginSuccessful() throws Exception {
        given(view.getContext()).willReturn(mockContext);
        final String username = "user01";
        final String password = "pass02";
        presenter.login(username,password);
        verify(view).showProgressDialog();
        verify(mockLoginInteractor,times(1)).execute(dummyCallbackArgumentCaptor.capture(), any(LoginInteractor.Params.class));
        dummyCallbackArgumentCaptor.getValue().onResult(username);
        verify(view).onLoginSuccess();
        verify(view).dismissProgressDialog();
    }


    @Test
    public void loginFail() throws Exception {
        given(view.getContext()).willReturn(mockContext);
        final String username = "user01";
        final String password = "pass02";
        final String errorMessage = "fail";
        presenter.login(username,password);
        verify(view).showProgressDialog();
        verify(mockLoginInteractor,times(1)).execute(dummyCallbackArgumentCaptor.capture(), any(LoginInteractor.Params.class));
        dummyCallbackArgumentCaptor.getValue().onError(errorMessage);
        verify(view).onLoginError(errorMessage);
        verify(view).dismissProgressDialog();
    }*/
}