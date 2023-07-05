import React from 'react'
import './styles/AccountMenuStyle.css'
import {Link} from "react-router-dom";
import {Auth} from "../Auth";

export class AccountMenu extends React.Component {
    constructor() {
        super();
        this.state = {
            info: [],
        }
        this.fetchInfo = this.fetchInfo.bind(this);
    }
    async fetchInfo() {
        const response = await fetch("http://localhost:8080/api/user", {
            method: 'GET',
            headers: {
                'Authorization': Auth.GetAuth()
            }
        });
        try{
            const recvInfo = await response.json();
            console.log(recvInfo);
            this.setState( { info: recvInfo });
        } catch(e) {

        }

    }

    componentDidMount() {
        if(Auth.IsLogged())
            this.fetchInfo()
    }

    render() {
        return (
            <div className={this.props.isShown ? "AccountMenu AccountMenu-Enable" : "AccountMenu AccountMenu-Disable"}>
                <label>Account Menu</label>
                {Auth.IsLogged() ? <div><Link to="/login" onClick={()=> Auth.SetAuth("")}>Log out</Link></div> : <div><Link to="/login">Log in</Link></div>}
                {Auth.IsLogged() ? "" : <div><Link to="/signup">Sign up</Link></div>}
                <div>Username: {this.state.info["username"]}</div>
                <div>First name: {this.state.info["firstName"]}</div>
                <div>Second name: {this.state.info["lastName"]}</div>
                {Auth.IsLogged() ? <div><Link to="/user/settings">My Account</Link></div> : ""}
                {Auth.IsLogged() ? <div><Link to="/bankaccounts">Bank Accounts</Link></div> : ""}
            </div>
        )
    }
}