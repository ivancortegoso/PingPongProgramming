import './App.css';
import React from 'react'
import {Route, Routes} from "react-router-dom";
import {SignUpWeb} from "./components/webs/SignUpWeb";
import {LogInWeb} from "./components/webs/LogInWeb";
import {BankAccountsWeb} from "./components/webs/BankAccountsWeb";
import {Menu} from "./components/Menu";
import {AccountMenu} from "./components/AccountMenu";

export class App extends React.Component {
    state = {
        showAccountMenu: false
    };

    render() {
        return (
            <div className="App">
                {this.state.showAccountMenu ? <AccountMenu/> : <></>}
                <div className="Web">
                    <header className="App-header">
                        <button className="AccountMenuButton">Acc menu</button>
                        <h1>Real World App</h1>
                        <Menu/>
                    </header>
                    <Routes>
                        <Route path="/" element={<div>In development</div>}/>
                        <Route path="/signup" element={<SignUpWeb/>}/>
                        <Route path="/login" element={<LogInWeb/>}/>
                        <Route path="/bankaccounts" element={<BankAccountsWeb/>}/>
                    </Routes>

                </div>

            </div>
        );

    }
}

