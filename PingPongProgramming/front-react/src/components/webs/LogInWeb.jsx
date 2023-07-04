import React from 'react'
import './LogInStyle.css'

export class LogInWeb extends React.Component {


    async login(data) {
        const response = await fetch("/api/login", {
            method: 'post',
            headers:{'Content-Type' : 'application/json'},
            body: JSON.stringify(data)
        });
    }

    submit(e) {
        const data = {
            username: e.target.username.value,
            password: e.target.password.value
        };
        //this.login(data);

        console.log(data);
        e.preventDefault();
    }
    render() {
        return (
            <div className="LogInWeb">
                <h3>Log In</h3>
                <form onSubmit={this.submit}>
                    <div className="FormGroup">
                        <label>Username</label><input type="text" name="username" placeholder="Username"/>
                    </div>
                    <div className="FormGroup">
                        <label>Password</label><input type="password" name="password" placeholder="Password"/>
                    </div>
                    <input className="StandardFormSubmit" type="submit"/>
                </form>
            </div>
        );
    }
}