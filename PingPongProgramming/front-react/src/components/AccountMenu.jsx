import React from 'react'
import './styles/AccountMenuStyle.css'
import {Link} from "react-router-dom";
import {Auth} from "../Auth";
import {useState, useEffect} from 'react'


export const AccountMenu = (props) => {
    const [info, setInfo] = useState([]);

    const fetchInfo = async() => {
        const response = await fetch("http://localhost:8080/api/user", {
            headers:{
                'Authorization' : Auth.GetAuth()
            },
        });
        try{
            const recvInfo = await response.json();
            console.log(recvInfo);
            setInfo(recvInfo);
        } catch(e) {

        }
    }

    useEffect(() => {
        if(Auth.IsLogged())
            fetchInfo();
    }, [])

    const LogOut = () => {
        Auth.SetAuth("");
        props.onLogout(false);
    }

    const ShowNoLoggedView = () => {
        return (
            <>
                <div><Link id="login-id" to="/login">Log in</Link></div>
                <div><Link id="signup-id" to="/signup">Sign up</Link></div>
            </>
        );
    }

    const ShowLoggedView = () => {
        return (
            <>
                <div>Username: {info["username"]}</div>
                <div>First name: {info["firstName"]}</div>
                <div>Second name: {info["lastName"]}</div>
                <div>Balance: {info["balance"]}</div>
                <button><Link to="/user/settings">My Account</Link></button>
                <div><button type="button" onClick={LogOut}>Log out</button></div>
            </>
        );
    }

    return (
        <div className={props.display ? "AccountMenu AccountMenu-Enable" : "AccountMenu AccountMenu-Disable"}>
            <h3>Account Menu</h3>
            {Auth.IsLogged() ? ShowLoggedView() : ShowNoLoggedView()}
        </div>
    )
}
