import React from 'react'
import {Link} from "react-router-dom";
import './MenuStyle.css'

export class Menu extends React.Component {

    render() {
        return (
            <div className="Menu">
                <div><Link className="links-styles" to="/login">Login</Link></div>
                <div><Link className="links-styles" to="/signup">Signup</Link></div>
                <div><Link className="links-styles" to="/bankaccounts">Bank accounts</Link></div>
            </div>
        );
    }
}