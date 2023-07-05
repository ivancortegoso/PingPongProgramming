import React from 'react'
import './styles/AccountMenuStyle.css'
import {Link} from "react-router-dom";
import {Auth} from "../Auth";

export class AccountMenu extends React.Component {

    render() {
        return (
            <div className={this.props.isShown ? "AccountMenu AccountMenu-Enable" : "AccountMenu AccountMenu-Disable"}>
                AccountMenu
                {Auth.IsLogged() ? <div>Logged</div> : <div><Link to="/login">Login</Link></div>}
                {Auth.IsLogged() ? "" : <div><Link to="/signup">Signup</Link></div>}
                <div><Link to="/bankaccounts">Bank accounts</Link></div>
            </div>
        )
    }
}