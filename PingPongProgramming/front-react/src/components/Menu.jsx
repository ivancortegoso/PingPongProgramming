import React from 'react'
import {Link} from "react-router-dom";
import './styles/MenuStyle.css'

export const Menu = () => {
    return (
        <div className="Menu">
            <Link className="links-styles" to="/transactions/all">Everyone</Link>
            <Link className="links-styles" to="/transactions/friends">Friends</Link>
            <Link className="links-styles" to="/transactions/user">Mine</Link>
            
        </div>
    );
}
