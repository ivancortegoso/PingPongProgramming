import React from 'react'
import {Link} from "react-router-dom";
import './styles/MenuStyle.css'

export class Menu extends React.Component {

    render() {
        return (
            <div className="Menu">
                <div><Link className="links-styles" to="/transactions/all">Everyone</Link></div>
                <div><Link className="links-styles" to="/transactions/friends">Friends</Link></div>
                <div><Link className="links-styles" to="/transactions/user">Mine</Link></div>
                <div><Link className="links-styles" to="/transaction/create">Create</Link></div>
            </div>
        );
    }
}