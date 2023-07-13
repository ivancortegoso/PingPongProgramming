import React from 'react'
import './styles/AccountMenuStyle.css'
import {Link, useNavigate} from "react-router-dom";
import {Auth} from "../Auth";
import {useState, useEffect} from 'react'


export const AccountMenu = (props) => {
    const [info, setInfo] = useState([]);
    const navigate = useNavigate();

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
        window.location.reload();
    }

    const ShowNoLoggedView = () => {
        return (
            <>
                <div><Link to="/login">Log in</Link></div>
                <div><Link to="/signup">Sign up</Link></div>
            </>
        );
    }

    const ShowLoggedView = () => {
        return (
            <>
                <div>Username: {info["username"]}</div>
                <div>First name: {info["firstName"]}</div>
                <div>Second name: {info["lastName"]}</div>
                <button><Link to="/user/settings">My Account</Link></button>
                <div><button type="button" onClick={LogOut}>Log out</button></div>
            </>
        );
    }

    return (
        <div className={props.isShown ? "AccountMenu AccountMenu-Enable" : "AccountMenu AccountMenu-Disable"}>
            <h3>Account Menu</h3>

            {Auth.IsLogged() ? ShowLoggedView() : ShowNoLoggedView()}
        </div>
    )
}

/*export class AccountMenu extends React.Component {
    constructor() {
        super();
        this.state = {
            info: [],
        }
        this.fetchInfo = this.fetchInfo.bind(this);
    }
    async fetchInfo() {
        const response = await fetch("http://localhost:8080/api/user", {
            method: 'GET',
            headers: {
                'Authorization': Auth.GetAuth()
            }
        });
        try{
            const recvInfo = await response.json();
            console.log(recvInfo);
            this.setState( { info: recvInfo });
        } catch(e) {

        }

    }

    componentDidMount() {
        if(Auth.IsLogged())
            this.fetchInfo()
    }



    render() {
        return (
            <div className={this.props.isShown ? "AccountMenu AccountMenu-Enable" : "AccountMenu AccountMenu-Disable"}>
                <label>Account Menu</label>
                {Auth.IsLogged() ? <div><Link to="/login" onClick={()=> Auth.SetAuth("")}>Log out</Link></div> : <div><Link to="/login">Log in</Link></div>}
                {Auth.IsLogged() ? "" : <div><Link to="/signup">Sign up</Link></div>}
                <div>Username: {this.state.info["username"]}</div>
                <div>First name: {this.state.info["firstName"]}</div>
                <div>Second name: {this.state.info["lastName"]}</div>
                {Auth.IsLogged() ? <div><Link to="/user/settings">My Account</Link></div> : ""}
                {Auth.IsLogged() ? <div><Link to="/bankaccounts">Bank Accounts</Link></div> : ""}
            </div>
        )
    }
}*/