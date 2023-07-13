import React from 'react'
import {Link} from "react-router-dom";
import './styles/MenuStyle.css'

export const Menu = () => {
    return (
        <div className="Menu">
            <Link className="links-styles" to="/bankaccounts">Bank accounts</Link>
            <Link className="links-styles" to="/transactions">Transactions</Link>
        </div>
    );
}
