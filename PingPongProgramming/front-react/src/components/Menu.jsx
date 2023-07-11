import React from 'react'
import {Link} from "react-router-dom";
import './styles/MenuStyle.css'

export class Menu extends React.Component {

    render() {
        return (
            <div className="Menu">
                <Link className="links-styles" to="/transactions/all">Everyone</Link>
                <Link className="links-styles" to="/transactions/friends">Friends</Link>
                <Link className="links-styles" to="/transactions/user">Mine</Link>
                <Link className="links-styles Menu-create-transaction" to="/transactions/all/create">Create</Link>
            </div>
        );
    }
}