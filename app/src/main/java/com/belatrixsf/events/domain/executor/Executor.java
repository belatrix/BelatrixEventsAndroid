package com.belatrixsf.events.domain.executor;

import com.belatrixsf.events.domain.interactors.base.AbstractInteractor;

import java.util.concurrent.Future;

/**
 * This executor is responsible for running interactors on background threads.
 * <p/>
 */
public interface Executor {

    /**
     * This method should call the interactor's run method and thus start the interactor. This should be called
     * on a background thread as interactors might do lengthy operations.
     *
     * @param interactor The interactor to run.
     */
    Future execute(final AbstractInteractor interactor);
}
