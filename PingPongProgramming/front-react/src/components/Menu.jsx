import React from 'react'
import {Link} from "react-router-dom";
import './styles/MenuStyle.css'

export class Menu extends React.Component {

    render() {
        return (
            <div className="Menu">
                <div><Link className="links-styles" to="/transactions">Everyone</Link></div>
                <div><Link className="links-styles" to="/transactions">Friends</Link></div>
                <div><Link className="links-styles" to="/transaction">Mine</Link></div>
            </div>
        );
    }
}