package view.swing.user;

import model.User;

public interface IUserFormView {
    User getUserFromForm();
    void setUserInForm(User user);
    void showInfoMessage(String msg);
    void showErrorMessage(String msg);
    void close();
}
