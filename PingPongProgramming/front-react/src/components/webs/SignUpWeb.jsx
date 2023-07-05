import React from 'react'
import '../styles/SignUpStyle.css'

export class SignUpWeb extends React.Component {
    constructor(props) {
        super(props);

        this.signup = this.signup.bind(this);
        this.submit = this.submit.bind(this);
    }

    async signup(data) {
        const response = await fetch("http://localhost:8080/api/public/register", {
            method: 'post',
            headers:{'Content-Type' : 'application/json'},
            body: JSON.stringify(data)
        });
    }

    submit(e) {
        e.preventDefault();
        const data = {
            firstName: e.target.firstName.value,
            lastName: e.target.lastName.value,
            birthDate: e.target.birthDate.value,
            address: e.target.address.value,
            documentId: e.target.documentId.value,
            phoneNumber: e.target.phoneNumber.value,
            email: e.target.email.value,
            username: e.target.username.value,
            password: e.target.password.value
        };

        this.signup(data);

        console.log(data);
    }

    render() {
        return (
            <div className="SignUpWeb">
                <h3>Sign Up</h3>
                <form onSubmit={this.submit}>
                    <div className="FormGroup">
                        <label>First name</label> <input type="text" name="firstName" defaultValue="" placeholder="First name"/>
                    </div>
                    <div className="FormGroup">
                        <label>Last name</label> <input type="text" name="lastName" defaultValue="" placeholder="Last name"/>
                    </div>
                    <div className="FormGroup">
                        <label>Birth date</label> <input type="date" name="birthDate" defaultValue="" placeholder="Birth date"/>
                    </div>
                    <div className="FormGroup">
                        <label>Address</label> <input type="text" name="address" defaultValue="" placeholder="Address"/>
                    </div>
                    <div className="FormGroup">
                        <label>Document Id</label> <input type="text" name="documentId" defaultValue="" placeholder="Document Id"/>
                    </div>
                    <div className="FormGroup">
                        <label>Phone number</label> <input type="tel" name="phoneNumber"  placeholder="Phone number"/>
                    </div>
                    <div className="FormGroup">
                        <label>Email</label> <input type="text" name="email" defaultValue="" placeholder="Email"/>
                    </div>
                    <div className="FormGroup">
                        <label>Username</label> <input type="text" name="username" defaultValue="" placeholder="Username"/>
                    </div>
                    <div className="FormGroup">
                        <label>Password</label> <input type="password" name="password" defaultValue="" placeholder="Password"/>
                    </div>
                    <input className="StandardFormSubmit" type="submit"/>
                </form>
            </div>
        )
    }
}