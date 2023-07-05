import React from 'react'
import './styles/AccountMenuStyle.css'
import {Link} from "react-router-dom";

export class AccountMenu extends React.Component {

    render() {
        return (
            <div className={this.props.isShown ? "AccountMenu AccountMenu-Enable" : "AccountMenu AccountMenu-Disable"}>
                AccountMenu
                <div><Link to="/login">Login</Link></div>
                <div><Link to="/signup">Signup</Link></div>
                <div><Link to="/bankaccounts">Bank accounts</Link></div>
            </div>
        )
    }
}