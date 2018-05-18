/* The MIT License (MIT)
* Copyright (c) 2016 BELATRIX
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:

* The above copyright notice and this permission notice shall be included in all
* copies or substantial portions of the Software.

* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
* SOFTWARE.
*/
package com.belatrix.events.presentation.presenters;

import com.belatrix.events.R;
import com.belatrix.events.domain.model.Collaborator;
import com.belatrix.events.presentation.presenters.base.BelatrixBasePresenter;
import com.belatrix.events.presentation.presenters.base.BelatrixBaseView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by icerrate on 09/06/2016.
 */
public class AboutFragmentPresenter extends BelatrixBasePresenter<AboutFragmentPresenter.View> {

    private List<Collaborator> collaboratorsList = new ArrayList<>();

    public interface View extends BelatrixBaseView {
        void addContacts(List<Collaborator> list);
        void resetList();
    }

    @Inject
    public AboutFragmentPresenter() {
    }

    public void getContacts() {
        view.resetList();
        prepareCollaborators();
        view.addContacts(collaboratorsList);
    }

    private void prepareCollaborators() {
        collaboratorsList = new ArrayList<>();
        collaboratorsList.add(new Collaborator("Diego", "Velásquez", R.drawable.dvelasquez));
        collaboratorsList.add(new Collaborator("Karla", "Cerron", R.drawable.kcerron));
        collaboratorsList.add(new Collaborator("Sergio", "Infante", R.drawable.sinfante));
        collaboratorsList.add(new Collaborator("Carlos", "Monzón", R.drawable.cmonzon));
    }

    @Override
    public void cancelRequests() {

    }

    public List<Collaborator> getCollaboratorsSync() {
        return collaboratorsList;
    }

    // saving state stuff

    public void loadPresenterState(List<Collaborator> collaboratorsList) {
        this.collaboratorsList = collaboratorsList;
    }

}