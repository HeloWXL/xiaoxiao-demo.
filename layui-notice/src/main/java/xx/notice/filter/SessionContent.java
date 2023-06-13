package xx.notice.filter;

import xx.notice.entity.User;

public class SessionContent {
    private static ThreadLocal<User> authInfoLocal = new ThreadLocal();

    public SessionContent() {
    }

    public static User getUserInfoLocal() {
        return (User)authInfoLocal.get();
    }

    public static void setUserInfoLocal(User authInfo) {
        authInfoLocal.set(authInfo);
    }

    public static void clear() {
        authInfoLocal.set((User) null);
    }
}
