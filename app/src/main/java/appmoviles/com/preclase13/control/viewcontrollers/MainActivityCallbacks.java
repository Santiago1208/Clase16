package appmoviles.com.preclase13.control.viewcontrollers;

import appmoviles.com.preclase13.model.entity.User;

interface MainActivityCallbacks {

    void onUserDownloaded(User user);

    void onAllDataDownloaded();

    void onUserNoAuth();

    void onUserSignOut();
}
