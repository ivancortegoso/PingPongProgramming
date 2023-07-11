export class Auth {
    static SetAuth(token) {
        localStorage.setItem("token", token);
    }

    static GetAuth() {
        return localStorage.getItem("token");
    }

    static IsLogged() {
        if(localStorage.getItem("token") === null) return false;
        return localStorage.getItem("token").length > 0;
    }
}