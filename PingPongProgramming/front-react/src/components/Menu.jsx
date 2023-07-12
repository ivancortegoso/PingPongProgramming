import React from 'react'
import {Link} from "react-router-dom";
import './styles/MenuStyle.css'
import Popup from 'reactjs-popup';
import {TransactionCreateWeb} from './Transaction/TransactionCreateWeb'

export const Menu = () => {
    return (
        <div className="Menu">
            <Link className="links-styles" to="/transactions/all">Everyone</Link>
            <Link className="links-styles" to="/transactions/friends">Friends</Link>
            <Link className="links-styles" to="/transactions/user">Mine</Link>
            <Popup trigger ={<button>Create</button>} modal nested>
                {close => (<TransactionCreateWeb onClose={close}/>)}
            </Popup>
        </div>
    );
}
