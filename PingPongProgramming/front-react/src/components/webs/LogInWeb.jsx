import React from 'react'
import '../styles/FormStyle.css'

export class LogInWeb extends React.Component {
    constructor() {
        super();

        this.submit = this.submit.bind(this);
        this.login = this.login.bind(this);
    }

    async login(data) {
        const response = await fetch("/api/public/login", {
            method: 'post',
            headers:{'Content-Type' : 'application/json'},
            body: JSON.stringify(data)
        });
        const dd = await response.json();
        console.log(dd);
        localStorage.setItem("token", "Bearer " + dd["token"]);
    }

    submit(e) {
        e.preventDefault();
        const data = {
            username: e.target.username.value,
            password: e.target.password.value
        };
        this.login(data);

        console.log(data);
    }
    render() {
        return (
            <div className="LogInWeb">
                <h3>Log In</h3>
                <form className={"BaseForm"} onSubmit={this.submit}>
                    <div className="FormGroup">
                        <label>Username</label><input type="text" name="username" placeholder="Username"/>
                    </div>
                    <div className="FormGroup">
                        <label>Password</label><input type="password" name="password" placeholder="Password"/>
                    </div>
                    <input className="StandardFormSubmit" type="submit" value={"Log In"}/>
                </form>
            </div>
        );
    }
}