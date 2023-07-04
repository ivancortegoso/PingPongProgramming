import React from 'react'
import './LoginWeb.css'

export class LoginWeb extends React.Component {

    submit(e) {
        console.log(e.target);
        e.preventDefault();
    }

    render() {
        return (
            <div className="LoginWeb">
                <h1>Real World App</h1>
                <h3>Login</h3>
                <form onSubmit={this.submit}>
                    <div className="FormGroup">
                        <label>First name</label>
                        <input type="text" name="firstName" defaultValue="" placeholder="First name"/>
                    </div>
                    <div className="FormGroup">
                        <label>Last name</label>
                        <input type="text" name="lastName" defaultValue="" placeholder="Last name"/>
                    </div>
                    <div className="FormGroup">
                        <label>Birth date</label>
                        <input type="date" name="birthDate" defaultValue="" placeholder="Birth date"/>
                    </div>
                    <div className="FormGroup">
                        <label>Address</label>
                        <input type="text" name="address" defaultValue="" placeholder="Address"/>
                    </div>
                    <div className="FormGroup">
                        <label>DNI</label>
                        <input type="text" name="dni" defaultValue="" placeholder="DNI"/>
                    </div>
                    <div className="FormGroup">
                        <label>Username</label>
                        <input type="text" name="username" defaultValue="" placeholder="Username"/>
                    </div>
                    <div className="FormGroup">
                        <label>Email</label>
                        <input type="text" name="email" defaultValue="" placeholder="Email"/>
                    </div>
                    <div className="FormGroup">
                        <label>Password</label>
                        <input type="password" name="password" defaultValue="" placeholder="Password"/>
                    </div>
                    <div className="FormGroup">
                        <input type="submit"/>
                    </div>
                </form>
            </div>
        )
    }
}