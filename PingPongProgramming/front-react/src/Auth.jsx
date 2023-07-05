

export class Auth {
    static SetAuth(token) {
        localStorage.setItem("token", token);
    }

    static GetAuth() {
        localStorage.getItem("token");
    }

    static IsLogged() {
        return localStorage.getItem("token").length > 0;
    }
}